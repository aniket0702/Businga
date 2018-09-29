package com.example.aniket.businga;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{

    private final String REQ_TOKEN = "REQ_TOKEN";
    @Override
    public void onTokenRefresh() {
        String recent = FirebaseInstanceId.getInstance().getToken();
        Log.d(REQ_TOKEN, recent);


    }
}
