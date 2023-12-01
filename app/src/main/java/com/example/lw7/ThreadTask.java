package com.example.lw7;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.FALSE;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class ThreadTask {


    final int LOGIN_ID = 0;
    final int REGISTER_ID = 1;
    final int DELETE_USER_ID = 2;
    final int CHANGE_PASSWORD_ID = 3;

    Handler thr_handler;
    DatabaseHandler databaseHandler;

    final Message message = Message.obtain();

    ThreadTask(Handler main_handler){
        this.thr_handler = main_handler;
    }

    public void register_user(DatabaseHandler db, User user) {
        databaseHandler = db;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                message.sendingUid = REGISTER_ID;
                message.obj = TRUE;
                String log = "Success registration";
                Log.v("Registration thread", log);
                databaseHandler.addUser(user);
                thr_handler.sendMessage(message);
            }
        }).start();
    }

    public void login_user(DatabaseHandler db, User user) {
        databaseHandler = db;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                message.sendingUid = LOGIN_ID;
                boolean FLAG = FALSE;
                List<User> Users = databaseHandler.getAllUsers();
                for (User usr: Users) {
                    if (usr.getID() == user.getID()) {
                        FLAG = TRUE;
                        break;
                    }
                }
                if (FLAG == TRUE) {
                    String log = "Success login";
                    Log.v("Login thread", log);
                }
                message.obj = FLAG != FALSE ? user.getLogin() : FLAG;
                thr_handler.sendMessage(message);
            }
        }).start();
    }

    public void delete_user(DatabaseHandler db, User user) {
        databaseHandler = db;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                message.sendingUid = DELETE_USER_ID;
                boolean FLAG = FALSE;
                List<User> Users = databaseHandler.getAllUsers();
                for (User usr: Users) {
                    if (usr.getID() == user.getID()) {
                        FLAG = TRUE;
                        break;
                    }
                }
                if (FLAG == TRUE) {
                    String log = "Success delete user";
                    Log.v("Delete user thread", log);
                    databaseHandler.removeUser(user);
                }
                Log.v("Delete user thread", "Exit thread");
                message.obj = FLAG;
                thr_handler.sendMessage(message);
            }
        }).start();
    }

    public void change_password(DatabaseHandler db, User user, String new_password) {
        databaseHandler = db;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                message.sendingUid = CHANGE_PASSWORD_ID;
                boolean FLAG = FALSE;
                List<User> Users = databaseHandler.getAllUsers();
                for (User usr: Users) {
                    if (usr.getID() == user.getID()) {
                        FLAG = TRUE;
                        break;
                    }
                }
                if (FLAG == TRUE) {
                    String log = "Success change password";
                    Log.v("Change password thread", log);
                    databaseHandler.editPassword(user, new_password);
                }
                message.obj = FLAG;
                thr_handler.sendMessage(message);
            }
        }).start();
    }
}
