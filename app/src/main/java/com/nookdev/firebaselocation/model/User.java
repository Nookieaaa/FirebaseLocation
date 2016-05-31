package com.nookdev.firebaselocation.model;


import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.nookdev.firebaselocation.Config;

public class User {

    public static final String FIREBASE_ALIAS = "Users";

    private String name;
    private float lat;
    private float lng;

    public User(){}

    public User(String name, float lat, float lng){
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public LatLng toLatLng() {
        return new LatLng(lat, lng);
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public String getName() {
        return name;
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
        data.putFloat(Config.EXTRA_LAT,getLat());
        data.putFloat(Config.EXTRA_LNG,getLng());

        return data;
    }
}
