package com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.Constants;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.Constants_d;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.Utils.Utilities;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.service.BatteryDisconnectDataService;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.BatteryData;

public class BatteryDisconnectData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_disconnect_data);

        /*
        Button submitButton = (Button)findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText batteryLevel = (EditText) findViewById(R.id.batterylevel);
                String batteryLevelText = batteryLevel.getText().toString();
                if (Utilities.isNumeric(batteryLevel.getText().toString())) {
                    Intent intent = new Intent(BatteryDisconnectData.this, BatteryDisconnectDataService.class);
                    intent.putExtra(Constants_d.BATTERY_LEVEL, Integer.parseInt(batteryLevelText.toString()));
                    startService(intent);
                } else {
                    Toast.makeText(BatteryDisconnectData.this.getApplicationContext(), "Battery level should be a number", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button endButton = (Button)findViewById(R.id.endButton);
        endButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent = new Intent(BatteryDisconnectData.this, BatteryDisconnectDataService.class);
                stopService(intent);


            }
        });*/


        ToggleButton toggleRecepie = (ToggleButton) findViewById(R.id.toggle_recepie);
        toggleRecepie.setChecked(BatteryData.isBatteryDisconnectWifi());

        toggleRecepie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    EditText batteryLevel = (EditText) findViewById(R.id.batterylevel);
                    String batteryLevelText = batteryLevel.getText().toString();
                    if (Utilities.isNumeric(batteryLevel.getText().toString())) {
                        BatteryData.setIsBatteryDisconnectWifi(true);
                        Intent intent = new Intent(BatteryDisconnectData.this, BatteryDisconnectDataService.class);
                        intent.putExtra(Constants.BATTERY_LEVEL, Integer.parseInt(batteryLevelText.toString()));
                        startService(intent);


                    } else {
                        Toast.makeText(BatteryDisconnectData.this.getApplicationContext(), "Battery level should be a number", Toast.LENGTH_LONG).show();
                    }

                } else {
                    BatteryData.setIsBatteryDisconnectWifi(false);
                    Intent intent = new Intent(BatteryDisconnectData.this, BatteryDisconnectDataService.class);
                    stopService(intent);
                }
            }
        });
    }
}
