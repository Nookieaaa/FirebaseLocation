package com.nookdev.firebaselocation;


import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.nookdev.firebaselocation.interfaces.IUpdate;

import java.util.ArrayList;
import java.util.List;

public class UpdateManager implements ValueEventListener {
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

    private void updateConsumers(DataSnapshot dataSnapshot){
        for (IUpdate consumer : mConsumers){
            consumer.onDataUpdated(dataSnapshot);
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
        FirebaseManager.getUsersPath().addValueEventListener(this);
    }

    public void unsubscribeUpdates(){
        FirebaseManager.getUsersPath().removeEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        updateConsumers(dataSnapshot);
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        Log.d("FirebaseApp","update error");
    }
}
