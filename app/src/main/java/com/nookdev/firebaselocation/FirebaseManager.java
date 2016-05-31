package com.nookdev.firebaselocation;


import com.firebase.client.Firebase;
import com.nookdev.firebaselocation.model.User;

public class FirebaseManager {


    public static String saveUser(User user){
        Firebase firebase = getUsersPath().push();
        String uid = firebase.getKey();
        user.setName(uid);
        firebase.setValue(user);
        return uid;
    }

    public static Firebase getUsersPath(){
        return getFirebase().child(User.FIREBASE_ALIAS);
    }

    private static Firebase getFirebase(){
        return new Firebase(Config.FIREBASE_URL);
    }

}
