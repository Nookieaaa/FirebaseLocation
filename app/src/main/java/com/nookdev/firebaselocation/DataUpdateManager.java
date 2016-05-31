package com.nookdev.firebaselocation;


import android.support.annotation.Nullable;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.nookdev.firebaselocation.interfaces.IUpdate;
import com.nookdev.firebaselocation.interfaces.OnItemSelectedCallback;
import com.nookdev.firebaselocation.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataUpdateManager implements ChildEventListener {
    private OnItemSelectedCallback mItemSelectedCallback;
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
    private User mCurrentSelectedUser;

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
        switch (action){
            case IUpdate.ADD:{
                User user = dataSnapshot.getValue(User.class);
                mUsersMap.put(key,user);
                if(!mData.contains(key)) {
                    mData.add(key);
                    for(IUpdate consumer:mConsumers){
                        consumer.onItemAdded(mData.indexOf(key));
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

    @Nullable
    public User getUserAt(int index){
        if(mData.size()>0)
            return mUsersMap.get(mData.get(index));
        else
            return null;
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

    public void setCurrentSelectedUser(String name){
        mCurrentSelectedUser = mUsersMap.get(name);
        if(mItemSelectedCallback!=null)
            mItemSelectedCallback.onItemSelected(mCurrentSelectedUser);
    }

    public void setItemSelectedCallback(OnItemSelectedCallback callback){
        mItemSelectedCallback = callback;
    }

    @Nullable
    public User getCurrentSelectedUser(){
        return mCurrentSelectedUser;
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
