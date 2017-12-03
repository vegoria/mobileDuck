package com.example.sylwia.mobileduck;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.sylwia.mobileduck.db.Manager;
import com.example.sylwia.mobileduck.db.tables.User;

import java.util.ArrayList;
import java.util.List;

public class AddNewFriendActivity extends AppCompatActivity {
    private List<User> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_friend);
        allUsers =  new ArrayList<User>();

                Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Manager manager = new Manager();
                allUsers = manager.getAllUsers();
                Spinner spinner = (Spinner) findViewById(R.id.spinner2);
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, allUsers);
                spinner.setAdapter(arrayAdapter);
            }
        });

        thread.start();

    }

    void onClickAddFriend(View view){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Manager manager = new Manager();
                Spinner spinner = (Spinner) findViewById(R.id.spinner2);
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                String userName =  sharedPref.getString(getString(R.string.preference_user_login),"");
                String friendName = spinner.getSelectedItem().toString().replaceAll("[^a-zA-Z0-9]", "");
                manager.addFriend(userName, friendName);
            }
        });

        thread.start();
    }

    void onClickCancel(View view){
        finish();
    }
}
