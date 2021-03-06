package com.example.aniket.businga;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class AdminMainActivity extends AppCompatActivity implements View.OnClickListener {

    Button new_notification;
    Button home_page;
    Button new_poll;
    Button old_poll_results;
    Button logout;
    Button update_driver;
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();
    GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        setContentView(R.layout.admin_main_activity);
        logout = findViewById(R.id.adminLogout);
        update_driver = findViewById(R.id.update_driver);
        update_driver.setOnClickListener(this);
        logout.setOnClickListener(this);
        new_notification = findViewById(R.id.new_notification);
        home_page = findViewById(R.id.home_page);
        new_poll = findViewById(R.id.new_poll);
        old_poll_results = findViewById(R.id.old_poll);
        home_page.setOnClickListener(this);
        new_notification.setOnClickListener(this);
        new_poll.setOnClickListener(this);
        old_poll_results.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = getIntent();
        switch (view.getId()){
            case R.id.new_notification:
                intent = new Intent(this, AdminNewNotification.class);
                break;
            case R.id.home_page:
                intent = new Intent(this, HomePage.class);
                break;
            case R.id.new_poll:
                intent = new Intent(this, AdminNewPoll.class);
                break;
            case R.id.old_poll:
                intent = new Intent(this, AdminPollResult.class);
                break;
            case R.id.update_driver:
                intent = new Intent(this, AdminUpdateDriver.class);
                break;
            case R.id.adminLogout:
                logoutFunction();
                break;

        }
        startActivity(intent);
    }

    private void logoutFunction(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Intent intent = new Intent(getApplicationContext(), Loginpage.class);
                SharedPreferences sharedPreferences = getSharedPreferences("Mypref", MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("isLogin", false).commit();
                startActivity(intent);
                finish();
            }
        });
    }
}
