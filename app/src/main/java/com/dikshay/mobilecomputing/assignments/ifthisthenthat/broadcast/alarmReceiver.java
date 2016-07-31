package com.dikshay.mobilecomputing.assignments.ifthisthenthat.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.AlarmData;

public class alarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO Auto-generated method stub
        Toast.makeText(context, "Alarm Triggered and SMS Sent", Toast.LENGTH_LONG).show();
        Log.d("Caught","Broadcast");
        // here you can start an activity or service depending on your need
        // for ex you can start an activity to vibrate phone or to ring the phone

        sendMessage(context, AlarmData.PHONE_NUMBER, AlarmData.MESSAGE);

    }
    private void sendMessage(Context context,String phoneNumber,String message)
    {
        try {
            Log.d("TAG", "phoneNumber:" + phoneNumber);
            Log.d("TAG","message:" + message);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(context, "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(context,ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
