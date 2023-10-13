package com.example.lw3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class LogActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Button button1 = findViewById(R.id.button);
        EditText editText1 = findViewById(R.id.login);
        EditText editText2 = findViewById(R.id.pass);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editText1.getText().toString();
                Intent intent = new Intent(LogActivity.this, ListActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });


//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(this, ListActivity.class);
//                intent.putExtra("user", user);
//                startActivity(intent);
//            }
//        });

//        public void onClick(View v) {
//            Intent intent = new Intent(this, ListActivity.class);
//            intent.putExtra("user", user);
//            startActivity(intent);
//        }



//        ArrayList<String> myStringArray = new ArrayList<String>();
//        ArrayAdapter<String> TextAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myStringArray);
//        ListView textList = findViewById(R.id.textList);
//
//        Button button1 = findViewById(R.id.button1);
//        EditText editText = findViewById(R.id.login);
//        EditText editText1 = findViewById(R.id.pass);
//
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myStringArray.add(editText.getText().toString());
//                textList.setAdapter(TextAdapter);
//            }
//        });
    }
}
