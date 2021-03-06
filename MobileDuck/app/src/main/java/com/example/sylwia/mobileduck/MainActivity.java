package com.example.sylwia.mobileduck;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sylwia.mobileduck.db.Manager;
import com.example.sylwia.mobileduck.db.tables.Item;
import com.example.sylwia.mobileduck.db.tables.ShoppingList;
import com.example.sylwia.mobileduck.db.tables.User;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    Manager dataManager;
    Boolean isUserInsertedToDatabase;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isUserInsertedToDatabase = false;
        dataManager = new Manager();
        currentUser = null;
        boolean userExist = checkIfUserExist();
        if(userExist)
        {
            goToShopActivity();
        }
        else
        {
            setButtonListener();
        }

//        Thread thread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//
//                try  {
//                    ShoppingListReceiverTest test = new ShoppingListReceiverTest(getApplicationContext());
//
//                    Manager manager = new Manager();
//
//                    boolean test12 = manager.addUser("TestKamil");
//                    manager.addUser("TestPawel");
//                    manager.addUser("TestSylwia");
//                    manager.addUser("TestDuck");
//                    manager.addUser("TestMichal");
//                    if (test12){ Toast.makeText(getApplicationContext(), "Haha", Toast.LENGTH_SHORT);}
//
//                    manager.addFriend("TestKamil", "TestDuck");
//                    manager.addFriend("TestKamil", "TestMichal");
//
//                    manager.addShoppingList("Moja testowa lista", "TestKamil");
//                    manager.addItem("TestMaslo",1,0,"TestKamil", "Moja testowa lista");
//
//
//                    for(User user: manager.getUserFriends("TestKamil")){
//                        System.out.println(user.getLogin());
//                        //Log.i("a", user.getLogin());
//                    }
//
//                    //Log.i("s", Manager.getUserShoppingLists("Kamil").get(0).getName());
//                    System.out.println(manager.getUserShoppingLists("TestKamil").get(0).getName());
//
//                    for (Item item : manager.getItemsFromShoppingList(manager.getShoppingList("Moja testowa lista", "TestKamil"))){
//                        System.out.println(item.getName());
//                        //Log.e("sa", item.getName());
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        thread.start();
    }

    private boolean checkIfUserExist()
    {
        boolean userExist = false;

        SharedPreferences sharedPref = getApplicationContext()
                .getSharedPreferences(
                        getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE);
        final String userLogin = sharedPref.getString(getString(R.string.preference_user_login), getUserLogin());


        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run(){
                currentUser = dataManager.getUserByLogin(userLogin);
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

        if(currentUser != null)
        {
            userExist = true;
        }

        return userExist;
    }

    private void goToShopActivity()
    {
        // TODO uncomment!
        //Intent mainViewIntent = new Intent(getApplicationContext(), MyShopActivity.class);
        //startActivity(mainViewIntent);

        // TODO delete (only test)
        Intent mainViewIntent = new Intent(getApplicationContext(), MyShopActivity.class);
        /*mainViewIntent.putExtra("ListId", (long)81); // ID listy - musi byc rzutowana na long!
        mainViewIntent.putExtra("OwnList", true); // Czy lista wlasna (chodzi o widocznosc przycisku dodawania itemu)*/
        startActivity(mainViewIntent);
    }

    private void setButtonListener()
    {
        Button saveButton = (Button) findViewById(R.id.saveUserButton);
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String userLogin = getUserLogin();
                addUserToDatabase(userLogin);

                if (isUserInsertedToDatabase || checkIfUserExist())
                {
                    SharedPreferences sharedPref = getApplicationContext()
                            .getSharedPreferences(
                                getString(R.string.preference_file_key),
                                          Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.preference_user_login), userLogin).commit();

                    goToShopActivity();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.could_not_add_user, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getUserLogin()
    {
        EditText userLoginInput = (EditText) findViewById(R.id.userLoginInput);
        return userLoginInput.getText().toString();
    }

    private void addUserToDatabase(final String userLogin)
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run(){
                isUserInsertedToDatabase = dataManager.addUser(userLogin);
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
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        boolean userExist = checkIfUserExist();
        if (userExist)
        {
            finish();
        }
    }

}
