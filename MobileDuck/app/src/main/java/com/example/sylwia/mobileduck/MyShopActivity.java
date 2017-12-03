package com.example.sylwia.mobileduck;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sylwia.mobileduck.db.tables.ShoppingList;

import static android.app.PendingIntent.getActivity;

public class MyShopActivity extends AppCompatActivity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        if(findViewById(R.id.fragments_holder) != null)
        {
            if(savedInstanceState == null)
            {
                setStartupFragment();
            }
        }
        setButtonsListeners();
    }

    private void setStartupFragment()
    {
        //TODO: add proper fragment name
        getSupportFragmentManager().beginTransaction().add(R.id.fragments_holder, ShoppingListFragment).commit();
    }

    private void setButtonsListeners()
    {
        setAddListListener();
        setAddFriendListener();
        setChangeFragmentListener();
    }

    private void setAddListListener()
    {
        Button addListButton = (Button) findViewById(R.id.add_list_button);
        addListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: add propoer activity name
                Intent addListIntent = new Intent(getApplicationContext(), AddListActivity.class);
                startActivity(addListIntent);
            }
        });
    }

    private void setAddFriendListener()
    {
        Button addListButton = (Button) findViewById(R.id.add_friend_buttom);
        addListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: add propoer activity name
                Intent addListIntent = new Intent(getApplicationContext(), AddFriendActivity.class);
                startActivity(addListIntent);
            }
        });
    }

    private void setChangeFragmentListener()
    {
        Button switchFragmentButton = (Button) findViewById(R.id.change_fragment_button);
        switchFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = getFragmentManager().findFragmentById(R.id.fragments_holder);
                //TODO: set name of fragment
                if(f instanceof ShoppingListFragment)
                {
                  setFriendsFragment();
                }
                else
                {
                    setShoppingListFragment();
                }
            }
        });
    }

    private void setShoppingListFragment()
    {
        //TODO: add proper fragment name
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragments_holder, ShoppingListFragment)
                .addToBackStack().commit();
    }
    private void setFriendsFragment()
    {
        //TODO: add proper fragment name
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragments_holder, FriendsListFragment)
                .addToBackStack().commit();
    }*/
}
