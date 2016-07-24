package com.dikshay.mobilecomputing.assignments.ifthisthenthat.data;

import android.location.Location;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.observer.MediaObserver;
import com.dropbox.client2.DropboxAPI;

/**
 * Created by abdul on 4/21/2016.
 */
public class BatteryData {

    private static boolean isBatterySms;
    private static boolean isBatteryReduceBrightness;
    private static boolean isBatteryDisconnectWifi;

    public static boolean isBatteryDisconnectWifi() {
        return isBatteryDisconnectWifi;
    }

    public static boolean isBatteryReduceBrightness() {
        return isBatteryReduceBrightness;
    }

    public static boolean isBatterySms() {
        return isBatterySms;
    }

    public static void setIsBatteryDisconnectWifi(boolean isBatteryDisconnectWifi) {
        BatteryData.isBatteryDisconnectWifi = isBatteryDisconnectWifi;
    }

    public static void setIsBatteryReduceBrightness(boolean isBatteryReduceBrightness) {
        BatteryData.isBatteryReduceBrightness = isBatteryReduceBrightness;
    }

    public static void setIsBatterySms(boolean isBatterySms) {
        BatteryData.isBatterySms = isBatterySms;
    }
}
