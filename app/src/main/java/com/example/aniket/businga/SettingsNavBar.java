package com.example.aniket.businga;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.widget.CompoundButton;

public class SettingsNavBar extends AppCompatActivity {

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
    }
}
