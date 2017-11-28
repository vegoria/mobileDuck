package com.example.sylwia.mobileduck.db.dao.WriteDao;

import android.database.SQLException;
import android.util.Log;

import com.example.sylwia.mobileduck.db.Connection;
import com.example.sylwia.mobileduck.db.dao.ItemDAO;
import com.example.sylwia.mobileduck.db.tables.Item;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

/**
 * Created by pawel on 28.11.2017.
 */

public class ItemWriteDao implements WriteDao<Item> {

    private static final String TAG = "ItemDAO";
    private static Dao<Item, Integer> itemDao;
    private static ItemWriteDao instance;

    public ItemWriteDao() {
        Connection.getInstance();
        try {
            itemDao = DaoManager.createDao(Connection.getConnectionSource(), Item.class);
        }
        catch(java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void save(Item item) {
        try {
            itemDao.create(item);
        }
        catch(java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void update(Item item) {
        try {
            itemDao.update(item);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void delete(Item item) {
        try {
            itemDao.delete(item);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
