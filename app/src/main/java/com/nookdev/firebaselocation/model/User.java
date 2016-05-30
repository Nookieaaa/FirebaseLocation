package com.nookdev.firebaselocation.model;


import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class User {
    private int mId;
    private String mName;
    private LatLng mLocation;

    public User(String name,Location location){
        mName = name;
        //mLocation = new LatLng(location.getLatitude(),location.getLongitude());
    }

    public int getId() {
        return mId;
    }

    public LatLng getLocation() {
        return mLocation;
    }

    public String getName() {
        return mName;
    }
}
