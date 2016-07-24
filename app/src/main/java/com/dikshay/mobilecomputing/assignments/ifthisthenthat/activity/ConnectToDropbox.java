package com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.Constants;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.GpsData;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.TokenPair;

public class ConnectToDropbox extends AppCompatActivity implements View.OnClickListener{

    //private LinearLayout container;
    private DropboxAPI dropboxAPI;
    private boolean isUserLoggedIn;
    private Button connectWithDropbox;
    //private Button uploadFileButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_dropbox);

        connectWithDropbox = (Button) findViewById(R.id.connect_with_dropbox);
        connectWithDropbox.setOnClickListener(this);
        //uploadFileButton = (Button) findViewById(R.id.upload_file);
        //uploadFileButton.setOnClickListener(this);

        loggedIn(false);

        AppKeyPair appKeyPair = new AppKeyPair(Constants.ACCESS_KEY, Constants.ACCESS_SECRET);
        AndroidAuthSession session;
        SharedPreferences prefs = getSharedPreferences(Constants.DROPBOX_NAME, 0);
        String key = prefs.getString(Constants.ACCESS_KEY, null);
        String secret = prefs.getString(Constants.ACCESS_SECRET, null);

        if(key != null && secret != null){
            AccessTokenPair token = new AccessTokenPair(key, secret);
            session = new AndroidAuthSession(appKeyPair, token);
        }
        else{
            session = new AndroidAuthSession(appKeyPair);
        }
        dropboxAPI = new DropboxAPI(session);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AndroidAuthSession session = (AndroidAuthSession)dropboxAPI.getSession();
        if(session.authenticationSuccessful()){

            try {
                session.finishAuthentication();

                TokenPair tokens = session.getAccessTokenPair();
                SharedPreferences prefs = getSharedPreferences(Constants.DROPBOX_NAME, 0);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(Constants.ACCESS_KEY, tokens.key);
                editor.putString(Constants.ACCESS_SECRET, tokens.secret);
                editor.commit();

                loggedIn(true);
            } catch (IllegalStateException e) {
                Toast.makeText(this, "Error during Dropbox auth", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Log.d("Upload To Dropbox", "onResume Exception");

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.connect_with_dropbox:
                if(isUserLoggedIn){
                    dropboxAPI.getSession().unlink();
                    loggedIn(false);
                } else {
                    AndroidAuthSession a = (AndroidAuthSession)dropboxAPI.getSession();
                            ((AndroidAuthSession) dropboxAPI.getSession())
                            .startAuthentication(ConnectToDropbox.this);
                }
                GpsData.setDropboxApi(dropboxAPI);
                GpsData.setRecordGymTime(true);
                break;
            default:
                break;
        }
    }

    public void loggedIn(boolean userLoggedIn){
        isUserLoggedIn = userLoggedIn;
        //uploadFileButton.setEnabled(userLoggedIn);
        //uploadFileButton.setBackgroundColor(userLoggedIn? Color.BLUE : Color.GRAY);
    }
}
