package com.dikshay.mobilecomputing.assignments.ifthisthenthat.data;

import android.location.Location;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.observer.MediaObserver;
import com.dropbox.client2.DropboxAPI;

/**
 * Created by abdul on 4/21/2016.
 */
public class GpsData_d {

    private static boolean isDropbBoxActive;
    private static boolean isDriveActive;
    private static boolean wifiToggle = false;
    private static boolean ringerVolumeToggle = false;
    private static boolean cancelCall = false;
    private static boolean recordGymTime = false;
    public static MediaObserver observer;

    private static Location homeLocation;
    private static Location classLocation;
    private static Location doNotDisturbLocation;
    private static Location gymLocation;

    private static DropboxAPI dropboxApi;

    //flag to check if in class
    private static boolean inClass = false;
    //private static String mobileNumber = "";
    private static String message = "";

    public static void setObserver(MediaObserver pObserver)
    {
        observer = pObserver;
    }
    public static MediaObserver getObserver()
    {
        return observer;
    }
    public static void setIsDropbBoxActive(boolean value)
    {
        isDropbBoxActive = value;
    }
    public static boolean getIsDropBoxActive()
    {
        return isDropbBoxActive;
    }
    public static void setIsDriveActive(boolean value)
    {
        isDriveActive = value;
    }
    public static boolean  getIsDriveActive()
    {
        return isDriveActive;
    }
    public static void setWifiToggle(boolean wifiToggle){
        GpsData_d.wifiToggle = wifiToggle;
    }

    public static boolean isWifiToggle() {
        return GpsData_d.wifiToggle;
    }

    public static void setRingerVolumeToggle(boolean ringerVolumeToggle){
        GpsData_d.ringerVolumeToggle = ringerVolumeToggle;
    }

    public static boolean isRingerVolumeToggle() {
        return GpsData_d.ringerVolumeToggle;
    }

    public static void setRecordGymTime(boolean recordGymTime) {
        GpsData_d.recordGymTime = recordGymTime;
    }

    public static boolean isRecordGymTime() {
        return GpsData_d.recordGymTime;
    }

    public static void setHomeLocation(Location homeLocation) {
        GpsData_d.homeLocation = homeLocation;
    }

    public static Location getHomeLocation() {
        return GpsData_d.homeLocation;
    }

    public static void setClassLocation(Location classLocation) {
        GpsData_d.classLocation = classLocation;
    }

    public static Location getClassLocation() {
        return GpsData_d.classLocation;
    }

    public static void setGymLocation(Location gymLocation) {
        GpsData_d.gymLocation = gymLocation;
    }

    public static Location getGymLocation() {
        return GpsData_d.gymLocation;
    }

    public static void setInClass(boolean inClass){
        GpsData_d.inClass = inClass;
    }

    public static boolean isInClass(){
        return GpsData_d.inClass;
    }

    public static void setCancelCall(boolean cancelCall) {
        GpsData_d.cancelCall = cancelCall;
    }

    public static boolean isCancelCall() {
        return GpsData_d.cancelCall;
    }

    public static void setMessage(String message) {
        GpsData_d.message = message;
    }

    public static String getMessage() {
        return GpsData_d.message;
    }

    /*
    public static void setMobileNumber(String mobileNumber) {
        GpsData_d.mobileNumber = mobileNumber;
    }

    public static String getMobileNumber() {
        return GpsData_d.mobileNumber;
    }*/

    public static void setDoNotDisturbLocation(Location doNotDisturbLocation) {
        GpsData_d.doNotDisturbLocation = doNotDisturbLocation;
    }

    public static Location getDoNotDisturbLocation() {
        return GpsData_d.doNotDisturbLocation;
    }

    public static void setDropboxApi(DropboxAPI dropboxApi) {
        GpsData_d.dropboxApi = dropboxApi;
    }

    public static DropboxAPI getDropboxApi() {
        return GpsData_d.dropboxApi;
    }
}
