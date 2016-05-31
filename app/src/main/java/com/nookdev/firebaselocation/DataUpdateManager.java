package com.nookdev.firebaselocation;


import android.support.annotation.Nullable;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.nookdev.firebaselocation.interfaces.IUpdate;
import com.nookdev.firebaselocation.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataUpdateManager implements ChildEventListener {
    List<IUpdate> mConsumers;
    private HashMap<String,User> mUsersMap = new HashMap<String,User>(){
        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            return super.equals(object);
        }
    };
    private List<String> mData = new ArrayList<>();

    private static DataUpdateManager sInstance = new DataUpdateManager();

    public static DataUpdateManager getInstance() {
        return sInstance;
    }

    private DataUpdateManager() {
        mConsumers = new ArrayList<>();
    }

    public void addConsumer(IUpdate consumer){
        if(!mConsumers.contains(consumer))
            mConsumers.add(consumer);
        if(mConsumers.size()==1){
            subscribeUpdates();
        }
    }

    private void updateConsumers(DataSnapshot dataSnapshot, int action){
        String key = dataSnapshot.getKey();
        for(DataSnapshot userSnapshot:dataSnapshot.getChildren()){
            switch (action){
                case IUpdate.ADD:{
                    String name = key;
                    double lat = (double)userSnapshot.child("lat").getValue();
                    double lng = (double)userSnapshot.child("lng").getValue();
                    User user = new User(name,(float)lat,(float)lng);
                    mUsersMap.put(key,user);
                    if(!mData.contains(key)) {
                        mData.add(0,key);
                        for(IUpdate consumer:mConsumers){
                            consumer.onItemAdded(0);
                        }
                    }
                    break;
                }
                case IUpdate.REMOVE:{
                    mUsersMap.remove(key);
                    if(mData.contains(key)){
                        int index = mData.indexOf(key);
                        mData.remove(key);
                        for (IUpdate consumer : mConsumers){
                            consumer.onItemRemoved(index);
                        }
                    }
                    break;
                }
            }
        }

    }

    @Nullable
    public User getUserAt(int index){
        return mUsersMap.get(mData.get(index));
    }

    public int getSize(){
        return mData.size();
    }

    public void removeConsumer(IUpdate consumer){
        mConsumers.remove(consumer);
        if(mConsumers.size()==0)
            unsubscribeUpdates();
    }

    public void clear(){
        mConsumers.clear();
    }

    public void subscribeUpdates(){
        FirebaseManager.getUsersPath().addChildEventListener(this);


    }

    public void unsubscribeUpdates(){
        FirebaseManager.getUsersPath().removeEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        updateConsumers(dataSnapshot,IUpdate.ADD);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        updateConsumers(dataSnapshot,IUpdate.REMOVE);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        Log.d("FirebaseAPP","error while updating");
    }
}
