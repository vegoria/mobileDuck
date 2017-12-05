package com.example.sylwia.mobileduck;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.sylwia.mobileduck.db.Manager;
import com.example.sylwia.mobileduck.db.tables.User;

import java.util.ArrayList;
import java.util.List;

public class AddNewFriendActivity extends AppCompatActivity {
    private List<User> allUsers;
    private Manager manager;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_friend);
        initData();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                allUsers = manager.getAllUsers();
            }
        });
        thread.start();

        joinThread(thread);
        setSpinnerAdapter();
    }

    private void initData() {
        allUsers =  new ArrayList<>();
        manager = new Manager();
        spinner = findViewById(R.id.spinner2);
    }

    private void setSpinnerAdapter() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.row_shoplist_item, allUsers);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    private void joinThread(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onClickAddFriend(View view){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                String userName =  sharedPref.getString(getString(R.string.preference_user_login),"");
                String friendName = spinner.getSelectedItem().toString().replaceAll("[^a-zA-Z0-9]", "");
                manager.addFriend(userName, friendName);
            }
        });
        thread.start();
        joinThread(thread);
        finish();
    }
}
