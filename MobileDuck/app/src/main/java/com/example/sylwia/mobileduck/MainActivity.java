package com.example.sylwia.mobileduck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sylwia.mobileduck.db.Manager;
import com.example.sylwia.mobileduck.db.tables.Item;
import com.example.sylwia.mobileduck.db.tables.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    Manager manager = new Manager();

                    manager.addUser("TestKamil");
                    manager.addUser("TestPawel");
                    manager.addUser("TestSylwia");
                    manager.addUser("TestDuck");
                    manager.addUser("TestMichal");

                    manager.addFriend("TestKamil", "TestDuck");
                    manager.addFriend("TestKamil", "TestMichal");

                    manager.addShoppingList("Moja testowa lista", "TestKamil");
                    manager.addItem("TestMaslo",1,0,"TestKamil", "Moja testowa lista");


                    for(User user: manager.getUserFriends("TestKamil")){
                        System.out.println(user.getLogin());
                        //Log.i("a", user.getLogin());
                    }

                    //Log.i("s", Manager.getShoppingListsForSpecifiedUser("Kamil").get(0).getName());
                    System.out.println(manager.getShoppingListsForSpecifiedUser("TestKamil").get(0).getName());

                    for (Item item : manager.getItemsFromShoppingList(manager.getShoppingList("Moja testowa lista", "TestKamil"))){
                        System.out.println(item.getName());
                        //Log.e("sa", item.getName());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
