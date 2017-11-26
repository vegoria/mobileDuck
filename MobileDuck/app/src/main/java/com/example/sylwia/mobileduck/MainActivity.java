package com.example.sylwia.mobileduck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sylwia.mobileduck.db.dao.UserDAO;
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
                    User superUser = new User("Pan Kapka");

                    UserDAO.getInstance();
                    UserDAO.addUser(superUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
