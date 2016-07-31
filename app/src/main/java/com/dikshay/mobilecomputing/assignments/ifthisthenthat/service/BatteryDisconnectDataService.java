package com.dikshay.mobilecomputing.assignments.ifthisthenthat.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.Constants;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.Constants_d;

/**
 * Created by Dikshay on 4/18/2016.
 */
public class BatteryDisconnectDataService extends Service {

    private static final String TAG = "BatteryService";
    private int previousBatteryLevel;
    private boolean firstBatteryRecord = true;
    private int batterylevelDisconnectData = 0;
    public BatteryDisconnectDataService()
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
        batterylevelDisconnectData = intent.getIntExtra(Constants.BATTERY_LEVEL,0);
        Log.d(TAG,"Service received battery level as"+batterylevelDisconnectData);
        return START_REDELIVER_INTENT;

    }
    @Override
    public void onDestroy()
    {

        Log.d("service done", "Battery Service execution done");
        unregisterReceiver(ConnectionReceiver);
    }
    private void disconnectWifi(Context context)
    {
        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled())
        {
            wifiManager.setWifiEnabled(false);
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
                if(currentBatteryLevel ==batterylevelDisconnectData)
                {
                    Log.d(TAG,"CurrentBatteryLevel:"+currentBatteryLevel);
                    Log.d(TAG,"PreviousBatteryLevel:"+previousBatteryLevel);
                    Toast.makeText(context,"Condition met for disconnect wifi",Toast.LENGTH_LONG).show();
                    //reduceBrightness(0.1f);
                    disconnectWifi(context);
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
                    if(currentBatteryLevel ==batterylevelDisconnectData)
                    {
                        Log.d(TAG,"CurrentBatteryLevel:"+currentBatteryLevel);
                        Log.d(TAG,"PreviousBatteryLevel:"+previousBatteryLevel);
                        Toast.makeText(context,"Condition met for disconncect wifi",Toast.LENGTH_LONG).show();
                        //reduceBrightness(0.1f);
                        disconnectWifi(context);
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
