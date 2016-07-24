package com.dikshay.mobilecomputing.assignments.ifthisthenthat.battery;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.Utils.Constants_d;

public class BatteryService extends Service {
    private static final String TAG = "BatteryService";
    private int batterylevelReduceBrightness = 0;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private int previousBatteryLevel;
    private boolean firstBatteryRecord = true;
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(Message msg){
            Log.d(TAG,msg.toString());
        }

    }
    public BatteryService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate()
    {
        HandlerThread thread = new HandlerThread("Service Thread Arguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.BATTERY_CHANGED");
        registerReceiver(ConnectionReceiver,filter);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(TAG, "Service Starting");
        //here 0 is the default value
        batterylevelReduceBrightness = intent.getIntExtra(Constants_d.BATTERY_LEVEL,0);
        Log.d(TAG,"Service received battery level as"+batterylevelReduceBrightness);
        Message message = mServiceHandler.obtainMessage();
        message.arg1 = startId;
        mServiceHandler.sendMessage(message);

        //return START_STICKY;
        return START_REDELIVER_INTENT;
    }
    @Override
    public void onDestroy()
    {

        Log.d("service done", "Battery Service execution done");
        unregisterReceiver(ConnectionReceiver);
    }

    private void broadcastMessage(Context context,String key,String value)
    {
        Intent intent = new Intent("UI update Broadcast");
        intent.putExtra(key,value);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
    private void reduceBrightness(float brightness)
    {
        // This is important. In the next line 'brightness'
        // should be a float number between 0.0 and 1.0
        int brightnessInt = (int)(brightness*255);

        //Check that the brightness is not 0, which would effectively
        //switch off the screen, and we don't want that:
        if(brightnessInt<1) {brightnessInt=1;}

        // Set systemwide brightness setting.
        Settings.System.putInt(getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightnessInt);

        // Apply brightness by creating a dummy activity
        Intent intent = new Intent(getBaseContext(), DummyBrightnessActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("brightness value", brightness);
        getApplication().startActivity(intent);



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
                if(currentBatteryLevel ==batterylevelReduceBrightness)
                {
                    Log.d(TAG,"CurrentBatteryLevel:"+currentBatteryLevel);
                    Log.d(TAG,"PreviousBatteryLevel:"+previousBatteryLevel);
                    Toast.makeText(context,"Condition met for reduce screen brightness",Toast.LENGTH_LONG).show();
                    reduceBrightness(0.1f);
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
                    if(currentBatteryLevel ==batterylevelReduceBrightness)
                    {
                        Log.d(TAG,"CurrentBatteryLevel:"+currentBatteryLevel);
                        Log.d(TAG,"PreviousBatteryLevel:"+previousBatteryLevel);
                        Toast.makeText(context,"Condition met for reduce screen brightness",Toast.LENGTH_LONG).show();
                        reduceBrightness(0.1f);
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

