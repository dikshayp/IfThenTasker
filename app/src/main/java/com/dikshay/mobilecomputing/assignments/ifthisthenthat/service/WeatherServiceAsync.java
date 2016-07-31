package com.dikshay.mobilecomputing.assignments.ifthisthenthat.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.AlarmData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by User847 on 4/24/2016.
 */
public class WeatherServiceAsync extends AsyncTask<String,Void,String> {

    HttpURLConnection con = null;
    NotificationManager NM = null;
    Context context;
    public WeatherServiceAsync(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... urls) {
        try {
            String BASE_URL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + AlarmData.STATE +"%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
            con = (HttpURLConnection) (new URL(BASE_URL)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            InputStream is = con.getInputStream();
            BufferedReader reader =new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String webPage = "",data="";

            while ((data = reader.readLine()) != null){
                webPage += data + "\n";
            }
            Log.d("weather",webPage);

            JSONObject  jsonRootObject = new JSONObject(webPage);
            Log.d("Root level",jsonRootObject.toString() );
            JSONObject jsonQuery = (JSONObject)jsonRootObject.get("query");
            JSONObject jsonResults = (JSONObject) jsonQuery.get("results");
            JSONObject jsonChannel = (JSONObject) jsonResults.get("channel");
            JSONObject jsonItem = (JSONObject) jsonChannel.get("item");
            JSONObject jsonCondition = (JSONObject) jsonItem.get("condition");
            // Create custom notification
            String dateString = jsonCondition.getString("date");

            String temperatureString = jsonCondition.getString("temp");
            //and the weather is
            String weather =  jsonCondition.getString("text");
            String Output = temperatureString + " Farenheit" + " " + weather + "Weather" ;

            //String temp = String.valueOf();
            System.out.println("abdul" + Output);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentText(Output)
                            .setContentTitle("IfThenTasker: " + AlarmData.STATE)
                    .setSmallIcon(R.mipmap.ic_launcher);

            Notification notification = builder.build();
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(0, notification);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
