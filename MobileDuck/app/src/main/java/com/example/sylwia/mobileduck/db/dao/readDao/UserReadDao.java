package com.example.sylwia.mobileduck.db.dao.readDao;

import android.util.Log;

import com.example.sylwia.mobileduck.db.Connection;
import com.example.sylwia.mobileduck.db.tables.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by pawel on 28.11.2017.
 */

public class UserReadDao implements ReadDao<User> {

    private static final String TAG = "UserDao";
    private static Dao<User, Integer> userDao;

    public UserReadDao() {
        Connection.getInstance();
        try {
            userDao = DaoManager.createDao(Connection.getConnectionSource(), User.class);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public User get(long id) {
        QueryBuilder<User, Integer> queryBuilder = userDao.queryBuilder();

        try {
            queryBuilder.where().like(User.USER_ID, id);
            return userDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public User get(String login) {
        QueryBuilder<User, Integer> queryBuilder = userDao.queryBuilder();

        try {
            queryBuilder.where().like(User.USER_LOGIN, login);
            return userDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        try {
            return userDao.queryForAll();
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
