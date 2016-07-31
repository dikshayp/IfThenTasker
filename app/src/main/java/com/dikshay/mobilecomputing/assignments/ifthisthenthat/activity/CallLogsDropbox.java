package com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.CallLogsData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.Constants;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.FileServerData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.GpsData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.service.LocationService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class CallLogsDropbox extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Inside Maps onCreate", "onCreate");
        setContentView(R.layout.activity_call_logs_dropbox);

        //Button activateBtn = (Button) findViewById(R.id.activate);
        ToggleButton toggleRecepie = (ToggleButton) findViewById(R.id.toggle_recepie);
        toggleRecepie.setChecked(CallLogsData.isRecordLogs());

        /*
        activateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });*/

        toggleRecepie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    if (FileServerData.getDropboxApi() == null)
                        startActivity(new Intent(CallLogsDropbox.this, ConnectToDropbox.class));
                    CallLogsData.setRecordLogs(true);

                } else {
                    CallLogsData.setRecordLogs(false);
                }
            }
        });
    }
}
