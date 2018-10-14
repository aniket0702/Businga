package com.example.aniket.businga;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class AdminUpdateDriver extends AppCompatActivity {

    Button submit;
    EditText driver_id;
    EditText driver_name;
    EditText driver_number;
    EditText bus_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_driver);
        submit = findViewById(R.id.submit_button);
        submit.setEnabled(false);
        driver_id = findViewById(R.id.driver_id);
        driver_name = findViewById(R.id.driver_name);
        driver_number = findViewById(R.id.driver_number);
        bus_number = findViewById(R.id.bus_number);
        driver_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length()>0) {
                    try {
                        int x = Integer.parseInt(charSequence.toString().trim());
                        if (x >= 1 && x <= 3) {
                            submit.setEnabled(true);
                        } else {
                            submit.setEnabled(false);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_variables();

            }
        });
    }
    public void check_variables(){
        boolean isEmpty = false;
        if(driver_name.getText().toString().trim().length() == 0){
            isEmpty = true;
        }
        if (driver_number.getText().toString().trim().length() == 0){
            isEmpty = true;
        }
        if (bus_number.getText().toString().trim().length() == 0){
            isEmpty = true;
        }

        if(isEmpty == false){
            send_request();
        }else
        {
            Toast.makeText(this, "All the fields should have values", Toast.LENGTH_SHORT).show();
        }

    }

    private void send_request() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST,"https://wwwbusingacom.000webhostapp.com/update_driver.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Do something when response recieved
                Log.i(TAG, "onResponse: "+response);
                Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("name", driver_name.getText().toString());
                params.put("mobile", driver_number.getText().toString());
                params.put("bus_no", bus_number.getText().toString());
                params.put("bus_id", driver_id.getText().toString());
                return params;
            }
        };
        queue.add(sr);
    }
}



