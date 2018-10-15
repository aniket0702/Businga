package com.example.aniket.businga;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdminNewPoll extends AppCompatActivity {

    private static final String TAG = "anything" ;
    EditText notification_header, notification_body;
    Button from_date, submit;
    Calendar calendar;
    int year, month, day;
    String date;
    TextView view_text;
    DateFormat df;
    String final_year[];
    String final_month[];
    String final_day[];
    RadioGroup radioGroup;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_poll);

        notification_body = findViewById(R.id.notification_body);
        notification_header = findViewById(R.id.notification_header);
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

        final DatePickerDialog.OnDateSetListener todatepicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                date = String.valueOf(i) + String.format("%02d", i1)+ String.format("%02d",i2);
                final_year[1] = String.valueOf(i);
                final_month[1] = String.format("%02d", i1);
                final_day[1] = String.format("%02d", i2);
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
                int id = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(id);
//                Toast.makeText(AdminNewPoll.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(AdminNewPoll.this, "Created Poll", Toast.LENGTH_SHORT).show();
                Toast.makeText(AdminNewPoll.this, final_year[0] + final_month[0] + final_day[0] ,Toast.LENGTH_SHORT).show();
            }
        });
    }


}
