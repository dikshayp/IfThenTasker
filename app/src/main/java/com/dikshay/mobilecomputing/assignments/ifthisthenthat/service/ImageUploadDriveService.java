package com.dikshay.mobilecomputing.assignments.ifthisthenthat.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.Constants;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.Constants_d;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.FileServerData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.GpsData_d;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.googledrive.ApplicationClass;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.observer.MediaObserver;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveId;

/**
 * Created by Dikshay on 4/24/2016.
 */
public class ImageUploadDriveService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    String TAG = "ImageUploadDriveService";

    private static final int REQUEST_CODE = 101;
    private GoogleApiClient googleApiClient = ApplicationClass.getGoogleApiClient();
    //private GoogleApiClient googleApiClient;
    public static String drive_id;
    public static DriveId driveID;
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
        MediaObserver mediaObserver;
        if(FileServerData.getObserver()==null)
        {
            FileServerData.setObserver(new MediaObserver(new Handler()));
        }
        mediaObserver = FileServerData.getObserver();
        //MediaObserver mediaObserver = new MediaObserver(new Handler(),getApplicationContext(), Constants_d.GOOGLE_DRIVE);
        mediaObserver.config(getApplicationContext(), Constants.GOOGLE_DRIVE);
        mediaObserver.observe();

        //mediaObserver.getNewMedia();
        googleApiClient = ApplicationClass.getGoogleApiClient();
        if(googleApiClient==null || !googleApiClient.isConnected())
        {
            Log.d(TAG,"not connected in service");
            Log.d(TAG,"now trying to connnect");
            buildGoogleApiClient();
            googleApiClient.connect();
        }
        mediaObserver.setmGoogleApiClient(googleApiClient);
        FileServerData.setIsDriveActive(true);
        return START_STICKY;
    }
    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "in onConnected() - we're connected, let's do the work in the background...");
        Log.d(TAG,"Connected in service");
        ApplicationClass.setGoogleApiClient(googleApiClient);
        FileServerData.setIsDriveActive(true);

    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed");
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), (Activity) getApplicationContext(), 0).show();
            return;
        }
        try {
            Log.i(TAG, "trying to resolve the Connection failed error...");
            result.startResolutionForResult((Activity)getApplicationContext(), REQUEST_CODE);
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "Exception while starting resolution activity", e);
        }
    }
    @Override
    public void onConnectionSuspended(int cause) {
        switch (cause) {
            case 1:
                Log.i(TAG, "Connection suspended - Cause: " + "Service disconnected");
                break;
            case 2:
                Log.i(TAG, "Connection suspended - Cause: " + "Connection lost");
                break;
            default:
                Log.i(TAG, "Connection suspended - Cause: " + "Unknown");
                break;
        }
    }
    @Override
    public void onDestroy()
    {
        Log.d(TAG,"service execution stopped successfully");
    }
    private void buildGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            ApplicationClass.setGoogleApiClient(googleApiClient);
        }
        FileServerData.setIsDriveActive(false);
    }

}
