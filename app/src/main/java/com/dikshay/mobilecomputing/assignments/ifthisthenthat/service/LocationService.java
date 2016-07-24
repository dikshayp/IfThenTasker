package com.dikshay.mobilecomputing.assignments.ifthisthenthat.service;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.Constants;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.GpsData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.helper.UploadFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by abdul on 3/16/2016.
 */
public class LocationService extends Service implements LocationListener {

    //private final Context context;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    // for gym calculation
    boolean gymFlag = false;
    Date gymEnterDate = null;
    Date gymExitDate = null;

    WifiManager wifiManager;
    AudioManager audioManager;

    double longitude;
    double latitude;

    //for detecting if home
    //double homeLatitude;
    //double homeLongitude;

    //Locations
    Location homeLocation;
    Location classLocation;
    Location doNotDisturbLocation;
    Location gymLocation;
    Location notifyFriendLocation;

    private static final long MIN_DIST_FOR_UPDATE = 10;
    private static final long MIN_TIME_FOR_UPDATE = 1000 * 5;

    protected LocationManager locationManager;
    Location location;

    /*public GetLocationService(Context context) {
        Log.d("Constructor", "constructor called");
        this.context = context;
    }*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d("onStartCommand ", "Something");
        getLocation();
        return START_STICKY;
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {

            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_FOR_UPDATE,
                            MIN_DIST_FOR_UPDATE, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_FOR_UPDATE,
                            MIN_DIST_FOR_UPDATE, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }

        } catch (SecurityException se){
            se.printStackTrace();

        }catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(LocationService.this);
        }
    }

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        return latitude;
    }

    public void setLatitude(double latitude){
        location.setLatitude(latitude);
    }

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        return longitude;
    }

    public void setLongitude(double longitude){
        location.setLongitude(longitude);
    }



    public boolean canGetLocation(){
        return this.canGetLocation;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS Settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings and enable GPS?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    public void setFlags(){

    }

    public float distanceTo(Location dest){
        float dist = 0;
        if(location != null && dest != null){
            dist = location.distanceTo(dest);
        }
        return dist;
    }

    public void toggleWiFi(){
        homeLocation = GpsData.getHomeLocation();
        if(homeLocation != null){
            float distance = this.distanceTo(homeLocation);
            Log.d("DIST from Home: ", Float.toString(distance) + " meters");
            //distance = distance * 62/100000;
            //Log.d("DIST NEW : ", Float.toString(distance));
            //Toast.makeText(getApplicationContext(), "Home Location : " + homeLocation.getLatitude() + ", "
             //       + homeLocation.getLongitude() + "\nDistance: " + distance, Toast.LENGTH_LONG).show();

            //Lets try toggling wifi
            if(distance > 50){
                wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
                if(wifiManager.isWifiEnabled())
                    wifiManager.setWifiEnabled(false);
            }
            else{
                wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
                if(!wifiManager.isWifiEnabled())
                    wifiManager.setWifiEnabled(true);
            }
        }

    }

    public void toggleRinger(){
        classLocation = GpsData.getClassLocation();
        if(classLocation != null){
            float distance = this.distanceTo(classLocation);
            Log.d("DIST from Class: ", Float.toString(distance) + " meters");
            //distance = distance * 62/100000;
            //Log.d("DIST NEW : ", Float.toString(distance));
            //Toast.makeText(getApplicationContext(), "Class Location : " + classLocation.getLatitude() + ", "
             //       + classLocation.getLongitude() + "\nDistance: " + distance, Toast.LENGTH_LONG).show();

            //Lets try Silent Mode
            if(distance < 100) {   // the distance would get replaced by class location
                //GpsData.setInClass(true);
                audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                if(audioManager.getStreamVolume(AudioManager.STREAM_RING) > 0){
                    //audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
                }
            }
            else{
                //GpsData.setInClass(false);
                audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                if(audioManager.getStreamVolume(AudioManager.STREAM_RING) == 0){
                    //audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, 50, 0);
                }
            }
        }

    }

    public void cancelCall(){
        doNotDisturbLocation = GpsData.getDoNotDisturbLocation();
        if(doNotDisturbLocation != null){
            float distance = this.distanceTo(doNotDisturbLocation);
            Log.d("DIST frm Dont Disturb: ", Float.toString(distance) + " meters");
            //distance = distance * 62/100000;
            //Log.d("DIST NEW : ", Float.toString(distance));
            //Toast.makeText(getApplicationContext(), "doNotDisturb Location : " + doNotDisturbLocation.getLatitude() + ", "
            //       + doNotDisturbLocation.getLongitude() + "\nDistance: " + distance, Toast.LENGTH_LONG).show();

            //Just Enable / Disable the inClass flag
            if(distance < 100) {
                GpsData.setInClass(true);
                Log.d("Setting In Class", "true");
            }
            else{
                GpsData.setInClass(false);
                Log.d("Setting In Class", "false");
            }
        }
    }

    public void generateNoteOnSD(String sBody) {
        try {
            //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            File externalStorageDir = new File(Constants.ROOT_DIRECTORY, "Notes");
            if (!externalStorageDir.exists()) {
                externalStorageDir.mkdirs();
            }
            File myFile = new File(externalStorageDir , Constants.TRACK_TIME);
            if(!myFile.exists()) {
                myFile.createNewFile();
            }
            Log.d("File Name : ", myFile.toString());

            FileOutputStream fOut = new FileOutputStream(myFile, true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(sBody);
            myOutWriter.append("\n\r");
            myOutWriter.close();
            fOut.close();
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recordGymTime(){

        gymLocation = GpsData.getGymLocation();
        if(gymLocation != null){
            float distance = this.distanceTo(gymLocation);
            Log.d("Gym Test DIST frm Gym: ", Float.toString(distance) + " meters");

            if(distance < 200) {
                if(!gymFlag){
                    gymFlag = true;
                    gymEnterDate = new Date();
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(gymEnterDate);
                    Log.d("Gym Test Entering Gym: ", currentDateTimeString);
                }
            }
            else{
                if(gymFlag){
                    gymFlag = false;
                    gymExitDate = new Date();
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(gymExitDate);
                    long difference = (gymExitDate.getTime() - gymEnterDate.getTime())/1000;

                    Log.d("Gym Test Exiting Gym: ", currentDateTimeString);
                    Log.d("Gym Test Time Spent: ", Long.toString(difference));
                    //String hrs = difference/(60 * 60) + " Hrs";
                    //difference /= 60;
                    String hrs = difference/(60*60) + " h ";
                    difference %= 60 * 60;
                    String mins = difference/60 + " m ";
                    difference %= 60;
                    String secs = difference + " s ";
                    String entry = DateFormat.getDateTimeInstance().format(gymEnterDate)
                            + " - " + DateFormat.getTimeInstance().format(gymExitDate)
                            + " = " + hrs + mins + secs + " ";
                    generateNoteOnSD(entry);
                    UploadFile uploadFile = new UploadFile(this, "track_time");
                    uploadFile.execute();
                }
            }
        }
    }

    private void sendMessage(String phoneNumber,String message)
    {
        try {
            Log.d("TAG", "phoneNumber:" + phoneNumber);
            Log.d("TAG","message:" + message);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            //Toast.makeText(context, "Message Sent",
            //        Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            //Toast.makeText(context,ex.getMessage().toString(),
            //        Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void notifyFriend(){

        notifyFriendLocation = GpsData.getNotifyFriendLocation();
        String message = "I have reached";
        if(notifyFriendLocation != null){
            float distance = this.distanceTo(notifyFriendLocation);
            Log.d("Notify Friend Dist: ", Float.toString(distance) + " meters");
            Log.d("Notify Friend Dist: ", Float.toString(distance) + " meters");
            if(distance < 100) {
                sendMessage(GpsData.getMobileNumber(), message);
                Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
                GpsData.setNotifyFriend(false);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
       Toast.makeText(getApplicationContext(), "Location: " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_LONG).show();
        this.location = location;
        Log.d("Location Changed", "Change");
        if(GpsData.isWifiToggle()){
            toggleWiFi();
        }
        if(GpsData.isRingerVolumeToggle()){
            toggleRinger();
        }
        if(GpsData.isCancelCall()){
            Log.d("Cancel Call", "Cancelling Call");
            cancelCall();
        }
        if(GpsData.isRecordGymTime()){
            //Log.d("Gym Test", "Flag Set");
            recordGymTime();
        }
        if(GpsData.isNotifyFriend()){
            Log.d("Notify Friend", "Flag Set");
            notifyFriend();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
