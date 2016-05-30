package com.nookdev.firebaselocation;


import com.firebase.client.Firebase;
import com.nookdev.firebaselocation.model.User;

public class FirebaseManager {

//    public static Single<Firebase> getUsersRef(){
//        return Observable.just(getUsersPath()).toSingle();
//    }

    public static void saveUser(User user){
        getUsersPath().child(user.getName()).setValue(user);
    }

    public static Firebase getUsersPath(){
        return getFirebase().child(User.FIREBASE_ALIAS);
    }

    private static Firebase getFirebase(){
        return new Firebase(Config.FIREBASE_URL);
    }


}
