package com.dikshay.mobilecomputing.assignments.ifthisthenthat.helper;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.FileServerData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.GpsData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.GpsData_d;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Dikshay on 4/25/2016.
 */
public class UploadFile_d extends AsyncTask<String, Void, Boolean> {
    public static String TAG = "UploadFileAsync";
    private Context mContext;
    private NotificationManager dropbox_NotifyManager;
    private NotificationCompat.Builder dropbox_Builder;
    private final int ID_Dropbox = 1;
    public UploadFile_d(Context context)
    {
        mContext = context;
    }
    @Override
    protected void onPreExecute()
    {
        dropbox_NotifyManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        dropbox_Builder = new NotificationCompat.Builder(mContext);
        dropbox_Builder.setContentTitle("Picture Upload to Dropbox")
                .setContentText("Upload in progress")
                .setSmallIcon(R.drawable.ic_launcher);
        dropbox_Builder.setProgress(100, 0, false);
        // Displays the progress bar for the first time.
        dropbox_NotifyManager.notify(ID_Dropbox, dropbox_Builder.build());
    }
    @Override
    protected Boolean doInBackground(String... params) {

        String path = params[0];
        String fileName = params[1];
        Log.d(TAG, "inside upload to dropbox");
        try {
            Log.d(TAG, "gname is :" + fileName);
            File myFile = new File(path);
            FileInputStream fileInputStream = new FileInputStream(myFile);
            DropboxAPI dropboxAPI = FileServerData.getDropboxApi();
            dropboxAPI.putFileOverwrite("/DropboxDemo/" + fileName, fileInputStream,
                    myFile.length(), null);
            return true;
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found for dropbox");
            e.printStackTrace();
        } catch (DropboxException e) {
            Log.d(TAG, "Dropbox exception");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Log.d(TAG, "file uploaded successfullly");
        } else {
            Log.d(TAG, "file not uploaded");
        }

        dropbox_Builder.setContentText("Upload to Dropbox complete").setProgress(0, 0, false);
        dropbox_NotifyManager.notify(ID_Dropbox, dropbox_Builder.build());
    }
}