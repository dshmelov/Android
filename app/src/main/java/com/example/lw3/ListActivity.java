package com.example.lw3;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import static com.example.lw3.LogActivity.APP_PREFERENCES;


import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class ListActivity extends Activity{
    public static final String APP_PREFERENCES_LIST = "LIST";
    SharedPreferences MAIN;

    ArrayList<String> myStringArray = new ArrayList<String>();
    ArrayAdapter<String> TextAdapter;
    ListView textList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        TextAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myStringArray);
        textList = findViewById(R.id.textList);
        Bundle arguments = getIntent().getExtras();
        String user = arguments.getString("user");



        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        EditText editText = findViewById(R.id.editText);
        editText.setText(user);

        MAIN = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStringArray.add(editText.getText().toString());
                textList.setAdapter(TextAdapter);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStringArray.remove(editText.getText().toString());
                textList.setAdapter(TextAdapter);
            }
        });

        loadPreferences(myStringArray);
        Toast.makeText(ListActivity.this, user, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        save();
    }

    public void save() {
        String List = myStringArray.toString();
        SharedPreferences.Editor editor = MAIN.edit();
        editor.putString(APP_PREFERENCES_LIST, List);
        editor.apply();
    }

    protected void loadPreferences(ArrayList<String> myStringArray) {
        SharedPreferences data = this.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        String dataset = data.getString(APP_PREFERENCES_LIST, null);
        if (Objects.equals(dataset, null)) return;
        if (Objects.equals(dataset, "[]")) return;
        dataset = dataset.replaceAll("^\\[|\\]$", "");
        String[] temp = dataset.split(", ");
        TextAdapter.addAll(temp);
        TextAdapter.notifyDataSetChanged();
        textList.setAdapter(TextAdapter);
    }
}
