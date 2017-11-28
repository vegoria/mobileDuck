package com.example.sylwia.mobileduck.db.dao.readDao;

import android.util.Log;

import com.example.sylwia.mobileduck.db.Connection;
import com.example.sylwia.mobileduck.db.dao.ReadDao;
import com.example.sylwia.mobileduck.db.tables.Item;
import com.example.sylwia.mobileduck.db.tables.ShoppingList;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by pawel on 28.11.2017.
 */

public class ItemReadDao implements ReadDao<Item> {

    private static final String TAG = "ItemDao";
    private static Dao<Item, Integer> itemDao;

    public ItemReadDao() {
        Connection.getInstance();
        try {
            itemDao = DaoManager.createDao(Connection.getConnectionSource(), Item.class);
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public Item get(long id) {
        QueryBuilder<Item, Integer> queryBuilder = itemDao.queryBuilder();

        try {
            queryBuilder.where().like(Item.ITEM_ID, id);
            return itemDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public List<Item> getShoppingListItems(ShoppingList list) {
        QueryBuilder<Item, Integer> queryBuilder = itemDao.queryBuilder();

        try {
            queryBuilder.where().like(Item.ITEM_SHOP_LIST_ID, list.getId());
            return itemDao.query(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    public List<Item> getAll() {
        try {
            return itemDao.queryForAll();
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
