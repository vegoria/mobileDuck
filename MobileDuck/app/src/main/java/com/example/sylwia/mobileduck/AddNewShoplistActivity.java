package com.example.sylwia.mobileduck;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sylwia.mobileduck.db.Manager;

public class AddNewShoplistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_shoplist);
    }

    void onClickAddNewShoplist(View view){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Manager manager = new Manager();
                EditText shopListName = (EditText) findViewById(R.id.shopListEditText);
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                String userName =  sharedPref.getString(getString(R.string.preference_user_login),"");
                manager.addShoppingList(shopListName.getText().toString(),userName);
            }
        });

        thread.start();
    }

}
