package com.example.lw3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class LogActivity extends Activity {
    public static final String APP_PREFERENCES = "MAIN_SETTINGS";
    public static final String APP_PREFERENCES_LOG = "LOGIN";
    SharedPreferences MAIN;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        MAIN = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Button button1 = findViewById(R.id.button);
        EditText editText1 = findViewById(R.id.login);
        EditText editText2 = findViewById(R.id.pass);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = MAIN.edit();
                editor.putString(APP_PREFERENCES_LOG, editText1.getText().toString());
                editor.apply();
                String user = editText1.getText().toString();
                Intent intent = new Intent(LogActivity.this, ListActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        loadPreferences(editText1);
    }

    protected void loadPreferences(EditText editText1) {
        SharedPreferences data = this.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        String dataset = data.getString(APP_PREFERENCES_LOG, null);
        editText1.setText(dataset);

    }
}
