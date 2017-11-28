package com.example.sylwia.mobileduck.db.dao.readDao;

import android.util.Log;

import com.example.sylwia.mobileduck.db.Connection;
import com.example.sylwia.mobileduck.db.dao.ReadDao;
import com.example.sylwia.mobileduck.db.tables.ShoppingList;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by pawel on 28.11.2017.
 */

public class ShoppingListReadDao implements ReadDao<ShoppingList> {
    private static final String TAG = "ShoppingListDap";
    private static Dao<ShoppingList, Integer> shoppingListDao;

    public ShoppingListReadDao() {
        Connection.getInstance();
        try {
            shoppingListDao = DaoManager.createDao(Connection.getConnectionSource(), ShoppingList.class);
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public ShoppingList get(long id) {
        QueryBuilder<ShoppingList, Integer> queryBuilder = shoppingListDao.queryBuilder();
        try {
            queryBuilder.where().like(ShoppingList.SHOPPING_LIST_ID, id);
            return shoppingListDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public ShoppingList get(String name, long userId) {
        QueryBuilder<ShoppingList, Integer> queryBuilder = shoppingListDao.queryBuilder();

        try {
            queryBuilder
                    .where()
                    .like(ShoppingList.SHOPPING_LIST_NAME, name)
                    .and()
                    .like(ShoppingList.SHOPPING_LIST_OWNER, userId);
            return shoppingListDao.queryForFirst(queryBuilder.prepare());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    public List<ShoppingList> getUserShoppingLists(long userId) {
        QueryBuilder<ShoppingList, Integer> queryBuilder = shoppingListDao.queryBuilder();
        try {
            queryBuilder.where().like(ShoppingList.SHOPPING_LIST_OWNER, userId);
            return shoppingListDao.query(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    public List<ShoppingList> getAll() {
        try {
            return shoppingListDao.queryForAll();
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
