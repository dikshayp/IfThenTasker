package com.dikshay.mobilecomputing.assignments.ifthisthenthat.ImageUpload;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.Utils.Constants_d;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.GpsData_d;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.observer.MediaObserver;

/**
 * Created by Dikshay on 4/19/2016.
 */
public class ImageUploadDropboxService extends Service {
    String TAG = "ImageUploadDropboxService";
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate()
    {
        Log.d(TAG,"oncreate");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(TAG,"request received");
        Log.d(TAG,"Starting media observer");
       // MediaObserver mediaObserver = new MediaObserver(new Handler(),getApplicationContext(), Constants_d.DROPBOX);
        MediaObserver mediaObserver;
        if(GpsData_d.getObserver()==null)
        {
            GpsData_d.setObserver(new MediaObserver(new Handler()));
        }
        mediaObserver = GpsData_d.getObserver();
        mediaObserver.config(getApplicationContext(), Constants_d.DROPBOX
        );
        mediaObserver.observe();
        GpsData_d.setIsDropbBoxActive(true);
        return START_STICKY;
    }
    @Override
    public void onDestroy()
    {
        Log.d(TAG,"service execution stopped successfully");
        GpsData_d.setIsDropbBoxActive(false);
    }
}
