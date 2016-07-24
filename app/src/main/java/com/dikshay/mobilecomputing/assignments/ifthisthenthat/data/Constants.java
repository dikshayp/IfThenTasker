package com.dikshay.mobilecomputing.assignments.ifthisthenthat.data;

import android.os.Environment;

import com.dropbox.client2.session.Session;

import java.io.File;

/**
 * Created by abdul on 4/21/2016.
 */
public class Constants {

    public static final double DEFAULT_LATITUDE = 33.42;
    public static final double DEFAULT_LONGITUDE = -111.92;
    public static final int ZOOM_LEVEL = 13;

    //for geocoding
    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME = "com.dikshay.mobilecomputing.assignments.ifthisthenthat";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME +
            ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
            ".LOCATION_DATA_EXTRA";

    //for file upload
    public static final String TRACK_TIME = "trackTime.txt";

    public final static String DROPBOX_FILE_DIR = "/DropboxDemo/";
    public final static String DROPBOX_NAME = "dropbox_prefs";
    public final static String ACCESS_KEY = "4zl9w2eamzbaiyt";
    public final static String ACCESS_SECRET = "sm4mpjy509m256x";
    public final static Session.AccessType ACCESS_TYPE = Session.AccessType.DROPBOX;
    public final static File ROOT_DIRECTORY = Environment.getExternalStorageDirectory();

    // for call logs
    public final static String CALL_LOGS = "callLogs.txt";

    //for automated mail task
    public final static String EMAIL_ID = "ifthentasker@gmail.com";
    public final static String PASSWORD = "abdul1234";
}
