package com.nookdev.firebaselocation.interfaces;


public interface IUpdate {
    int ADD = 1;
    int REMOVE = 2;

    void onItemAdded(int position);
    void onItemRemoved(int position);
}
