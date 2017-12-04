package com.example.sylwia.mobileduck;

import android.net.Uri;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sylwia.mobileduck.db.tables.ShoppingList;

import static android.app.PendingIntent.getActivity;

public class MyShopActivity extends AppCompatActivity implements ListsListFragment.OnFragmentInteractionListener, FriendListFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
        getSupportFragmentManager().beginTransaction().add(R.id.fragments_holder, new ListsListFragment()).commit();
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
                Intent addListIntent = new Intent(getApplicationContext(), AddNewShoplistActivity.class);
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
                Intent addListIntent = new Intent(getApplicationContext(), AddNewFriendActivity.class);
                startActivity(addListIntent);
            }
        });
    }

    private void setChangeFragmentListener()
    {
        /*Button switchFragmentButton = (Button) findViewById(R.id.change_fragment_button);
        switchFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragments_holder);

                if(f instanceof ListsListFragment)
                {
                  setFriendsFragment();
                }
                else
                {
                    setShoppingListFragment();
                }
            }
        });*/
    }

    private void setShoppingListFragment()
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragments_holder, new ListsListFragment())
                .addToBackStack(null).commit();
    }
    private void setFriendsFragment()
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragments_holder, new FriendListFragment())
                .addToBackStack(null).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
