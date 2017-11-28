package com.example.sylwia.mobileduck.db.dao;

import android.util.Log;

import com.example.sylwia.mobileduck.db.Connection;
import com.example.sylwia.mobileduck.db.tables.Item;
import com.example.sylwia.mobileduck.db.tables.ShoppingList;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

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

    public static List<Item> getItemsFromShoppingList(ShoppingList shoppingList){
        QueryBuilder<Item, Integer> queryBuilder = itemDAO.queryBuilder();

        try {
            queryBuilder.where().like(Item.ITEM_SHOP_LIST_ID, shoppingList.getId());
            return itemDAO.query(queryBuilder.prepare());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
