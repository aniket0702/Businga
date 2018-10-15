package com.example.aniket.businga;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;


public class Loginpage extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private boolean isLogin;
    private boolean isAdmin;
    private TextView loginmessage;
    private SignInButton signin;
    private Button signout;
    private String email;
    static GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        loginmessage = (TextView)findViewById(R.id.text_view);
        signin = (SignInButton)findViewById(R.id.googlelogin);
        signin.setOnClickListener(this);
        signout = (Button)findViewById(R.id.signoutbutton);
        signout.setOnClickListener(this);
        signout.setVisibility(View.GONE);

        loginmessage.setVisibility(View.GONE);
        SharedPreferences sharedpreferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        isLogin = sharedpreferences.getBoolean("isLogin", false);
        isAdmin = sharedpreferences.getBoolean("isAdmin", false);
        updateUI();
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();


        }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.googlelogin:
                signIn();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn()
    {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(REQ_CODE == requestCode)
        {
            GoogleSignInResult result  = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

    private void handleResult(GoogleSignInResult result)
    {
        if(result.isSuccess())
        {
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            loginmessage.setText(name + " " + email);
            SharedPreferences sharedpreferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            isLogin = true;
            editor.putBoolean("isLogin", true);
            editor.putString("Name", name);
            editor.putString("email", email);


            if(email.equals("aniketagarwal11133@gmail.com") || email.equals("varshneymayur31@gmail.com"))
            {
                isAdmin = true;
                editor.putBoolean("isAdmin", true);
            }
            else{
                isAdmin = false;
                editor.putBoolean("isAdmin", false);
            }
            updateUI();
            editor.commit();
        }
    }

    private void updateUI()
    {
        if(isLogin)
        {
            if(isAdmin){
                Intent intent = new Intent(this, AdminMainActivity.class);
                startActivity(intent);
                this.finish();
            }else {
                Intent intent = new Intent(this, HomePage.class);
                startActivity(intent);
                this.finish();
            }
        }
        else
        {
            signin.setVisibility(View.VISIBLE);
        }
    }
}
