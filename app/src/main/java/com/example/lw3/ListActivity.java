package com.example.lw3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ListActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        ArrayList<String> myStringArray = new ArrayList<String>();
        ArrayAdapter<String> TextAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myStringArray);
        ListView textList = findViewById(R.id.textList);

        Bundle arguments = getIntent().getExtras();
        String user = arguments.getString("user");

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        EditText editText = findViewById(R.id.editText);
        editText.setText(user);
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


        Toast.makeText(ListActivity.this, user, Toast.LENGTH_SHORT).show();
    }
}
