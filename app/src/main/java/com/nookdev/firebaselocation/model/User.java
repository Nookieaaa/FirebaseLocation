package com.nookdev.firebaselocation.model;


import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class User {

    public static final String FIREBASE_ALIAS = "Users";

    private String mId;
    private String mName;
    private LatLng mLocation;

    public User(String name,Location location){
        mName = name;
        //mLocation = new LatLng(location.getLatitude(),location.getLongitude());
    }

    public LatLng getLocation() {
        return mLocation;
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
}
