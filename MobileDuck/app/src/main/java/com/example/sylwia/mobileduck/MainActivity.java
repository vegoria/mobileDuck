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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sylwia.mobileduck.db.Manager;
import com.example.sylwia.mobileduck.db.tables.Item;
import com.example.sylwia.mobileduck.db.tables.ShoppingList;
import com.example.sylwia.mobileduck.db.tables.User;

public class MainActivity extends AppCompatActivity {
    Manager dataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButtonListener();
        dataManager = new Manager();

//        Thread thread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try  {
//                    Manager manager = new Manager();
//
//                    manager.addUser("TestKamil");
//                    manager.addUser("TestPawel");
//                    manager.addUser("TestSylwia");
//                    manager.addUser("TestDuck");
//                    manager.addUser("TestMichal");
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

    private void setButtonListener()
    {
        Button saveButton = (Button) findViewById(R.id.saveUserButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void run() {
                try  {

                    ShoppingListReceiverTest test = new ShoppingListReceiverTest(getApplicationContext());

                    Manager manager = new Manager();
            public void onClick(View view) {
                EditText userLoginInput = (EditText) findViewById(R.id.userLoginInput);
                String userLogin = userLoginInput.getText().toString();
                Boolean isUserInsertedToDatabase = addUserToDatabase(userLogin);
                if (isUserInsertedToDatabase)
                {
                    SharedPreferences sharedPref = getApplicationContext()
                            .getSharedPreferences(
                                getString(R.string.preference_file_key),
                                          Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.preference_user_login), userLogin).commit();

                    Intent mainViewIntent = new Intent(new MainViewActivity.class);
                    startActivity(mainViewIntent);
                }
                else
                {

                }
            }
        });
    }

    private boolean addUserToDatabase(String userLogin)
    {
        boolean isUserInserted = false;
        dataManager.addUser(userLogin);

        return isUserInserted;
    }

}
