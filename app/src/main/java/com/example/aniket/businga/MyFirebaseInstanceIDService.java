package com.example.aniket.businga;

import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{

    private final String REQ_TOKEN = "REQ_TOKEN";
    @Override
    public void onTokenRefresh() {
        String recent = FirebaseInstanceId.getInstance().getToken();
        Log.d(REQ_TOKEN, recent);
    }



}
