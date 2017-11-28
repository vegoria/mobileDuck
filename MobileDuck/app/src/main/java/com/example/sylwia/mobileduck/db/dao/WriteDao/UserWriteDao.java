package com.example.sylwia.mobileduck.db.dao.WriteDao;

import android.util.Log;

import com.example.sylwia.mobileduck.db.Connection;
import com.example.sylwia.mobileduck.db.dao.UserDAO;
import com.example.sylwia.mobileduck.db.tables.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;

/**
 * Created by pawel on 28.11.2017.
 */

public class UserWriteDao implements WriteDao<User> {

    private static final String TAG = "UserDao";
    private static Dao<User, Integer> userDao;

    public UserWriteDao() {
        Connection.getInstance();
        try {
            userDao = DaoManager.createDao(Connection.getConnectionSource(), User.class);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void save(User user) {
        if(user == null) {
            return;
        }

        try {
            userDao.create(user);
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void update(User user) {
        if (user == null) {
            return;
        }

        try {
            userDao.update(user);
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        if(user == null) {
            return;
        }

        DeleteBuilder<User, Integer> deleteBuilder = userDao.deleteBuilder();

        try {
            deleteBuilder.where().like(User.USER_LOGIN, user.getLogin());
            userDao.delete(deleteBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
