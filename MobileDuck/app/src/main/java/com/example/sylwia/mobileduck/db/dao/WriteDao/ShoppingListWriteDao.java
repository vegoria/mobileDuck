package com.example.sylwia.mobileduck.db.dao.writeDao;

import android.util.Log;

import com.example.sylwia.mobileduck.db.Connection;
import com.example.sylwia.mobileduck.db.dao.WriteDao;
import com.example.sylwia.mobileduck.db.tables.ShoppingList;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;

/**
 * Created by pawel on 28.11.2017.
 */

public class ShoppingListWriteDao implements WriteDao<ShoppingList> {

    private static final String TAG = "ShoppingListDao";
    private static Dao<ShoppingList, Integer> shoppingListDao;

    public ShoppingListWriteDao()
    {
        Connection.getInstance();
        try {
            shoppingListDao = DaoManager.createDao(Connection.getConnectionSource(), ShoppingList.class);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void save(ShoppingList shoppingList) {
        if(shoppingList == null) {
            return;
        }

        try {
            shoppingListDao.create(shoppingList);
        }
        catch(java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void update(ShoppingList shoppingList) {
        if(shoppingList == null) {
            return;
        }
        try {
            shoppingListDao.update(shoppingList);
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void delete(ShoppingList shoppingList) {
        if(shoppingList == null) {
            return;
        }
        try {
            shoppingListDao.delete(shoppingList);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
