package com.example.aniket.businga;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyAccount extends AppCompatActivity {

    Button edit;
    Button done;
    EditText name_edittext;
    TextView email_edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        edit = findViewById(R.id.edit_button);
        done = findViewById(R.id.done);
        name_edittext = findViewById(R.id.name_editview);
        email_edittext = findViewById(R.id.email_editview);
        final SharedPreferences sharedPreferences = getSharedPreferences("Mypref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        String name = sharedPreferences.getString("Name","");
        String email = sharedPreferences.getString("email", "");
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
                String new_name = name_edittext.getText().toString();
                editor.putString("Name", new_name);
                Toast.makeText(getApplicationContext(), "Name changed to " + new_name,Toast.LENGTH_SHORT);
                name_edittext.setEnabled(false);
                editor.commit();
                done.setVisibility(View.GONE);
                edit.setEnabled(true);
            }
        });
    }
}
