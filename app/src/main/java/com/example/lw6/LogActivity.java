package com.example.lw6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LogActivity extends Activity {
    ArrayList<String> USERS;
    DatabaseHandler db = new DatabaseHandler(this);
    List<User> ALL_USERS;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_activity);

        Bundle arguments = getIntent().getExtras();

        USERS = new ArrayList<String>();
        ALL_USERS = db.getAllUsers();
        for (User usr: ALL_USERS) {
            String login_user = usr.getLogin();
            USERS.add(login_user);
        }

        EditText userName = findViewById(R.id.user_name);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        Button registration = findViewById(R.id.registration);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int COMPARE = 0;
                        ALL_USERS = db.getAllUsers();
                        String userName_cur = userName.getText().toString();
                        String password_cur = password.getText().toString();
                        for (User usr : ALL_USERS) {
                            String log = "Id: " + usr.getID() + ", Login:" + usr.getLogin() + ", Password:" + usr.getPass();
                            Log.v("Loading...", log);
                            String userName_cmp = usr.getLogin();
                            String password_cmp = usr.getPass();
                            if (userName_cur.equals(userName_cmp) && password_cur.equals(password_cmp)) {
                                COMPARE = 1;
                            }
                        }
                        if (COMPARE == 1) {
                            Intent intent = new Intent(LogActivity.this, ListActivity.class);
                            intent.putExtra("CURRENT_USER", userName_cur);
                            intent.putExtra("USERS", USERS);
                            startActivity(intent);
                        }
                    }
                }).start();
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String userNameSave = userName.getText().toString();
                        String passwordSave = password.getText().toString();
                        USERS.add(userNameSave);
                        db.addUser(new User(userNameSave, passwordSave));
                    }
                }).start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

}