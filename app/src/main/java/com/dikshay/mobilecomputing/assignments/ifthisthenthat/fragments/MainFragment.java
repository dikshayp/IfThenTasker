package com.dikshay.mobilecomputing.assignments.ifthisthenthat.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity.CallLogsDropbox;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity.ClassCancelCall;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity.ClassRingerToggle;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity.GymTrackTime;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity.HomeWifiToggle;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity.MailTask;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity.NotifyFriendLocation;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity.SmsTask;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity.WeatherTask;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.adapter.MyArrayAdapter;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity.BatteryDisconnectData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity.BatteryReduceBrightness;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity.BatterySendMessage;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity.NewImageUploadToDrive;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity.NewImageUploadToDropbox;


/**
 * Created by Dikshay on 4/12/2016.
 */
public class MainFragment extends Fragment{
    private String[] data={"Reduce brightness on battery","Disconnect wifi on battery","Send Message on battery",
    "Upload New Image To Dropbox","Upload New Image to Google Drive", "Home Wifi Toggle", "Class Ringtone Toggle", "Class Do Not Disturb",
            "Gym Track Time", "Mantain Call Logs", "Notify Friend", "Schedule Sms", "Schedule Mail", "Get Weather"};
    private ListView mainFragmentListView;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = layoutInflater.inflate(R.layout.fragment_planet,container,false);

        mainFragmentListView = (ListView)rootView.findViewById(R.id.main_fragment_listView);
        MyArrayAdapter listAdapter = new MyArrayAdapter(getActivity(),data);
        mainFragmentListView.setAdapter(listAdapter);
        mainFragmentListView.setOnItemClickListener(new SlideMenuClickListener());
        return rootView;
    }
    public class SlideMenuClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Log.e("ListView item clicked","clicked");
            Log.d("ListView",String.valueOf(position));
            Intent intent;
            switch(position)
            {
                case 0:
                    Log.d("ListView","battery screen brightness clicked");
                    intent = new Intent(getActivity(), BatteryReduceBrightness.class);
                    startActivity(intent);
                    break;
                case 1:
                    Log.d("ListView","battery disconnect data clicked");
                    intent = new Intent(getActivity(), BatteryDisconnectData.class);
                    startActivity(intent);
                    break;
                case 2:
                    Log.d("ListView","battery send message clicked");
                    intent = new Intent(getActivity(), BatterySendMessage.class);
                    startActivity(intent);
                    break;
                case 3:
                    Log.d("ListView","Upload To Dropbox");
                    intent = new Intent(getActivity(), NewImageUploadToDropbox.class);
                    startActivity(intent);
                    break;
                case 4:
                    Log.d("ListView", "Upload To Google Drive");
                    intent = new Intent(getActivity(), NewImageUploadToDrive.class);
                    startActivity(intent);
                    break;
                case 5:
                    Log.d("ListView", "Home Wifi Toggle");
                    intent = new Intent(getActivity(), HomeWifiToggle.class);
                    startActivity(intent);
                    break;
                case 6:
                    Log.d("ListView", "Class Ringtone Toggle");
                    intent = new Intent(getActivity(), ClassRingerToggle.class);
                    startActivity(intent);
                    break;
                case 7:
                    Log.d("ListView", "Class Cancel Call Toggle");
                    intent = new Intent(getActivity(), ClassCancelCall.class);
                    startActivity(intent);
                    break;
                case 8:
                    Log.d("ListView", "Track Gym Time");
                    intent = new Intent(getActivity(), GymTrackTime.class);
                    startActivity(intent);
                    break;
                case 9:
                    Log.d("ListView", "Upload Call Logs to Dropbox");
                    intent = new Intent(getActivity(), CallLogsDropbox.class);
                    startActivity(intent);
                    break;
                case 10:
                    Log.d("ListView", "Notify Friend Of Your Location");
                    intent = new Intent(getActivity(), NotifyFriendLocation.class);
                    startActivity(intent);
                    break;
                case 11:
                    Log.d("ListView", "Schedule Sms");
                    intent = new Intent(getActivity(), SmsTask.class);
                    startActivity(intent);
                    break;
                case 12:
                    Log.d("ListView", "Schedule Mail");
                    intent = new Intent(getActivity(), MailTask.class);
                    startActivity(intent);
                    break;
                case 13:
                    Log.d("ListView", "Weather Task");
                    intent = new Intent(getActivity(), WeatherTask.class);
                    startActivity(intent);
                    break;
                default:
                    Log.d("ListView","Error in input");
                    break;
            }
        }
    }

}
