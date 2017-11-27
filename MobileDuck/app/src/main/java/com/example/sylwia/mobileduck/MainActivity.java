package com.example.sylwia.mobileduck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.sylwia.mobileduck.db.Manager;
import com.example.sylwia.mobileduck.db.dao.UserDAO;
import com.example.sylwia.mobileduck.db.dao.UserFriendKeyDAO;
import com.example.sylwia.mobileduck.db.tables.Item;
import com.example.sylwia.mobileduck.db.tables.User;
import com.example.sylwia.mobileduck.db.tables.UserFriendKey;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    Manager.getInstance();
                    Manager.addUser("Kamil");
                    Manager.addUser("Pawel");
                    Manager.addUser("Sylwia");
                    Manager.addUser("Duck");
                    Manager.addUser("Michal");

                    Manager.addFriend("Kamil", "Duck");
                    Manager.addFriend("Kamil", "Michal");

                    Manager.addShoppingList("Moja lista", "Kamil");
                    Manager.addItem("Maslo",1,0,"Kamil", "Moja lista");


                    for(User user: Manager.getUserFriends("Kamil")){
                        System.out.println(user.getLogin());
                        //Log.i("a", user.getLogin());
                    }

                    //Log.i("s", Manager.getShoppingListsForSpecifiedUser("Kamil").get(0).getName());
                    System.out.println(Manager.getShoppingListsForSpecifiedUser("Kamil").get(0).getName());

                    for (Item item : Manager.getItemsFromShoppingList(Manager.getShoppingList("Moja lista", "Kamil"))){
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
