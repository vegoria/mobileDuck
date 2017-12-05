package com.example.sylwia.mobileduck;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sylwia.mobileduck.db.Manager;
import com.example.sylwia.mobileduck.db.tables.ShoppingList;

import java.util.List;

public class AddNewShoplistActivity extends AppCompatActivity {
    private ShoppingList shoppingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_shoplist);
        setOnClickListenerForAddingButton();
    }

    private void setOnClickListenerForAddingButton(){
        Button button = (Button) findViewById(R.id.addShoplistButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddNewShoplist();
            }
        });
    }

    void onClickAddNewShoplist(){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Manager manager = new Manager();
                EditText shopListName = (EditText) findViewById(R.id.shopListEditText);
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                String userName =  sharedPref.getString(getString(R.string.preference_user_login),"");
                manager.addShoppingList(shopListName.getText().toString(),userName);
                shoppingList = manager.getShoppingList(shopListName.getText().toString(), userName);
            }
        });

        thread.start();
        try
        {
            thread.join();
        }
        catch(InterruptedException e)
        {
        }
        Intent newActivityIntent=new Intent(getApplicationContext(),
                ShopListActivity.class).putExtra("ListId",
                shoppingList.getId()).putExtra("OwnList", true);
        startActivity(newActivityIntent);
    }

}
