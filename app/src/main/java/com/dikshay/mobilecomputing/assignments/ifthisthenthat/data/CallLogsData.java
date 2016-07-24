package com.dikshay.mobilecomputing.assignments.ifthisthenthat.data;

import android.location.Location;

import com.dropbox.client2.DropboxAPI;

/**
 * Created by abdul on 4/21/2016.
 */
public class CallLogsData {

    private static boolean recordLogs = false;

    private static DropboxAPI dropboxApi;

    public static void setDropboxApi(DropboxAPI dropboxApi) {
        CallLogsData.dropboxApi = dropboxApi;
    }

    public static DropboxAPI getDropboxApi() {
        return CallLogsData.dropboxApi;
    }

    public static void setRecordLogs(boolean recordLogs) {
        CallLogsData.recordLogs = recordLogs;
    }

    public static boolean isRecordLogs() {
        return CallLogsData.recordLogs;
    }
}
