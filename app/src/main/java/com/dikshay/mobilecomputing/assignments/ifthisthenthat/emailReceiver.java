package com.dikshay.mobilecomputing.assignments.ifthisthenthat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.Constants;


/**
 * Created by Bhakti on 4/12/2016.
 */
public class emailReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO Auto-generated method stub
        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_LONG).show();
        Log.d("Caught","Broadcast");
        // here you can start an activity or service depending on your need
        // for ex you can start an activity to vibrate phone or to ring the phone

        sendMessage(context);
    }
        private void sendMessage(Context context)
    {


        final GMailSender sender = new GMailSender(Constants.EMAIL_ID,Constants.PASSWORD);
        new AsyncTask<Void, Void, Void>() {
            @Override public Void doInBackground(Void... arg) {
                try {
                    sender.sendMail(AlarmData.SUBJECT,
                            AlarmData.MESSAGE,
                            Constants.EMAIL_ID,
                            AlarmData.MAILID);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
                return null;}
        }.execute();

    }
}
