package com.example.sylwia.mobileduck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sylwia.mobileduck.db.Manager;
import com.example.sylwia.mobileduck.db.tables.ShoppingList;
import com.example.sylwia.mobileduck.db.tables.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewItemActivity extends AppCompatActivity {

    Manager dataManager;
    boolean itemAdded;
    ShoppingList shoppingList;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        itemAdded = false;
        dataManager = new Manager();
    }

    public void saveNewItemHandler(View view) {
        final long shoppingListId = getIntent().getLongExtra("shoppingListIdExtra", 0);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                shoppingList =  dataManager.getShoppingList(shoppingListId);
                user = dataManager.getUserById(shoppingList.getOwner());
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(shoppingList == null) {
            return;
        }

        addItemToShoppingList();
    }

    public void addItemToShoppingList() {
        EditText itemNameInput = (EditText) findViewById(R.id.itemNameInput);
        EditText numberOfItemsInput = (EditText) findViewById(R.id.numberOfItemsInput);

        final String itemName = itemNameInput.getText().toString();
        final int numberOfItems = Integer.parseInt(numberOfItemsInput.getText().toString());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                itemAdded = dataManager.addItem(itemName, numberOfItems, 0, user.getId(), shoppingList.getId());
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(itemAdded)
        {
            Toast.makeText(this, itemName + " dodano do listy zakupów", Toast.LENGTH_SHORT).show();
            Thread newThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Date date = new Date();
                    shoppingList.setModificationDate(date);
                    shoppingList.setName(shoppingList.getName().replace("[^a-zA-Z0-9]", ""));
                    dataManager.updateShoppingList(shoppingList);
                }
            });
            newThread.start();

            try {
                newThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finish();
        }
        else
        {
            Toast.makeText(this, "Nie udało się dodać do listy", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
