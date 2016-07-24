package com.dikshay.mobilecomputing.assignments.ifthisthenthat.receipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.ImageUpload.ImageUploadDriveService;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.ImageUpload.ImageUploadDropboxService;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.CallLogsData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.UploadData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.googledrive.ApplicationClass;

public class NewImageUploadToDropbox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_image_upload_to_dropbox);

        /*
        Button submitButton = (Button)findViewById(R.id.activate);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(NewImageUploadToDropbox.this, ImageUploadDropboxService.class);
                startService(intent);

            }
        });
        Button deactivateButton = (Button)findViewById(R.id.deactivate);
        deactivateButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent = new Intent(NewImageUploadToDropbox.this,ImageUploadDropboxService.class);
                stopService(intent);
            }
        });*/

        ToggleButton toggleRecepie = (ToggleButton) findViewById(R.id.toggle_recepie);
        toggleRecepie.setChecked(UploadData.isUploadToDropbox());

        toggleRecepie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UploadData.setUploadToDropbox(true);
                    Intent intent = new Intent(NewImageUploadToDropbox.this, ImageUploadDropboxService.class);
                    startService(intent);

                } else {
                    UploadData.setUploadToDropbox(false);
                    Intent intent = new Intent(NewImageUploadToDropbox.this,ImageUploadDropboxService.class);
                    stopService(intent);
                }
            }
        });
        //MediaObserver mediaObserver = new MediaObserver(new Handler());
        //mediaObserver.observe();
    }
}
