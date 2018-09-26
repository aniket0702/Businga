package com.example.aniket.businga;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
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

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SplashScreen extends AppCompatActivity {
    // private final int SPLASH_DISPLAY_LENGTH = 4000;
    VideoView videoView;
    int flg=0,flg2=0;
    BusDetails bus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        getSupportActionBar().hide();

        bus=new BusDetails(this);

        videoView = (VideoView)findViewById(R.id.videoView);

        Uri video = Uri.parse("android.resource://"+getPackageName() + "/"+R.raw.video);
        videoView.setVideoURI(video);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
               flg=1;
                Log.d("tag","Anim: "+String.valueOf(flg)+String.valueOf(flg2));
               if(flg2==1)
               {
                   Log.d("tag",String.valueOf(flg)+String.valueOf(flg2));
                   startActivity(new Intent(SplashScreen.this, Loginpage.class));
                   finish();
               }
            }
        });
        postNewComment();
        videoView.start();
       /* new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this,Loginpage.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH); */


    }
    public void postNewComment(){

        RequestQueue queue = Volley.newRequestQueue(SplashScreen.this);
        StringRequest sr = new StringRequest(Request.Method.POST,"https://wwwbusingacom.000webhostapp.com/get_driver.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Do something when response recieved

                String driv[]=response.split(";");

                for(String d:driv)
                {
                    String det[]=d.split(",");
                    bus.updateBus(det[0],det[1],det[2],Integer.parseInt(det[3]));
                }


                Log.d("tag","Volley: "+String.valueOf(flg)+String.valueOf(flg2));
                flg2=1;
                if(flg==1) {
                    Log.d("tag",String.valueOf(flg)+String.valueOf(flg2));
                    startActivity(new Intent(SplashScreen.this, Loginpage.class));
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag","Errroroor");
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("complaint","haha");
                return params;
            }
        };
        queue.add(sr);

    }

}