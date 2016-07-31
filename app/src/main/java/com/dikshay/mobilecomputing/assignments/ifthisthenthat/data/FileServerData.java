package com.dikshay.mobilecomputing.assignments.ifthisthenthat.data;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.observer.MediaObserver;
import com.dropbox.client2.DropboxAPI;

/**
 * Created by abdul on 7/31/2016.
 */
public class FileServerData {

    private static DropboxAPI dropboxApi;
    private static boolean isDropbBoxActive;
    private static boolean isDriveActive;
    private static MediaObserver observer;

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

    public static void setDropboxApi(DropboxAPI dropboxApi) {
        FileServerData.dropboxApi = dropboxApi;
    }

    public static DropboxAPI getDropboxApi() {
        return FileServerData.dropboxApi;
    }
}
