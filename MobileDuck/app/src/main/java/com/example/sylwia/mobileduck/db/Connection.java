package com.example.sylwia.mobileduck.db;

import android.util.Log;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by kamil on 26.11.2017.
 */

public class Connection {
    public static final String TAG = "Connection";

    private static final String dbHost = "80.211.244.76";
    private static final String dbPort = "3306";
    private static final String dbName = "shoplistapplicationdb";
    private static final String dbUser = "duck";
    private static final String dbPassword = "Ducksolutions1";

    private static final String dbUrl = "jdbc:mysql://" + dbHost +":"+ dbPort +"/" + dbName + "?user=" + dbUser + "&password=" + dbPassword;

    private static ConnectionSource connectionSource;
    private static Connection instance;

    private Connection(){
        Log.i(TAG, "Connecting to DB");

        try {
            connectionSource = new JdbcConnectionSource(dbUrl);
        } catch (SQLException e) {
            Log.e(TAG,e.getMessage());
        }

        Log.i(TAG, "Connected to DB");
    }

    public static Connection getInstance(){
        if(instance == null){
            instance = new Connection();
        }
        return instance;
    }

    public static ConnectionSource getConnectionSource(){
        return connectionSource;
    }


}
