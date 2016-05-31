package com.nookdev.firebaselocation;


import android.app.Activity;
import android.content.IntentSender;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.nookdev.firebaselocation.interfaces.IOnLocationDetected;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LocationProvider {
    private Subscription mSubscription;
    private IOnLocationDetected mCallback;
    private ReactiveLocationProvider mReactiveLocationProvider;
    public static final int REQUEST_CHECK_SETTINGS = 555;



    public LocationProvider(android.content.Context context) {
        mReactiveLocationProvider = new ReactiveLocationProvider(context);
    }


    private LocationRequest buildLocationRequest(){
        return LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setNumUpdates(1);
    }

    public void getLastLocation(Activity activity,IOnLocationDetected callback){
        mCallback = callback;
        mSubscription = mReactiveLocationProvider.checkLocationSettings(
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(buildLocationRequest())
                        .setAlwaysShow(true)
                        .build())
                .doOnNext(locationSettingsResult -> {
                    Status status = locationSettingsResult.getStatus();
                    if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                        try {
                            status.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException th) {
                            Log.e("MainActivity", "Error opening settings activity.", th);
                        }
                    }
                })
                .flatMap(locationSettingsResult ->
                mReactiveLocationProvider.getUpdatedLocation(buildLocationRequest()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::notifyLocationFound,
                        Throwable::printStackTrace);
    }

    private void notifyLocationFound(Location location){
        if(mCallback!=null)
            mCallback.onLocationDetected(location);
    }
}
