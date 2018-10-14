package com.example.aniket.businga;

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
    Calendar from, to;
    DateFormat df;
    List<Date> listDate;
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
        submit = findViewById(R.id.submit_button);
        radioGroup = findViewById(R.id.to_or_from);
        from_date = findViewById(R.id.select_from_date);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        df = new SimpleDateFormat("yyyyMMdd");
        from = Calendar.getInstance();
        to = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener todatepicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                date = String.valueOf(i) + String.format("%02d", i1)+ String.format("%02d",i2);
                try{
                    to.setTime(df.parse(date));
                }catch (Exception e){
                    e.printStackTrace();
                }
                listDate = getDates();
                Log.i(TAG, "onDateSet: "+listDate.toString());
            }
        };
        final DatePickerDialog.OnDateSetListener fromdatepicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                Calendar tocalandar = Calendar.getInstance();
                tocalandar.set(year,monthOfYear, dayOfMonth);
                date = String.valueOf(year) + String.format("%02d",monthOfYear)+ String.format("%02d",dayOfMonth);
                try{
                    from.setTime(df.parse(date));
                }catch (Exception e){
                    e.printStackTrace();
                }
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
                Toast.makeText(AdminNewPoll.this, radioButton.getText(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    List<Date> getDates(){
        ArrayList<Date> dates = new ArrayList<Date>();
        Calendar cal1 = from;
        Calendar cal2 = to;
        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

}
