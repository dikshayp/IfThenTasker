package com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.AlarmData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.broadcast.weatherReceiver;

import java.util.Calendar;

public class WeatherTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_task);
        final Button weatherbutton = (Button) findViewById(R.id.weatherbutton);
        final Spinner wspinner = (Spinner) findViewById(R.id.states);
        final DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        final TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        final Calendar calendar = Calendar.getInstance();
        weatherbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), weatherReceiver.class);
                System.out.print(AlarmData.STATE);
                Log.d("weather", String.valueOf(wspinner.getSelectedItem()));
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Toast.makeText(WeatherTask.this, "checking alarm", Toast.LENGTH_SHORT).show();
                //get time and data and set in calender
                calendar.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), tp.getCurrentHour(), tp.getCurrentMinute());
                calendar.add(Calendar.SECOND, 10);
                Toast.makeText(WeatherTask.this, calendar.toString(), Toast.LENGTH_SHORT).show();
                //setalarm
                AlarmData.STATE = wspinner.getSelectedItem().toString();
                System.out.println(AlarmData.STATE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        PendingIntent.getBroadcast(WeatherTask.this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
                Log.d("weather", String.valueOf(calendar.get(Calendar.MONTH)));
                //Intent wintent = new Intent(getCurrentContext(),WeatherServiceAsync.class);
                //startService(wintent);

            }
        });
    }

}
