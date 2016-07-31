package com.dikshay.mobilecomputing.assignments.ifthisthenthat.data;

import android.location.Location;

import com.dropbox.client2.DropboxAPI;

/**
 * Created by abdul on 4/21/2016.
 */
public class GpsData {

    private static boolean wifiToggle = false;
    private static boolean ringerVolumeToggle = false;
    private static boolean cancelCall = false;
    private static boolean recordGymTime = false;
    private static boolean notifyFriend = false;

    private static Location homeLocation;
    private static Location classLocation;
    private static Location doNotDisturbLocation;
    private static Location gymLocation;
    private static Location notifyFriendLocation;

    //flag to check if in class
    private static boolean inClass = false;
    //private static String mobileNumber = "";
    private static String message = "";

    //mobile number of friend to be notified
    private static String mobileNumber;

    public static void setWifiToggle(boolean wifiToggle){
        GpsData.wifiToggle = wifiToggle;
    }

    public static boolean isWifiToggle() {
        return GpsData.wifiToggle;
    }

    public static void setRingerVolumeToggle(boolean ringerVolumeToggle){
        GpsData.ringerVolumeToggle = ringerVolumeToggle;
    }

    public static boolean isRingerVolumeToggle() {
        return GpsData.ringerVolumeToggle;
    }

    public static void setRecordGymTime(boolean recordGymTime) {
        GpsData.recordGymTime = recordGymTime;
    }

    public static boolean isRecordGymTime() {
        return GpsData.recordGymTime;
    }

    public static void setHomeLocation(Location homeLocation) {
        GpsData.homeLocation = homeLocation;
    }

    public static Location getHomeLocation() {
        return GpsData.homeLocation;
    }

    public static void setClassLocation(Location classLocation) {
        GpsData.classLocation = classLocation;
    }

    public static Location getClassLocation() {
        return GpsData.classLocation;
    }

    public static void setGymLocation(Location gymLocation) {
        GpsData.gymLocation = gymLocation;
    }

    public static Location getGymLocation() {
        return GpsData.gymLocation;
    }

    public static void setInClass(boolean inClass){
        GpsData.inClass = inClass;
    }

    public static boolean isInClass(){
        return GpsData.inClass;
    }

    public static void setCancelCall(boolean cancelCall) {
        GpsData.cancelCall = cancelCall;
    }

    public static boolean isCancelCall() {
        return GpsData.cancelCall;
    }

    public static void setMessage(String message) {
        GpsData.message = message;
    }

    public static String getMessage() {
        return GpsData.message;
    }

    /*
    public static void setMobileNumber(String mobileNumber) {
        GpsData.mobileNumber = mobileNumber;
    }

    public static String getMobileNumber() {
        return GpsData.mobileNumber;
    }*/

    public static void setDoNotDisturbLocation(Location doNotDisturbLocation) {
        GpsData.doNotDisturbLocation = doNotDisturbLocation;
    }

    public static Location getDoNotDisturbLocation() {
        return GpsData.doNotDisturbLocation;
    }

    public static void setNotifyFriendLocation(Location notifyFriendLocation) {
        GpsData.notifyFriendLocation = notifyFriendLocation;
    }

    public static Location getNotifyFriendLocation() {
        return GpsData.notifyFriendLocation;
    }

    public static boolean isNotifyFriend() {
        return GpsData.notifyFriend;
    }

    public static void setNotifyFriend(boolean notifyFriend) {
        GpsData.notifyFriend = notifyFriend;
    }

    public static void setMobileNumber(String mobileNumber) {
        GpsData.mobileNumber = mobileNumber;
    }

    public static String getMobileNumber() {
        return GpsData.mobileNumber;
    }
}
