package com.nookdev.firebaselocation;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nookdev.firebaselocation.interfaces.IUpdate;
import com.nookdev.firebaselocation.interfaces.OnItemSelectedCallback;
import com.nookdev.firebaselocation.model.User;

import java.util.HashMap;

public class MapManager implements IUpdate,OnItemSelectedCallback {
    private GoogleMap mGoogleMap;
    HashMap<String,Marker> mMarkers = new HashMap<String,Marker>(){
        @Override
        public boolean equals(Object object) {
            return super.equals(object);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    };


    public MapManager(GoogleMap map){
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        this.mGoogleMap = map;
        DataUpdateManager.getInstance().addConsumer(this);
        DataUpdateManager.getInstance().setItemSelectedCallback(this);
        for (int i=0;i<DataUpdateManager.getInstance().getSize();i++) {
            onItemAdded(i);
        }
        if(DataUpdateManager.getInstance().getCurrentSelectedUser()!=null)
            onItemSelected(DataUpdateManager.getInstance().getCurrentSelectedUser());
    }


    @Override
    public void onItemAdded(int position) {
        User user = DataUpdateManager.getInstance().getUserAt(position);
        mMarkers.put(
                user.getName(),
                mGoogleMap.addMarker(createMarker(user))
        );
    }

    @Override
    public void onItemRemoved(int position) {
        User user = DataUpdateManager.getInstance().getUserAt(position);
        try {
            mMarkers.remove(user.getName()).remove();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void unBind(){
        DataUpdateManager.getInstance().removeConsumer(this);
    }

    private MarkerOptions createMarker(User user) {
        MarkerOptions options = new MarkerOptions()
                .position(user.toLatLng())
                .snippet("lat: "+String.valueOf(user.getLat())+" lng: "+String.valueOf(user.getLng()))
                .title(user.getName());
        return options;
    }

    @Override
    public void onItemSelected(User user) {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(user.toLatLng(),20f), new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                Marker marker = mMarkers.get(user.getName());
                if(marker!=null)
                    marker.showInfoWindow();
            }

            @Override
            public void onCancel() {
                Marker marker = mMarkers.get(user.getName());
                if(marker!=null)
                    marker.showInfoWindow();
            }
        });
    }
}
