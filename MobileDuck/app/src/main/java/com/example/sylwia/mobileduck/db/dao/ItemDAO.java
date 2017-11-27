package com.example.sylwia.mobileduck.db.dao;

import android.util.Log;

import com.example.sylwia.mobileduck.db.Connection;
import com.example.sylwia.mobileduck.db.tables.Item;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;

/**
 * Created by kamil on 27.11.2017.
 */

public class ItemDAO {
    private static final String TAG = "ItemDAO";
    private static Dao<Item, Integer> itemDAO;
    private static ItemDAO instance;

    private ItemDAO(){
        Connection.getInstance();
        Log.i(TAG, "Creating Item DAO");

        try {
            itemDAO = DaoManager.createDao(Connection.getConnectionSource(), Item.class);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        Log.i(TAG, "User DAO was created");
    }

    public static ItemDAO getInstance(){
        if(instance == null){
            instance = new ItemDAO();
        }

        return instance;
    }

    public static  void addItem(Item item){
        try {
            itemDAO.create(item);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
