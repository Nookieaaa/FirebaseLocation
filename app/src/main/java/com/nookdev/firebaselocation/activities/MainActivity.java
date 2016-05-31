package com.nookdev.firebaselocation.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.nookdev.firebaselocation.Config;
import com.nookdev.firebaselocation.LocationProvider;
import com.nookdev.firebaselocation.R;
import com.nookdev.firebaselocation.interfaces.IOnLocationDetected;
import com.nookdev.firebaselocation.model.User;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private boolean mTwoPaneMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> MainActivityPermissionsDispatcher.registerUserWithCheck(this));

        mTwoPaneMode = findViewById(R.id.map_fragment)!=null;
        MainActivityPermissionsDispatcher.registerUserWithCheck(this);
    }

    @Nullable
    public User getCurrentUser(){
        SharedPreferences preferences = getSharedPreferences(Config.PREFS_NAME,MODE_PRIVATE);
        String name = preferences.getString(Config.PREFS_KEY_USERNAME,"");
        float lat = preferences.getFloat(Config.PREFS_KEY_LAT,0);
        float lng = preferences.getFloat(Config.PREFS_KEY_LNG,0);

        if (name.length()>0&&lat!=0&&lng!=0)
            return new User(name,new LatLng(lat,lng));
        return null;
    }

    private boolean isRegistered(){
        return getCurrentUser()!=null;
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void registerUser(){
        if(!isRegistered()){
            new LocationProvider(this).getLastLocation(this,new IOnLocationDetected() {
                @Override
                public void onLocationDetected(Location location) {
                    User currentUser = new User("test",new LatLng(location.getLatitude(),location.getLongitude()));
                    String s = currentUser.getName();
                }
            });
        }
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    public void onLocationDenied(){
        Toast.makeText(this,"Location permission denied",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LocationProvider.REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        // All required changes were successfully made
                        Log.d("Main Activity","User enabled location");
                        break;
                    case RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(this,"Location disabled, you`re not registered",Toast.LENGTH_SHORT).show();
                        Log.d("Main Activity","User Cancelled enabling location");
                        break;
                    default:
                        break;
                }
                break;
        }
    }

}
