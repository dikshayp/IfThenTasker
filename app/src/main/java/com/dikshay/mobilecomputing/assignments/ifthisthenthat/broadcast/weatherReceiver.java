package com.dikshay.mobilecomputing.assignments.ifthisthenthat.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.service.WeatherServiceAsync;

/**
 * Created by User847 on 5/2/2016.
 */
public class weatherReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO Auto-generated method stub
        Toast.makeText(context, "Alarm Triggered and weather Sent", Toast.LENGTH_LONG).show();
        Log.d("Caught", "Broadcast");
        // here you can start an activity or service depending on your need
        // for ex you can start an activity to vibrate phone or to ring the phone


        try {
            WeatherServiceAsync task = new WeatherServiceAsync(context);
            task.execute();
            Toast.makeText(context, "Weather Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(context,ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }


    }

}
