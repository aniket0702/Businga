package com.example.aniket.businga;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ComplaintPortal extends Fragment {
    ProgressDialog progressDialog;
    Button button;
    EditText complaintPortalText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_complaint_portal, container, false);

        complaintPortalText = view.findViewById(R.id.complaintPortalText);
        button = view.findViewById(R.id.complaintSendButton);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Mypref",Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString("Name", " ");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                String complaint;
                complaint = complaintPortalText.getText().toString();

                if (complaint.trim().length()==0) {
                    Toast.makeText(getContext(), "Enter something!", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog=new ProgressDialog(getContext());
                    progressDialog.setTitle("Authenticate");;
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();
                    String currentDateString = DateFormat.getDateTimeInstance().format(new Date());
                    postNewComment(getContext(), complaint, currentDateString, name);
                }
            }
        });
        return view;
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
                    Intent intent = new Intent(getContext(), HomePage.class);
                    startActivity(intent);
                    getActivity().finish();
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
                params.put("id", "1hsCOVzDZOlFZEiZ4k6GnMmqXmjs1HTvt-Rm8aQ_CPh4");
                return params;
            }
        };
        queue.add(sr);
    }
}
