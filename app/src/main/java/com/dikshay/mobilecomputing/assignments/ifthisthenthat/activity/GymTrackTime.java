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


public class GymTrackTime extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions marker;
    private LatLng markerLocation;
    private Location gymLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Inside Maps onCreate", "onCreate");
        setContentView(R.layout.activity_gym_track_time);
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
        toggleRecepie.setChecked(GpsData.isRecordGymTime());

        // Add a marker to previous map location
        gymLocation = GpsData.getGymLocation();
        if(gymLocation != null){
            markerLocation = new LatLng(gymLocation.getLatitude(), gymLocation.getLongitude());
        }
        else{
            markerLocation = new LatLng(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE);
        }
        marker = new MarkerOptions().position(markerLocation).title("Marker in Gym/Default").draggable(true);
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

                //Set Home Location
                gymLocation = new Location("Gym");
                gymLocation.setLatitude(markerLocation.latitude);
                gymLocation.setLongitude(markerLocation.longitude);
                GpsData.setGymLocation(gymLocation);



            }
        });

        toggleRecepie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    //Location Service
                    Intent intent = new Intent(getApplicationContext(), LocationService.class);
                    startService(intent);

                    //Do this after allowing connection
                    //GpsData.setRecordGymTime(true);

                    //lets try creating a file
                    if(FileServerData.getDropboxApi() == null)
                        startActivity(new Intent(GymTrackTime.this, ConnectToDropbox.class));

                } else {
                    GpsData.setRecordGymTime(false);
                }
            }
        });
    }

    private void moveToCurrentLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
    }
}
