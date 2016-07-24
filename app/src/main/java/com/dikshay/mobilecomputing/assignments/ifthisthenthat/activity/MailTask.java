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

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.AlarmData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.emailReceiver;

import java.util.Calendar;

public class MailTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_task);
        final Button mbutton = (Button)findViewById(R.id.mbutton);
        final DatePicker mdp = (DatePicker) findViewById(R.id.mdatePicker);
        final TimePicker mtp = (TimePicker) findViewById(R.id.mtimePicker);
        final TextView mailid = (TextView) findViewById(R.id.meditText);
        final TextView subject = (TextView) findViewById(R.id.meditText2);
        final TextView body = (TextView) findViewById(R.id.meditText3);
        final Calendar mcalendar = Calendar.getInstance();
        mbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //set email data
                AlarmData.MAILID = mailid.getText().toString();
                AlarmData.SUBJECT = subject.getText().toString();
                AlarmData.BODY = body.getText().toString();
                //create intent for receiver
                Intent intent = new Intent(getApplicationContext(), emailReceiver.class);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Toast.makeText(MailTask.this, "checking alarm", Toast.LENGTH_SHORT).show();
                //get time and data and set in calender
                mcalendar.set(mdp.getYear(), mdp.getMonth(), mdp.getDayOfMonth(), mtp.getCurrentHour(), mtp.getCurrentMinute());
                mcalendar.add(Calendar.SECOND, 10);
                alarmManager.set(AlarmManager.RTC_WAKEUP, mcalendar.getTimeInMillis(),
                        PendingIntent.getBroadcast(MailTask.this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
                Toast.makeText(MailTask.this, mcalendar.toString(), Toast.LENGTH_SHORT).show();
                //setalarm
                Log.d("Bhakti", String.valueOf(mcalendar.get(Calendar.MONTH)));
            }
        });


    }

}
