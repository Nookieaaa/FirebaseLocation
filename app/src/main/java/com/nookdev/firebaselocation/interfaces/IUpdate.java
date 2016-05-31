package com.nookdev.firebaselocation.interfaces;


import com.firebase.client.DataSnapshot;

public interface IUpdate {
    int ADD = 1;
    int REMOVE = 2;

    void onDataUpdated(DataSnapshot dataSnapshot,int action);
}
