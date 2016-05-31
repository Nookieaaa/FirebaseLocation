package com.nookdev.firebaselocation;


import com.firebase.client.DataSnapshot;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nookdev.firebaselocation.interfaces.IUpdate;
import com.nookdev.firebaselocation.model.User;

import java.util.HashMap;
import java.util.List;

public class MapManager implements IUpdate {
    private GoogleMap mGoogleMap;
    private boolean isBound = false;
    HashMap<User,MarkerOptions> mMarkers;


    public MapManager(GoogleMap map){
        this.mGoogleMap = map;
        isBound = true;
    }

    @Override
    public void onDataUpdated(DataSnapshot dataSnapshot,int action) {
        if(!isBound)
            return;
    }

    public void unBind(){
        mGoogleMap = null;
        isBound = false;
    }

    private void updateList(List<User> users){
       for (User user:users){
           mMarkers.put(user,createMarker(user));
       }

        updateMap();
    }

    private void updateMap(){
        mGoogleMap.clear();
        for(User user:mMarkers.keySet())
            mGoogleMap.addMarker(mMarkers.get(user));
    }

    private MarkerOptions createMarker(User user) {
        MarkerOptions options = new MarkerOptions()
                .position(user.toLatLng())
                .snippet(user.getName());
        return options;
    }
}
