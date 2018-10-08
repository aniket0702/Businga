package com.example.aniket.businga;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    //VideoView videoView;
    Button businga;
    ImageView icon;
    TextView display;
    Animation mytransition, fromtop;

    int flg = 0, flg2 = 0;
    BusDetails bus;
    NotificationDetails notificationDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        getSupportActionBar().hide();

        notificationDetails = new NotificationDetails(this);
        bus = new BusDetails(this);

        FirebaseMessaging.getInstance().subscribeToTopic("all")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "subscribed";
                        if (!task.isSuccessful()) {
                            msg = "fail";
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(SplashScreen.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


//
//        videoView = (VideoView)findViewById(R.id.videoView);
//
//        Uri video = Uri.parse("android.resource://"+getPackageName() + "/"+R.raw.video);
//        videoView.setVideoURI(video);
//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//               flg=1;
//                Log.d("tag","Anim: "+String.valueOf(flg)+String.valueOf(flg2));
//
//               {
//                   Log.d("tag",String.valueOf(flg)+String.valueOf(flg2));
//                   startActivity(new Intent(SplashScreen.this, Loginpage.class));
//                   finish();
//               }
//            }
//        });
//        getNotifications();
//        videoView.start();
        businga = (Button) findViewById(R.id.button);
        icon = (ImageView) findViewById(R.id.imageView2);
        display = (TextView) findViewById(R.id.textView);

        mytransition = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        businga.setAnimation(mytransition);
        icon.setAnimation(fromtop);
        display.setAnimation(mytransition);


//        Log.d("tag", String.valueOf(flg) + String.valueOf(flg2));
//        startActivity(new Intent(SplashScreen.this, Loginpage.class));
//        finish();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getNotifications();
                flg = 1;
            }
        }, SPLASH_DISPLAY_LENGTH);


    }

    public void postNewComment() {

        RequestQueue queue = Volley.newRequestQueue(SplashScreen.this);
        StringRequest sr = new StringRequest(Request.Method.POST, "https://wwwbusingacom.000webhostapp.com/get_driver.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Do something when response recieved

                String driv[] = response.split(";");

                for (String d : driv) {
                    String det[] = d.split(",");
                    bus.updateBus(det[0], det[1], det[2], Integer.parseInt(det[3]));
                }

                Log.d("tag", "Volley: " + String.valueOf(flg) + String.valueOf(flg2));
                flg2 = 1;
                if (flg == 1) {
                    Log.d("tag", String.valueOf(flg) + String.valueOf(flg2));
                    startActivity(new Intent(SplashScreen.this, Loginpage.class));
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "Errroroor");
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("complaint", "haha");
                return params;
            }
        };
        queue.add(sr);

    }


    public void getNotifications() {

        RequestQueue queue = Volley.newRequestQueue(SplashScreen.this);
        StringRequest sr = new StringRequest(Request.Method.GET, "https://wwwbusingacom.000webhostapp.com/notifications.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);
                //Do something when response recieved
                String driv[] = response.split("////");
                for (String d : driv) {
                    String det[] = d.split("//");
                    if (det.length == 3) {
                        if (det[1] != "" && det[2] != "") {
                            notificationDetails.add_notification(det[1], det[2]);
                        }
                    }
                }
                postNewComment();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        queue.add(sr);
    }

}