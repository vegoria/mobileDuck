package com.example.sylwia.mobileduck.db.dao;

import android.util.Log;

import com.example.sylwia.mobileduck.db.Connection;
import com.example.sylwia.mobileduck.db.tables.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;

/**
 * Created by kamil on 26.11.2017.
 */

public class UserDAO {
    private static final String TAG = "UserDAO";
    private static Dao<User, Integer> userDao;

    private static UserDAO instance;

    private UserDAO(){
        Connection.getInstance();
        Log.i(TAG, "Creating User DAO");

        try {
            userDao = DaoManager.createDao(Connection.getConnectionSource(), User.class);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        Log.i(TAG, "User DAO was created");
    }

    public static UserDAO getInstance(){
        if(instance == null){
            instance = new UserDAO();
        }

        return instance;
    }

    public static User getUser(String login){
        QueryBuilder<User, Integer> queryBuilder = userDao.queryBuilder();

        try {
            queryBuilder.where().like(User.USER_LOGIN, login);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            return userDao.queryForFirst(queryBuilder.prepare());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    public static User getUser(long id){
        QueryBuilder<User, Integer> queryBuilder = userDao.queryBuilder();

        try {
            queryBuilder.where().like(User.USER_ID, id);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            return userDao.queryForFirst(queryBuilder.prepare());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    public static Dao<User, Integer> getUserDao() {
        return userDao;
    }
}
