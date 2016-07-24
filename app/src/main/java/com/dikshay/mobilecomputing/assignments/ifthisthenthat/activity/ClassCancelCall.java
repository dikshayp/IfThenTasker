package com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity;

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
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.Constants;
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


public class ClassCancelCall extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions marker;
    private LatLng markerLocation;
    private Location doNotDisturbLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Inside Maps onCreate", "onCreate");
        setContentView(R.layout.activity_class_cancel_call);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Button setLocation = (Button) findViewById(R.id.set_location);
        Button search = (Button) findViewById(R.id.search);
        ToggleButton toggleRecepie = (ToggleButton) findViewById(R.id.toggle_recepie);
        toggleRecepie.setChecked(GpsData.isCancelCall());

        // Add a marker to previous map location
        doNotDisturbLocation = GpsData.getDoNotDisturbLocation();
        if(doNotDisturbLocation != null){
            markerLocation = new LatLng(doNotDisturbLocation.getLatitude(), doNotDisturbLocation.getLongitude());
        }
        else{
            markerLocation = new LatLng(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE);
        }

        marker = new MarkerOptions().position(markerLocation).title("Marker in Class/Default").draggable(true);
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLocation, Constants.ZOOM_LEVEL));

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                markerLocation = marker.getPosition();
            }
        });

        //show location address in string
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText address = (EditText) findViewById(R.id.address);
                String address_string = address.getText().toString();
                List<Address> addressList = null;
                if (address_string != null || address_string != "") {
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        addressList = geocoder.getFromLocationName(address_string, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address addr = addressList.get(0);
                    mMap.clear();
                    markerLocation = new LatLng(addr.getLatitude(), addr.getLongitude());

                    marker.position(markerLocation).title("Marker in Searched Address").draggable(true);
                    mMap.addMarker(marker);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerLocation, Constants.ZOOM_LEVEL));
                }
            }
        });

        //show location point on map
        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Current Marker Location:  " +
                        markerLocation.latitude + ", " + markerLocation.longitude, Toast.LENGTH_LONG).show();

                // set message to be sent
                EditText message = (EditText) findViewById(R.id.set_message);
                if(message.getText().toString() != null && message.getText().toString() != ""){
                    Toast.makeText(getApplicationContext(), "Message Set", Toast.LENGTH_LONG).show();
                    GpsData.setMessage(message.getText().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please set a message", Toast.LENGTH_LONG).show();
                    return;
                }

                //Set Class Location
                doNotDisturbLocation = new Location("Home");
                doNotDisturbLocation.setLatitude(markerLocation.latitude);
                doNotDisturbLocation.setLongitude(markerLocation.longitude);
                GpsData.setDoNotDisturbLocation(doNotDisturbLocation);

            }
        });

        toggleRecepie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    GpsData.setCancelCall(true);

                    //Location Service
                    Intent intent = new Intent(getApplicationContext(), LocationService.class);
                    startService(intent);

                } else {
                    GpsData.setCancelCall(false);
                }
            }
        });
    }
}
