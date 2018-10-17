package com.example.aniket.businga;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsNavBar extends AppCompatActivity {

    Button edit;
    Button done;
    EditText name_edittext;
    TextView email_edittext;
    SwitchCompat switchCompat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_nav_bar);
        switchCompat = findViewById(R.id.switchNotifications);
        final SharedPreferences sharedPreferences = getSharedPreferences("Enable notifications", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean enable = sharedPreferences.getBoolean("notifications", true);
        if(enable == true){
            switchCompat.setChecked(false);
        }else{
            switchCompat.setChecked(true);
        }
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public String TAG = "notifications";

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    editor.putBoolean("notifications", false);
                }else{
                    editor.putBoolean("notifications", true);
                }
                editor.commit();
                Log.i(TAG, "onCheckedChanged: " + sharedPreferences.getBoolean("notifications",true));
            }
        });

        edit = findViewById(R.id.edit_button);
        done = findViewById(R.id.done);
        name_edittext = findViewById(R.id.name_editview);
        email_edittext = findViewById(R.id.email_editview);
        final SharedPreferences mypref = getSharedPreferences("Mypref", MODE_PRIVATE);
        final SharedPreferences.Editor myprefeditor = mypref.edit();
        String name = mypref.getString("Name","");
        String email = mypref.getString("email", "");
        name_edittext.setText(name);
        email_edittext.setText(email);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_edittext.setEnabled(true);
                done.setVisibility(View.VISIBLE);
                edit.setEnabled(false);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_name = name_edittext.getText().toString().trim();
                myprefeditor.putString("Name", new_name);
                Toast.makeText(getApplicationContext(), "Name changed to " + new_name,Toast.LENGTH_SHORT).show();
                name_edittext.setEnabled(false);
                myprefeditor.apply();
                name_edittext.setText(mypref.getString("Name", ""));
                done.setVisibility(View.GONE);
                edit.setEnabled(true);
            }
        });
    }

}
