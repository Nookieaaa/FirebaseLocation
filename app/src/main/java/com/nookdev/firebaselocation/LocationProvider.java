package com.nookdev.firebaselocation;


import android.content.Context;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

public class LocationProvider {
    ReactiveLocationProvider mReactiveLocationProvider;
    private static LocationProvider sInstance = new LocationProvider();

    public static LocationProvider getInstance() throws IllegalStateException {
        if(sInstance.mReactiveLocationProvider==null)
            throw new IllegalStateException("init missed");
        return sInstance;
    }

    private LocationProvider() {
    }

    public static void init(Context context){
        sInstance.mReactiveLocationProvider = new ReactiveLocationProvider(context);
    }
}
