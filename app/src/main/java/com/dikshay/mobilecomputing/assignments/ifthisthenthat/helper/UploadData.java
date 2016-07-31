package com.dikshay.mobilecomputing.assignments.ifthisthenthat.helper;

/**
 * Created by abdul on 4/21/2016.
 */
public class UploadData {

    private static boolean uploadToDrive;
    private static boolean uploadToDropbox;

    public static boolean isUploadToDrive() {
        return uploadToDrive;
    }

    public static boolean isUploadToDropbox() {
        return uploadToDropbox;
    }

    public static void setUploadToDrive(boolean uploadToDrive) {
        UploadData.uploadToDrive = uploadToDrive;
    }

    public static void setUploadToDropbox(boolean uploadToDropbox) {
        UploadData.uploadToDropbox = uploadToDropbox;
    }
}
