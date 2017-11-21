package co.awgm.charged;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    final static String M = "MAIN_ACTIVITY";
    Toolbar toolbar;
    Fragment devices;
    Fragment places;

    FragmentTransaction bottomNav;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            bottomNav = fragmentManager.
                    beginTransaction();
            switch (item.getItemId()) {
                case R.id.nav_devices:
                    if(devices == null){
                        Log.d(M,"devices fragment was null");
                        devices = new Fragment();
                    }
                    if(devices != null) {
                        //Log.d(M,"serving devices fragment");
                        bottomNav.replace(R.id.content, devices);
                        bottomNav.commit();
                    }
                    return true;

                case R.id.nav_places:
                    if(places == null){
                        Log.d(M,"places fragment was null");
                        places = new Fragment();
                    }
                    if(places != null) {
                        //Log.d(M,"serving places fragment");
                        bottomNav.replace(R.id.content, places);
                        bottomNav.commit();
                    }
                    return true;
            }
            return false;
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        bottomNav = fragmentManager.
                beginTransaction();
        if(devices == null){
            Log.d(M,"ONE TIME: devices fragment was null");
            devices = new Fragment();
        }
        if(devices != null) {
            Log.d(M,"ONE TIME: serving devices fragment");
            bottomNav.add(R.id.content, devices);
            bottomNav.commit();
        }




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(M, "onOptionsItemSelected()");
        switch (item.getItemId()) {
            case R.id.action_settings:
                Log.d(M, "onOptionsItemSelected():case:action_settings");
                Intent toolbarSettings = new Intent(this,
                        SettingsActivity.class);
                startActivity(toolbarSettings);
                //Toast.makeText(this, "Pretend the Settings opened", Toast.LENGTH_LONG).show();
                // User chose the "Settings" item, show the app settings UI...
                return true;
            //case R.id.option_get_place:
                //Handles a click on the menu option to get a place.
                //showCurrentPlace();
                //return true;
            //May be redundant as the nearby bottom navigation goes to the same place
            // and it's much closer to the users thumb

            case R.id.action_nearby:
                // User chose the "Nearby" item, open the map
                Intent toolbarNearby = new Intent(this,
                        ChargedMapsActivity.class);
                startActivity(toolbarNearby);
                //Toast.makeText(this, "Pretend the Map opened", Toast.LENGTH_LONG).show();
                Log.d(M, "onOptionsItemSelected():case:action_nearby");
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(M, "OnCreateOptionsMenu()");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }




}
