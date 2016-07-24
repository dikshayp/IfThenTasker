package com.dikshay.mobilecomputing.assignments.ifthisthenthat.googledrive;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.DriveId;

/**
 * Created by Dikshay on 4/24/2016.
 */
public class ApplicationClass extends Application{

    public Context mContext;
    private static final int REQUEST_CODE = 101;
    private static GoogleApiClient googleApiClient;
    public static String drive_id;
    public static DriveId driveID;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

    }
    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }
    public static void setGoogleApiClient(GoogleApiClient pgoogleApiClient)
    {
        googleApiClient = googleApiClient;
    }

    public static GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }
}
