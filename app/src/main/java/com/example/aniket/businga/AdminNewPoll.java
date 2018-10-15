package com.example.aniket.businga;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class AdminNewPoll extends AppCompatActivity {

    private static final String TAG = "anything" ;
    EditText notification_header, notification_body;
    Button from_date, submit;
    Calendar calendar;
    int year, month, day;
    String date;
    TextView view_text;
    int fl;
    ProgressDialog progressDialog;
    String final_year[];
    String final_month[];
    String final_day[];
    RadioGroup radioGroup;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_poll);
        fl = 0;
        notification_body = findViewById(R.id.poll_body);
        notification_header = findViewById(R.id.poll_header);
        view_text=findViewById(R.id.view_text_view);
        date = "";
        final_day = new String[2];
        final_month = new String[2];
        final_year = new String[2];
        submit = findViewById(R.id.submit_button);
        radioGroup = findViewById(R.id.to_or_from);
        from_date = findViewById(R.id.select_from_date);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        radioGroup.check(R.id.arrival);

        final DatePickerDialog.OnDateSetListener todatepicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                date = String.valueOf(i) + String.format("%02d", i1)+ String.format("%02d",i2);
                final_year[1] = String.valueOf(i);
                final_month[1] = String.format("%02d", i1);
                final_day[1] = String.format("%02d", i2);
                fl = 1;
            }
        };

        final DatePickerDialog.OnDateSetListener fromdatepicker = new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                Calendar tocalandar = Calendar.getInstance();
                tocalandar.set(year,monthOfYear, dayOfMonth);
                date = String.valueOf(year) + String.format("%02d",monthOfYear)+ String.format("%02d",dayOfMonth);
                final_year[0] = String.valueOf(year);
                final_month[0] = String.format("%02d", monthOfYear);
                final_day[0] = String.format("%02d", dayOfMonth);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminNewPoll.this, todatepicker, year, monthOfYear, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(tocalandar.getTimeInMillis());
                datePickerDialog.show();
            }
        };
        from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminNewPoll.this, fromdatepicker, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkConstraints() == true) {
                    int id = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(id);
                    Toast.makeText(AdminNewPoll.this, final_year[0] + final_month[0] + final_day[0], Toast.LENGTH_SHORT).show();
                    int type;
                    if (radioButton.getText().equals("Arrival")) {
                        type = 1;
                    } else {
                        type = 2;
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date myDate = null;
                    try {
                        myDate = dateFormat.parse(final_year[0] + "-" + final_month[0] + "-" + final_day[0]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date newDate = new Date(myDate.getTime() - 86400000L);
                    String till_date = dateFormat.format(newDate);
                    progressDialog = new ProgressDialog(AdminNewPoll.this);
                    progressDialog.setTitle("Creating new poll");
                    progressDialog.setMessage("Just a moment...");
                    progressDialog.show();
                    createNewPoll(type, till_date);
                    Log.i(TAG, "onClick: " + till_date);
                }
                else{
                    Toast.makeText(AdminNewPoll.this, "Select all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean checkConstraints(){
        int id = radioGroup.getCheckedRadioButtonId();
        String header,body;
        try{

            header = notification_header.getText().toString().trim();
            body = notification_body.getText().toString().trim();
        }catch(Exception e){
            e.printStackTrace();
            header = "";
            body = "";
            Log.i(TAG, "checkConstraints: entered catch" );
        }
        if(header.length()>0 && body.length()>0 && fl==1){
            return true;
        }else{
            return false;
        }
    }
    public void createNewPoll(final int type, final String till_date){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,"https://wwwbusingacom.000webhostapp.com/bus_poll_admin.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Do something when response recieved
                Log.i(TAG, "onResponse: new poll created" + response);
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
                if(progressDialog!=null)
                    progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("type",String.valueOf(type));
                params.put("FromDate",final_year[0]+final_month[0] + final_day[0]);
                params.put("ToDate",final_year[1]+final_month[1] + final_day[1]);
                params.put("till_when", till_date);

                return params;
            }
        };
        queue.add(sr);
    }

}

