package com.example.lw7;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class LogActivity extends Activity {

    final int LOGIN_ID = 0;
    final int REGISTER_ID = 1;
    final int DELETE_USER_ID = 2;
    final int CHANGE_PASSWORD_ID = 3;

    ArrayList<String> USERS;
    DatabaseHandler db = new DatabaseHandler(this);
    List<User> ALL_USERS;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_activity);

        EditText userName = findViewById(R.id.user_name);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        Button registration = findViewById(R.id.registration);

        Bundle arguments = getIntent().getExtras();

        USERS = new ArrayList<String>();
        ALL_USERS = db.getAllUsers();
        for (User usr: ALL_USERS) {
            String login_user = usr.getLogin();
            USERS.add(login_user);
        }

        final Looper looper = Looper.getMainLooper();
        final Message message = Message.obtain();


        final Handler handler = new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.sendingUid == LOGIN_ID) {
                    if (msg.obj != FALSE) {
                        Intent intent = new Intent(LogActivity.this, ListActivity.class);
                        String current_user = msg.obj.toString();
                        intent.putExtra("CURRENT_USER", current_user);
                        intent.putExtra("USERS", USERS);
                        String log = "User successfully login";
                        Log.v("UI thread", log);
                        startActivity(intent);
                    }
                }
            }
        };


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = userName.getText().toString();
                String pass = password.getText().toString();
                List<User> Users = db.getAllUsers();
                User current_user = new User("Null", "Null");
                for (User usr: Users) {
                    if (usr.getLogin().equals(login) && usr.getPass().equals(pass)) {
                        current_user = usr;
                    }
                }
                String log = "Check login";
                Log.v("UI thread", log);
                if (!current_user.getLogin().equals("Null") && !current_user.getPass().equals("Null")) {
                    new ThreadTask(handler).login_user(db, current_user);
                }
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameSave = userName.getText().toString();
                String passwordSave = password.getText().toString();
                USERS.add(userNameSave);
                String log = "Check registration";
                Log.v("UI thread", log);
                new ThreadTask(handler).register_user(db, new User(userNameSave, passwordSave));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

}