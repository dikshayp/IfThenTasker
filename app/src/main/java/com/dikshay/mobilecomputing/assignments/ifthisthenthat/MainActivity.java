package com.dikshay.mobilecomputing.assignments.ifthisthenthat;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.adapter.MyArrayAdapter;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.battery.BatteryService;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.data.Constants;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.fragments.MainFragment;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.service.LocationService;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView leftDrawerList;
    //private ArrayAdapter<String> navigationDrawerAdapter;
    private String[] leftSliderData = {"Home","About Us","Contact"};
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           String message = intent.getStringExtra("Message");
            Toast.makeText(context, "service starting", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                /*
        * code for actionbar drawer
        * */
        createView();

        if(toolbar !=null)
        {
            toolbar.setTitle("IFFT");
            setSupportActionBar(toolbar);
        }
        initDrawer();
        /*
        *end code for actionbar drawer
         */
        Fragment fragment = new MainFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit();


        //needed for android v 6.0 and sdk v 24.0
        checkPermissions();
        //Intent intent = new Intent(this, BatteryService.class);
        //startService(intent);


    }

    private void checkPermissions(){
        //Location Permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
        }

        //Access External Storage Permissions
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constants.MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
        }
    }
    private void createView()
    {
        leftDrawerList = (ListView)findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        // navigationDrawerAdapter = new ArrayAdapter<String>(Calculator.this,android.R.layout.simple_list_item_1,leftSliderData);
        MyArrayAdapter navigationDrawerAdapter  =  new MyArrayAdapter(MainActivity.this,leftSliderData);
        leftDrawerList.setAdapter(navigationDrawerAdapter);
        leftDrawerList.setOnItemClickListener(new SlideMenuClickListener());
    }
    public class SlideMenuClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Log.e("ListView item clicked","clicked");
            displayView(position);
        }
    }
    public void displayView(int position)
    {
        Fragment fragment = null;
        switch(position)
        {
            case 0:
                fragment = new MainFragment();
                break;
            case 1:
                fragment = new MainFragment();
                break;
            case 2:
                fragment = new MainFragment();
                break;


            default: break;
        }
        if(fragment!=null)
        {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit();
            RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.left_drawer_relative_layout);
            // drawerLayout.closeDrawer(leftDrawerList);
            drawerLayout.closeDrawer(relativeLayout);
        }
        else
        {
            Log.e("IFFT","error");
        }


    }

    private void initDrawer()
    {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("UI update Broadcast"));
    }

    @Override
    public void onPause()
    {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
