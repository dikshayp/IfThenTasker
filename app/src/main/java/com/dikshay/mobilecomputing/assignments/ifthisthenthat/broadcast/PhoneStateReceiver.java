package com.dikshay.mobilecomputing.assignments.ifthisthenthat.broadcast;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.CallLog;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.CallLogsData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.Constants;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.GpsData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.helper.UploadFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhoneStateReceiver extends BroadcastReceiver {
    public static String TAG="PhoneStateReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            Log.d(TAG, "PhoneStateReceiver**Call State=" + state);

            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                Log.d(TAG, "PhoneStateReceiver**Idle");

                if(CallLogsData.isRecordLogs())
                    addLog(context);
            } else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                // Incoming call
                String incomingNumber =
                        intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.d(TAG,"PhoneStateReceiver**Incoming call " + incomingNumber);


                if (!killCall(context, incomingNumber)) { // Using the method defined earlier
                    Log.d(TAG,"PhoneStateReceiver **Unable to kill incoming call");
                }

            } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                Log.d(TAG,"PhoneStateReceiver **Offhook");
            }
        } else if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            // Outgoing call
            String outgoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.d(TAG,"PhoneStateReceiver **Outgoing call " + outgoingNumber);

            setResultData(null); // Kills the outgoing call

        } else {
            Log.d(TAG,"PhoneStateReceiver **unexpected intent.action=" + intent.getAction());
        }
    }

    public void addLog(Context context){

        try{
            Cursor cur = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null,
                    null, android.provider.CallLog.Calls.DATE + " DESC limit 1;");
            int number = cur.getColumnIndex(CallLog.Calls.NUMBER);
            int type = cur.getColumnIndex(CallLog.Calls.TYPE);
            int date = cur.getColumnIndex(CallLog.Calls.DATE);
            int duration = cur.getColumnIndex(CallLog.Calls.DURATION);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cur.moveToFirst();
            String callType;
            if(type == 1)
                callType = "Missed Call";
            else if(type == 2)
                callType = "Outgoing Call";
            else
                callType = "Incoming Call";
            String dateString = sdf.format(new Date(Long.valueOf(cur.getString(date))));
            String log = cur.getString(number) + " " + callType + " " + dateString
                    + " " + cur.getString(duration);
            generateNoteOnSD(context, log);
            UploadFile uploadFile = new UploadFile(context, "call_logs");
            uploadFile.execute();
        }
        catch(SecurityException se){
            se.printStackTrace();
        }

    }

    public void generateNoteOnSD(Context context, String sBody) {
        try {
            //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            File externalStorageDir = new File(Constants.ROOT_DIRECTORY, "Notes");
            if (!externalStorageDir.exists()) {
                externalStorageDir.mkdirs();
            }
            File myFile = new File(externalStorageDir , Constants.CALL_LOGS);
            if(!myFile.exists()) {
                myFile.createNewFile();
            }
            Log.d("File Name : ", myFile.toString());

            FileOutputStream fOut = new FileOutputStream(myFile, true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(sBody);
            myOutWriter.append("\n\r");
            myOutWriter.close();
            fOut.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean killCall(Context context, String number) {
        if(!GpsData.isCancelCall() || !GpsData.isInClass()){
            Log.d(TAG,"PhoneStateReceiver **Dont Kill Call ");
            Log.d(TAG,"PhoneStateReceiver " + Boolean.toString(GpsData.isCancelCall()) + " " + Boolean.toString(GpsData.isCancelCall()));
            return false;
        }
        try {
            // Get the boring old TelephonyManager
            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            // Get the getITelephony() method
            Class classTelephony = Class.forName(telephonyManager.getClass().getName());
            Method methodGetITelephony = classTelephony.getDeclaredMethod("getITelephony");

            // Ignore that the method is supposed to be private
            methodGetITelephony.setAccessible(true);

            // Invoke getITelephony() to get the ITelephony interface
            Object telephonyInterface = methodGetITelephony.invoke(telephonyManager);

            // Get the endCall method from ITelephony
            Class telephonyInterfaceClass =
                    Class.forName(telephonyInterface.getClass().getName());
            Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");

            // Invoke endCall()
            Log.d(TAG,"PhoneStateReceiver **Kill Call");
            methodEndCall.invoke(telephonyInterface);

            //send message
            sendMessage(number, GpsData.getMessage());


        } catch (Exception ex) { // Many things can go wrong with reflection calls
            Log.d(TAG,"PhoneStateReceiver **" + ex.toString());
            return false;
        }
        return true;
    }

    private void sendMessage(String phoneNumber,String message)
    {
        try {
            Log.d("TAG", "phoneNumber:" + phoneNumber);
            Log.d("TAG","message:" + message);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            //Toast.makeText(context, "Message Sent",
            //        Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            //Toast.makeText(context,ex.getMessage().toString(),
            //        Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
