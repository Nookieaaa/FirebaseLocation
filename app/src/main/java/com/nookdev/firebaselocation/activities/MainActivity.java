package com.nookdev.firebaselocation.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nookdev.firebaselocation.Config;
import com.nookdev.firebaselocation.FirebaseManager;
import com.nookdev.firebaselocation.LocationProvider;
import com.nookdev.firebaselocation.R;
import com.nookdev.firebaselocation.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

import static com.nookdev.firebaselocation.R.id.fab;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private boolean mTwoPaneMode = false;
    private Unbinder mUnbinder;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @OnClick(fab)
    public void onFabClick(View v){
        MainActivityPermissionsDispatcher.registerUserWithCheck(this);
        checkFabVisibility();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTwoPaneMode = findViewById(R.id.map_fragment)!=null;
        mUnbinder = ButterKnife.bind(this);
        MainActivityPermissionsDispatcher.registerUserWithCheck(this);
    }

    private void checkFabVisibility(){
        if(isRegistered())
            mFab.hide();
        else if(!mFab.isShown())
            mFab.show();
    }

    @Nullable
    public User getCurrentUser(){
        SharedPreferences preferences = getSharedPreferences(Config.PREFS_NAME,MODE_PRIVATE);
        String name = preferences.getString(Config.PREFS_KEY_USERNAME,"");
        float lat = preferences.getFloat(Config.PREFS_KEY_LAT,0);
        float lng = preferences.getFloat(Config.PREFS_KEY_LNG,0);

        if (name.length()>0&&lat!=0&&lng!=0)
            return new User(name,lat,lng);
        return null;
    }

    private boolean isRegistered(){
        return getCurrentUser()!=null;
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void registerUser(){
        Log.d("location enabled","++++++");
        //if(!isRegistered()){
            new LocationProvider(this).getLastLocation(this, location -> {

                //get user name
                User currentUser = new User("test",(float)location.getLatitude(),(float)location.getLongitude());
                SharedPreferences preferences = getSharedPreferences(Config.PREFS_NAME,MODE_PRIVATE);
                SharedPreferences.Editor ed = preferences.edit();
                ed.putString(Config.PREFS_KEY_USERNAME,currentUser.getName());
                ed.putFloat(Config.PREFS_KEY_LAT,(float)currentUser.getLat());
                ed.putFloat(Config.PREFS_KEY_LNG,(float)currentUser.getLng());
                ed.apply();
                FirebaseManager.saveUser(currentUser);

            });
        //}
        checkFabVisibility();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
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
