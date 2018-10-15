package com.example.aniket.businga;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HolidayPoll extends Fragment {
    private static final String TAG = "Main Activity " ;
    protected Spinner sp;
    protected Spinner sp2;
    protected Spinner sp3;
    Button bt2;
    Date d;
    int option;
    int type;
    EditText response;
    EditText v;
    Button bt3;
    List<String> ls = new ArrayList<>();
    List<String> ls2 = new ArrayList<>();
    List<String> ls3 = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private Context mcontext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View iv= inflater.inflate(R.layout.fragment_holiday_poll, container, false);
        this.mcontext=getContext();
        sp = (Spinner) iv.findViewById(R.id.spinner);
        sp2 = (Spinner) iv.findViewById(R.id.spinner2);
        sp3 = (Spinner) iv.findViewById(R.id.spinner3);
        bt2 = (Button) iv.findViewById(R.id.button2);
        response = (EditText) iv.findViewById(R.id.editText2);
        v=(EditText) iv.findViewById(R.id.view);
        option = 0;
        sharedPref = mcontext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor= sharedPref.edit();
        if(sharedPref.getBoolean("repeated", false)==true){
            v.setText("You have alreday filled the response");
            bt2.setEnabled(false);
        }
        refreshnotification();
        if(sharedPref.getBoolean("invalid", false)==true){
            v.setText("The poll has been closed");
            bt2.setEnabled(false);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ls);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ls2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ls3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp3.setAdapter(adapter3);

        final String[] is = new String[1];
        is[0]=null;
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                is[0] = parent.getItemAtPosition(position).toString();
                try {
                    Log.d(TAG, "onItemSelected: " + is[0]);
                    d = new SimpleDateFormat("yyyy-mm-dd").parse(is[0]);
                    //v.setText();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(MainActivity.this, "Selected " + is[0], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                response.setText("Please select a valid option");
            }
        });

        if(type==1) {
            sp3.setEnabled(false);
            sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    option = position;
                    String il = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(MainActivity.this, "Selected " + il, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    response.setText("Please select a valid option");
                }
            });
        }

        if(type==2) {
            sp2.setEnabled(false);
            sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    option = position;
                    String il = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(MainActivity.this, "Selected " + il, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    response.setText("Please select a valid option");
                }
            });
        }

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (option != 0 && is[0] != null) {
                    response.setText("Entered");
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    StringRequest sr = new StringRequest(Request.Method.POST, "https://wwwbusingacom.000webhostapp.com/bus_poll_user.php", new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            //Do something when response recieved


                            //swipeRefreshLayout.setRefreshing(false);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("tag", "Errroroor");
                            error.printStackTrace();
                            //swipeRefreshLayout.setRefreshing(false);
                        }


                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Date", new SimpleDateFormat("yyyy-mm-dd").format(d));
                            params.put("option", Integer.toString(option));
                            params.put("type", Integer.toString(type));
                            return params;
                        }
                    });

                    editor.putBoolean("repeated", true);
                    response.setText("Your response has been recorded");
                    //bt2.setEnabled(false);
                    editor.commit();

                }
            }
        });
        return iv;
    }
        public void refreshnotification() {
            //final int[] a = {0};
            RequestQueue queue = Volley.newRequestQueue(getContext());
            StringRequest sr = new StringRequest(Request.Method.GET, "https://wwwbusingacom.000webhostapp.com/bus_poll_user.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Do something when response recieved

                    String driv[] = response.split("////");
                    for (String d : driv) {
                        String det[] = d.split("//");
                        if (det.length == 3) {
                            if (det[0] != "" && det[1] != "" && det[2]!="") {
                                try {
                                    if(new SimpleDateFormat("yyyy-mm-dd").parse(det[1]).compareTo(new java.util.Date())<0)
                                        editor.putBoolean("invalid", true);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                ls.add(det[0]);
                                type=Integer.parseInt(det[2]);
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

            ls.add("Day");
            ls2.add("Time");
            ls2.add("11:00 am");
            ls2.add("3:05 pm");
            ls2.add("6:15 pm");
            ls2.add("7:00 pm");
            ls2.add("8:00 pm");
            ls3.add("Time");
            ls3.add("7:00 am");
            ls3.add("8:00 am");
            ls3.add("1:30 pm");
            ls3.add("5:30 pm");
            ls3.add("7:00 pm");
            ls3.add("8:30 pm");
            ls3.add("9:30 pm");
            //return a[0];
        }
}

