package com.example.sylwia.mobileduck.db.dao;

import android.util.Log;

import com.example.sylwia.mobileduck.db.Connection;
import com.example.sylwia.mobileduck.db.tables.ShoppingList;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by kamil on 27.11.2017.
 */

public class ShoppingListDAO {
    private static final String TAG = "ShoppingListDAO";
    private static Dao<ShoppingList, Integer> shoppingListDAO;
    private static ShoppingListDAO instance;

    private ShoppingListDAO(){
        Connection.getInstance();
        Log.i(TAG, "Creating ShoppingList DAO");

        try {
            shoppingListDAO = DaoManager.createDao(Connection.getConnectionSource(), ShoppingList.class);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        Log.i(TAG, "ShoppingList DAO was created");
    }

    public static ShoppingListDAO getInstance(){
        if(instance == null){
            instance = new ShoppingListDAO();
        }

        return instance;
    }

    public static ShoppingList getShoppingList(String shopListName, long userId){
        QueryBuilder<ShoppingList, Integer> queryBuilder = shoppingListDAO.queryBuilder();

        try {
            queryBuilder.where().like(ShoppingList.SHOPPING_LIST_NAME, shopListName).and().like(ShoppingList.SHOPPING_LIST_OWNER, userId);
            return shoppingListDAO.queryForFirst(queryBuilder.prepare());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    public static List<ShoppingList> getShoppingListForSpecifiedUser(long userId){
        QueryBuilder<ShoppingList, Integer> queryBuilder = shoppingListDAO.queryBuilder();

        try {
            queryBuilder.where().like(ShoppingList.SHOPPING_LIST_OWNER, userId);
            return shoppingListDAO.query(queryBuilder.prepare());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }
}
