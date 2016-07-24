package com.dikshay.mobilecomputing.assignments.ifthisthenthat.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.Constants;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.GpsData;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by abdul on 4/24/2016.
 */
public class UploadFile extends AsyncTask<Void, Void, Boolean>{

    private DropboxAPI dropboxAPI;
    private String path;
    private Context context;
    private String file;

    public UploadFile(Context context, String file) {
        super();
        this.dropboxAPI = GpsData.getDropboxApi();
        this.path = Constants.DROPBOX_FILE_DIR;
        this.context = context;
        this.file = file;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final File tempDropboxDirectory = context.getCacheDir();
        File externalStorageDir = new File(Constants.ROOT_DIRECTORY, "Notes");
        File myFile = null;
        String targetFile = "";
        if(file.equals("track_time")) {
            myFile = new File(externalStorageDir, Constants.TRACK_TIME);
            targetFile = Constants.TRACK_TIME;
        }
        else if(file.equals("call_logs")){
            myFile = new File(externalStorageDir , Constants.CALL_LOGS);
            targetFile = Constants.CALL_LOGS;
        }

        //FileWriter fileWriter = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(myFile);
            dropboxAPI.putFileOverwrite(path + targetFile, fileInputStream,
                    myFile.length(), null);
            //myFile.delete();

            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();

        } catch (DropboxException de) {
            de.printStackTrace();
            // TODO: handle exception
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result) {
            Toast.makeText(context, "File has been uploaded!",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Error occured while processing the upload request",
                    Toast.LENGTH_LONG).show();
        }
    }
}
