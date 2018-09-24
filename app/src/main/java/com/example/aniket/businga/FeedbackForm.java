package com.example.aniket.businga;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FeedbackForm extends AppCompatActivity {

    ProgressDialog progressDialog;
    EditText feedback;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);
        feedback = findViewById(R.id.feedbackReview);
        button = findViewById(R.id.feedbackSubmit);
        SharedPreferences sharedPreferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString("Name", " ");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                String complaint;
                complaint = feedback.getText().toString();

                if (complaint.trim().length()==0) {
                    Toast.makeText(getApplicationContext(), "Enter something!", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog=new ProgressDialog(getApplicationContext());
                    progressDialog.setTitle("Authenticate");;
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();
                    String currentDateString = DateFormat.getDateTimeInstance().format(new Date());
                    postNewComment(getApplicationContext(), complaint, currentDateString, name);
                }
            }
        });
    }
    public void postNewComment(Context context, final String complaint, final String date, final String name){

        Log.i(TAG, "getParams: " + complaint);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST,"https://script.google.com/macros/s/AKfycby0Rze19m7yU8aTYRAFfJDfgDhmjUPPiWFDp2NwfGL23CBU4ts/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Do something when response recieved
                if(progressDialog!=null)
                {
                    progressDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if(progressDialog!=null)
                    progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("complaint",complaint);
                params.put("name", name);
                Log.i(TAG, "getParams: " + complaint);
                params.put("date",date);
                Log.i(TAG, "getParams: " + date);
                params.put("id", "1RcWIDwBAK4Iluw3hMovmcAMDwHVdvli7BeHP_mSr5h4");
                return params;
            }
        };
        queue.add(sr);

    }
}
