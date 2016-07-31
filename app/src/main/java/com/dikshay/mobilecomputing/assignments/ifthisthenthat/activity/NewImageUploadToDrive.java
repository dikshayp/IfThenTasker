package com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.service.ImageUploadDriveService;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.helper.UploadData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.googledrive.ApplicationClass;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveId;

public class NewImageUploadToDrive extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = "upload_file";
    private static final int REQUEST_CODE = 101;
    //private GoogleApiClient googleApiClient = ApplicationClass.getGoogleApiClient();
    private GoogleApiClient googleApiClient;
    public static String drive_id;
    public static DriveId driveID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_image_upload_to_drive);

        /*
        Button submitButton = (Button)findViewById(R.id.activate);
        submitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                buildGoogleApiClient();
                googleApiClient.connect();
                ApplicationClass.setGoogleApiClient(googleApiClient);
                //Intent intent =new Intent(NewImageUploadToDrive.this, ImageUploadDriveService.class);
                //startService(intent);

            }
        });
        Button deactivateButton = (Button)findViewById(R.id.deactivate);
        deactivateButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent = new Intent(NewImageUploadToDrive.this,ImageUploadDriveService.class);
                stopService(intent);
            }
        });*/

        ToggleButton toggleRecepie = (ToggleButton) findViewById(R.id.toggle_recepie);
        toggleRecepie.setChecked(UploadData.isUploadToDrive());

        toggleRecepie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UploadData.setUploadToDrive(true);
                    buildGoogleApiClient();
                    googleApiClient.connect();
                    ApplicationClass.setGoogleApiClient(googleApiClient);


                } else {
                    UploadData.setUploadToDrive(false);
                    Intent intent = new Intent(NewImageUploadToDrive.this,ImageUploadDriveService.class);
                    stopService(intent);
                }
            }
        });
    }
    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "in onConnected() - we're connected, let's do the work in the background...");
        ApplicationClass.setGoogleApiClient(googleApiClient);
        Intent intent =new Intent(NewImageUploadToDrive.this, ImageUploadDriveService.class);
        startService(intent);

    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed");
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
            return;
        }
        try {
            Log.i(TAG, "trying to resolve the Connection failed error...");
            result.startResolutionForResult(this, REQUEST_CODE);
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "Exception while starting resolution activity", e);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (googleApiClient == null) {
            // Create the API client and bind it to an instance variable.
            // We use this instance as the callback for connection and connection
            // failures.
            // Since no account name is passed, the user is prompted to choose.
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        // Connect the client. Once connected, the camera is launched.
        googleApiClient.connect();
        ApplicationClass.setGoogleApiClient(googleApiClient);
    }
    @Override
    protected void onPause() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
        super.onPause();
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
    }
}
