package com.nookdev.firebaselocation.model;


import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.nookdev.firebaselocation.Config;

public class User {

    public static final String FIREBASE_ALIAS = "Users";

    private String mId;
    private String mName;
    private LatLng mLocation;

    public User(String name,LatLng location){
        mName = name;
        mLocation = location;
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

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Bundle toBundle(){
        Bundle data = new Bundle();
        data.putString(Config.EXTRA_NAME,getName());
        data.putFloat(Config.EXTRA_LAT,(float)getLocation().latitude);
        data.putFloat(Config.EXTRA_LNG,(float)getLocation().longitude);

        return data;
    }
}
