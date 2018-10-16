package com.example.aniket.businga;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AdminPollResult extends AppCompatActivity {
    Button b;
    TextView t;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_poll_result);
        b = (Button)findViewById(R.id.show_result);
        t = (TextView) findViewById(R.id.poll_result);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResult();
            }
        });
    }

    public void showResult() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.GET, "https://wwwbusingacom.000webhostapp.com/bus_poll_admin.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Do something when response recieved

                String driv[] = response.split("////");
                t.setText((""));
                for (String d : driv) {
                    String det[] = d.split("//");
                    if (det.length == 14) {
                        if (det[0] != "" && det[1] != "") {
                            t.append(det[0]+": \n");
                            if(det[1]=="1")
                            {
                                t.append("11:00 am : "+det[2]+"        ");
                                t.append("3:05 pm : "+det[3]+"        ");
                                t.append("6:15 pm : "+det[4]+"        ");
                                t.append("7:00 pm : "+det[5]+"        ");
                                t.append("8:00 pm : "+det[6]+"        ");
                            }
                            else{
                                t.append("7:00 am : "+det[7]+"        ");
                                t.append("8:00 am : "+det[8]+"        ");
                                t.append("1:30 pm : "+det[8]+"        ");
                                t.append("5:30 pm : "+det[10]+"        ");
                                t.append("7:00 pm : "+det[11]+"        ");
                                t.append("8:30 pm : "+det[12]+"        ");
                                t.append("9:30 pm : "+det[13]+"        ");
                            }
                            t.append("\n\n");
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "Errroroor");
                error.printStackTrace();
                //swipeRefreshLayout.setRefreshing(false);
            }
        });
        queue.add(sr);
    }
}
