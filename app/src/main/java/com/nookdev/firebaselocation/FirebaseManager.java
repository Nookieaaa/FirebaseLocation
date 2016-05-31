package com.nookdev.firebaselocation;


import android.support.annotation.Nullable;

import com.firebase.client.Firebase;
import com.nookdev.firebaselocation.model.User;

public class FirebaseManager {

//    public static Single<Firebase> getUsersRef(){
//        return Observable.just(getUsersPath()).toSingle();
//    }

    @Nullable
    public static User getLastUser(){

        return null;
    }

    public static void saveUser(User user){
        getUsersPath().push().child(user.getName()).setValue(user);
    }

    public static Firebase getUsersPath(){
        Firebase usersRef =  getFirebase().child(User.FIREBASE_ALIAS);
        usersRef.keepSynced(true);
        return usersRef;
    }

    private static Firebase getFirebase(){
        Firebase firebase = new Firebase(Config.FIREBASE_URL);
        return firebase;
    }

}
