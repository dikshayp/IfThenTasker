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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.AlarmData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.broadcast.alarmReceiver;

import java.util.Calendar;

public class SmsTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_task);
        final Button button = (Button) findViewById(R.id.button);
        final DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        final TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        final TextView message = (TextView) findViewById(R.id.editText);
        final TextView phonenumber = (TextView) findViewById(R.id.editText2);
        final Calendar calendar = Calendar.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                AlarmData.MESSAGE = message.getText().toString();
                AlarmData.PHONE_NUMBER = phonenumber.getText().toString();
                message.clearComposingText();
                phonenumber.clearComposingText();
                Log.d("Message is " + AlarmData.MESSAGE, AlarmData.PHONE_NUMBER);
                Intent intent = new Intent(getApplicationContext(), alarmReceiver.class);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Toast.makeText(SmsTask.this, "checking alarm", Toast.LENGTH_SHORT).show();
                //get time and data and set in calender
                calendar.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), tp.getCurrentHour(), tp.getCurrentMinute());
                calendar.add(Calendar.SECOND, 10);
                Toast.makeText(SmsTask.this, calendar.toString(), Toast.LENGTH_SHORT).show();
                //setalarm
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        PendingIntent.getBroadcast(SmsTask.this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
                Log.d("Bhakti", String.valueOf(calendar.get(Calendar.MONTH)));
                //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                //startService(intent);
            }
        });

    }

}
