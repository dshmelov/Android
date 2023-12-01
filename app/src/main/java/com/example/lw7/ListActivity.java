package com.example.lw7;
import static java.lang.Boolean.FALSE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {

    final int LOGIN_ID = 0;
    final int REGISTER_ID = 1;
    final int DELETE_USER_ID = 2;
    final int CHANGE_PASSWORD_ID = 3;

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

        final Looper looper = Looper.getMainLooper();
        final Message message = Message.obtain();


        final Handler handler = new Handler(looper);

        confirmChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_password = changePassword.getText().toString();
                List<User> all_users = db.getAllUsers();
                User currentUser = new User("Null", "Null");
                for (User usr : all_users) {
                    String usr_login = usr.getLogin();
                    if (usr_login.equals(current_user)) {
                        currentUser = usr;
                        break;
                    }
                }
                String log = "Check change password";
                Log.v("UI thread", log);
                new ThreadTask(handler).change_password(db, currentUser, new_password);
            }
        });
//
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_delete = user.getText().toString();
                myStringArray.remove(user_delete);
                textList.post(new Runnable() {
                    @Override
                    public void run() {
                        textList.setAdapter(TextAdapter);
                    }
                });
                List<User> all_users = db.getAllUsers();
                User deleteUser = new User("Null", "Null");
                for (User usr : all_users) {
                    String usr_login = usr.getLogin();
                    if (usr_login.equals(user_delete)) {
                        deleteUser = usr;
                        break;
                    }
                }
                if (!deleteUser.getLogin().equals("Null") && !deleteUser.getPass().equals("Null")) {
                    String log = "Check delete user";
                    Log.v("UI thread", log);
                    new ThreadTask(handler).delete_user(db, deleteUser);
                }
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