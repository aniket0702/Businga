package com.example.aniket.businga;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class AdminNewNotification extends AppCompatActivity {

    ProgressDialog progressDialog;
    private String header;
    private String body;
    EditText notification_header;
    EditText notification_body;
    Button send_notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_notification);
        notification_header = (EditText)findViewById(R.id.notification_header);
        notification_body = (EditText)findViewById(R.id.notification_body);
        send_notification = findViewById(R.id.send_notification);
        send_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notification_body.getText().toString().trim().length() != 0 && notification_header.getText().toString().trim().length() !=0){
                    progressDialog = new ProgressDialog(AdminNewNotification.this);
                    progressDialog.setTitle("Sending Notification");
                    progressDialog.setMessage("Just a moment...");
                    progressDialog.show();                    sendNotification();
                }else
                {
                    Toast.makeText(AdminNewNotification.this, "Cant leave fields empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendNotification(){
        header = notification_header.getText().toString().trim();
        body = notification_body.getText().toString().trim();
        JSONObject bodyObject = new JSONObject();

        try {
            bodyObject.put("to", "/topics/all");
            JSONObject notification = new JSONObject();
            notification.put("body", body);
            notification.put("title", header);
            notification.put("content_available", true);
            notification.put("priority","high");
            bodyObject.put("notification", notification);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = bodyObject.toString();
        Log.i(TAG, "sendNotification: " + mRequestBody);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, "https://fcm.googleapis.com/fcm/send", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: send notification" + response);
                addNotification();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if(progressDialog!=null)
                {
                    progressDialog.dismiss();
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                params.put("Authorization", "key=AIzaSyB9AJqWjjAj29kHPdUjd8sEdK6lIKHsu0Y");
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }

        };
        queue.add(sr);
    }
    private void addNotification(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, "https://wwwbusingacom.000webhostapp.com/notifications.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);
                if(progressDialog!=null)
                {
                    progressDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                    startActivity(intent);
                    finish();
                }
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
                params.put("header", header);
                params.put("body", body);
                return params;
            }
        };
        queue.add(sr);
    }

}
