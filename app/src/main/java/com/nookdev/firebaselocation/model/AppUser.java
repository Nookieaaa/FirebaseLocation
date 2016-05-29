package com.nookdev.firebaselocation.model;

import android.location.Location;


public class AppUser {
    private String mId;
    private Location mLocation;
    private String name;

    public AppUser(Location location, String name) {
        mLocation = location;
        this.name = name;
    }

    public Location getLocation() {
        return mLocation;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
}
