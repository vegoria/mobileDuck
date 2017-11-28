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

    private static final String TAG = "UserDAO";
    private static Dao<User, Integer> userDao;

//    private static UserWriteDao instance;

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
        try {
            userDao.create(user);
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {
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
