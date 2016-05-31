package com.nookdev.firebaselocation;


import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.nookdev.firebaselocation.interfaces.IUpdate;

import java.util.ArrayList;
import java.util.List;

public class UpdateManager implements ChildEventListener {
    List<IUpdate> mConsumers;

    private static UpdateManager sInstance = new UpdateManager();

    public static UpdateManager getInstance() {
        return sInstance;
    }

    private UpdateManager() {
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
        for (IUpdate consumer : mConsumers){
            consumer.onDataUpdated(dataSnapshot, action);
        }
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
