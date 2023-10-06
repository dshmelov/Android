package com.example.lw2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class HelloActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloact);

        ArrayList<String> myStringArray = new ArrayList<String>();
        ArrayAdapter<String> TextAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myStringArray);
        ListView textList = findViewById(R.id.textList);
        textList.setAdapter(TextAdapter);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        EditText editText = findViewById(R.id.editText);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStringArray.add(editText.getText().toString());
                TextAdapter.notifyDataSetChanged();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStringArray.remove(editText.getText().toString());
                TextAdapter.notifyDataSetChanged();
            }
        });
    }
}
