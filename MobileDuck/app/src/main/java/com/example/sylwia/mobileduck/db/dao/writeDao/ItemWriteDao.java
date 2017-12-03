package com.example.sylwia.mobileduck.db.dao.writeDao;

import android.util.Log;

import com.example.sylwia.mobileduck.db.Connection;
import com.example.sylwia.mobileduck.db.dao.WriteDao;
import com.example.sylwia.mobileduck.db.tables.Item;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

/**
 * Created by pawel on 28.11.2017.
 */

public class ItemWriteDao implements WriteDao<Item> {

    private static final String TAG = "ItemDao";
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
    public boolean save(Item item) {
        if(item == null) {
            return false;
        }
        try {
            itemDao.create(item);
        }
        catch(java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void update(Item item) {
        if(item == null) {
            return;
        }
        try {
            itemDao.update(item);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void delete(Item item) {
        if(item == null) {
            return;
        }
        try {
            itemDao.delete(item);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
