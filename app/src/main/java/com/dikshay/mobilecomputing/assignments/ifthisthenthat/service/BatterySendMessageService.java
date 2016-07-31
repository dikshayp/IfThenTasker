package com.dikshay.mobilecomputing.assignments.ifthisthenthat.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.Constants;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.Constants_d;

/**
 * Created by Dikshay on 4/18/2016.
 */
public class BatterySendMessageService extends Service {
    private static final String TAG = "BatteryService";
    private int previousBatteryLevel;
    private boolean firstBatteryRecord = true;
    private int batterylevelSendMessage = 0;
    String phoneNumber="";
    String message="";
    public BatterySendMessageService()
    {

    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate()
    {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.BATTERY_CHANGED");
        registerReceiver(ConnectionReceiver,filter);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        batterylevelSendMessage = intent.getIntExtra(Constants.BATTERY_LEVEL,0);
        phoneNumber = intent.getStringExtra(Constants.PHONE_NUMBER);
        message = intent.getStringExtra(Constants.MESSAGE);
        Log.d(TAG,"Service received battery level as"+batterylevelSendMessage);
        return START_REDELIVER_INTENT;

    }
    @Override
    public void onDestroy()
    {

        Log.d("service done", "Battery Service execution done");
        unregisterReceiver(ConnectionReceiver);
    }
    private void sendMessage(String phoneNumber,String message)
    {
        try {
            Log.d(TAG,"phoneNumber:" + phoneNumber);
            Log.d(TAG,"message:" + message);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
    private BroadcastReceiver ConnectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent batteryStatus) {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = (level / (float)scale)*100;
            int currentBatteryLevel = (int)batteryPct;
            Log.d(TAG,String.valueOf(currentBatteryLevel));
            if(firstBatteryRecord)
            {
                if(currentBatteryLevel ==batterylevelSendMessage)
                {
                    Log.d(TAG,"CurrentBatteryLevel:"+currentBatteryLevel);
                    Log.d(TAG,"PreviousBatteryLevel:"+previousBatteryLevel);
                    Toast.makeText(context,"Condition met for disconnect wifi",Toast.LENGTH_LONG).show();
                    //reduceBrightness(0.1f);
                    //disconnectWifi(context);
                    sendMessage(phoneNumber,message);
                }
                Log.d(TAG,"CurrentBatteryLevel:"+currentBatteryLevel);
                Log.d(TAG,"PreviousBatteryLevel:"+previousBatteryLevel);
                previousBatteryLevel = currentBatteryLevel;
                firstBatteryRecord = false;
            }
            else
            {
                if(Math.abs(previousBatteryLevel-currentBatteryLevel)==1)
                {
                    if(currentBatteryLevel ==batterylevelSendMessage)
                    {
                        Log.d(TAG,"CurrentBatteryLevel:"+currentBatteryLevel);
                        Log.d(TAG,"PreviousBatteryLevel:"+previousBatteryLevel);
                        Toast.makeText(context,"Condition met for disconncect wifi",Toast.LENGTH_LONG).show();
                        //reduceBrightness(0.1f);
                        //disconnectWifi(context);
                        sendMessage(phoneNumber,message);
                    }
                    previousBatteryLevel = currentBatteryLevel;
                }
                else if(Math.abs(previousBatteryLevel-currentBatteryLevel)==0)
                {
                    Log.d(TAG,"Battery level not changed");
                    Log.d(TAG,"CurrentBatteryLevel:"+currentBatteryLevel);
                    Log.d(TAG,"PreviousBatteryLevel:"+previousBatteryLevel);
                }
                else
                {
                    Log.d(TAG,"There has been an error in recording battery levels");
                    Log.d(TAG,"CurrentBatteryLevel:"+currentBatteryLevel);
                    Log.d(TAG,"PreviousBatteryLevel:"+previousBatteryLevel);
                }
            }
            //Toast.makeText(context,"batteryLevel:" + batteryPct,Toast.LENGTH_LONG).show();
            // Intent intent = new Intent(this,Notification)
        }
    };
}
