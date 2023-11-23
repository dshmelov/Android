package com.example.lw6;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {
    ArrayList<String> myStringArray;
    String user_delete = "";
    String current_user = "";
    String new_password = "";
    DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        Bundle arguments = getIntent().getExtras();

        Intent intent = new Intent(ListActivity.this, LogActivity.class);

        current_user = arguments.getString("CURRENT_USER");

        myStringArray = arguments.getStringArrayList("USERS");
        ArrayAdapter<String> TextAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myStringArray != null ? myStringArray : null);
        ListView textList = findViewById(R.id.listView);
        textList.setAdapter(TextAdapter);


        Button confirmChangePassword = findViewById(R.id.confirmChangePassword);
        Button delete = findViewById(R.id.delete);
        Button back = findViewById(R.id.back);
        EditText user = findViewById(R.id.user);
        EditText changePassword = findViewById(R.id.changePassword);

        confirmChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new_password = changePassword.getText().toString();
                        List<User> all_users = db.getAllUsers();
                        for (User usr : all_users) {
                            String usr_login = usr.getLogin();
                            if (usr_login.equals(current_user)) {
                                db.editPassword(usr, new_password);
                            }
                        }
                    }
                }).start();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        user_delete = user.getText().toString();
                        myStringArray.remove(user_delete);
                        textList.post(new Runnable() {
                            @Override
                            public void run() {
                                textList.setAdapter(TextAdapter);
                            }
                        });

                        List<User> all_users = db.getAllUsers();
                        for (User usr : all_users) {
                            String usr_login = usr.getLogin();
                            if (usr_login.equals(user_delete)) {
                                db.removeUser(usr);
                            }
                        }
                    }
                }).start();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}