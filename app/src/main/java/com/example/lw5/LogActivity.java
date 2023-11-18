package com.example.lw5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import java.io.Console;
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

//        String DELETE_USER = arguments != null ? arguments.getString("deleteUser") : "";
//        if (!DELETE_USER.equals("")) {
//            for (User usr : ALL_USERS) {
//                String login = usr.getLogin();
//                if (login.equals(DELETE_USER)) {
//                    db.removeUser(usr);
//                    USERS.remove(DELETE_USER);
//                }
//            }
//        }
//
//        String CURRENT_USER = arguments != null ? arguments.getString("CURRENT_USER") : "";
//        String NEW_PASSWORD = arguments != null ? arguments.getString("NEW_PASSWORD") : "";
//
//        if (!NEW_PASSWORD.equals("")) {
//            for (User usr : ALL_USERS) {
//                String login = usr.getLogin();
//                if (login.equals(CURRENT_USER)) {
//                    db.editPassword(usr, NEW_PASSWORD);
//                }
//            }
//        }

        EditText userName = findViewById(R.id.user_name);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        Button registration = findViewById(R.id.registration);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameSave = userName.getText().toString();
                String passwordSave = password.getText().toString();
                USERS.add(userNameSave);
                db.addUser(new User(userNameSave, passwordSave));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

}
