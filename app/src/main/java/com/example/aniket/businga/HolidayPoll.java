package com.example.aniket.businga;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ProgressBar;
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

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class HolidayPoll extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG = "Main Activity ";
    static int type;
    static List<String> ls = new ArrayList<>();
    protected Spinner sp;
    protected Spinner sp2;
    protected Spinner sp3;
    Button bt2;
    Date d;
    int option;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText response;
    String email;
    List<String> ls2 = new ArrayList<>();
    List<String> ls3 = new ArrayList<>();
    ProgressDialog progressDialog;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private Context mcontext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View iv = inflater.inflate(R.layout.fragment_holiday_poll, container, false);
        this.mcontext = getContext();
        SharedPreferences mypref = mcontext.getSharedPreferences("Mypref", MODE_PRIVATE);
        email = mypref.getString("email", "");
        sp = (Spinner) iv.findViewById(R.id.spinner);
        sp2 = (Spinner) iv.findViewById(R.id.spinner2);
        sp3 = (Spinner) iv.findViewById(R.id.spinner3);
        bt2 = (Button) iv.findViewById(R.id.button2);
        option = 0;
        type = 1;
        sharedPref = mcontext.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        editor = sharedPref.edit();
        updateSpinners();
        if (sharedPref.getBoolean("invalid", false) == true) {
            Toast.makeText(mcontext, "The poll has ended", Toast.LENGTH_SHORT).show();
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
        is[0] = null;
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                is[0] = parent.getItemAtPosition(position).toString();
                try {
                    Log.d(TAG, "onItemSelected: " + is[0]);
                    d = new SimpleDateFormat("yyyy-MM-dd").parse(is[0]);
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

        if (type == 1) {
            sp3.setEnabled(false);
            sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    option = position;
                    Log.i(TAG, "onItemSelected: " + String.valueOf(option));
                    String il = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(MainActivity.this, "Selected " + il, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(mcontext, "Please select a valid option", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (type == 2) {
            sp2.setEnabled(false);
            sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    option = position;
                    Log.i(TAG, "onItemSelected: " + String.valueOf(option));
                    String il = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(MainActivity.this, "Selected " + il, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(mcontext, "Please select a valid option", Toast.LENGTH_SHORT).show();
                }
            });
        }

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: " + String.valueOf(option) + String.valueOf(is[0]));
                if (option != 0 && is[0] != null) {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle("Sending Response");
                    progressDialog.setMessage("Just a moment...");
                    progressDialog.show();
                    Log.i(TAG, "getParams: " + new SimpleDateFormat("yyyy-MM-dd").format(d));
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    StringRequest sr = new StringRequest(Request.Method.POST, "https://wwwbusingacom.000webhostapp.com/bus_poll_user.php", new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            //Do something when response recieved
                            Log.i(TAG, "onResponse: " + response);
                            //swipeRefreshLayout.setRefreshing(false);
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                                getActivity().finish();
                                startActivity(getActivity().getIntent());
                            }
                            if (response.equals("pollDone") == true) {
                                Toast.makeText(getContext(), "You have already submitted the response...", Toast.LENGTH_SHORT).show();
                            }
                            //bt2.setEnabled(false);
                            editor.commit();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("tag", "Errroroor");
                            error.printStackTrace();
                            //swipeRefreshLayout.setRefreshing(false);
                            if (progressDialog != null)
                                progressDialog.dismiss();
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(d));
                            params.put("option", Integer.toString(option));
                            params.put("type", Integer.toString(type));
                            params.put("email", email);
                            return params;
                        }
                    };
                    queue.add(sr);
                }
            }
        });
        return iv;
    }

    private void updateSpinners() {
        if (type == 1) {
            ls2.add("11:00 am");
            ls2.add("3:05 pm");
            ls2.add("6:15 pm");
            ls2.add("7:00 pm");
            ls2.add("8:00 pm");
        } else {
            ls3.add("7:00 am");
            ls3.add("8:00 am");
            ls3.add("1:30 pm");
            ls3.add("5:30 pm");
            ls3.add("7:00 pm");
            ls3.add("8:30 pm");
            ls3.add("9:30 pm");
        }
    }

    public void refreshpoll() {
        SharedPreferences pollPreference = mcontext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pollPreference.edit();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.GET, "https://wwwbusingacom.000webhostapp.com/bus_poll_user.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Do something when response recieved
                ls.clear();
                String driv[] = response.split("////");
                for (String d : driv) {
                    String det[] = d.split("//");
                    if (det.length == 3) {
                        if (det[0] != "" && det[1] != "" && det[2] != "") {
                            try {
                                Log.i(TAG, "onResponse: " + det[1]);
                                if ((new SimpleDateFormat("yyyy-MM-dd").parse(det[1]).after(new java.util.Date()))) {
                                    editor.putBoolean("invalid", true);
                                    Log.i(TAG, "onResponse: done invalid true");
                                    editor.putBoolean("repeated", false);
                                    editor.commit();
                                } else {
                                    editor.putBoolean("invalid", false);
                                    editor.commit();
                                    ls.add(det[0]);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            type = Integer.parseInt(det[2]);
                            Log.i(TAG, "type  " + String.valueOf(HolidayPoll.type));
                        }
                    }
                }
                ls.add("Day");
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
