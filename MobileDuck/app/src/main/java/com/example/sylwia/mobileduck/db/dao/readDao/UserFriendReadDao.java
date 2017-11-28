package com.example.sylwia.mobileduck.db.dao.readDao;

import android.util.Log;

import com.example.sylwia.mobileduck.db.Connection;
import com.example.sylwia.mobileduck.db.dao.UserFriendKeyDAO;
import com.example.sylwia.mobileduck.db.tables.User;
import com.example.sylwia.mobileduck.db.tables.UserFriendKey;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by pawel on 28.11.2017.
 */

public class UserFriendReadDao {
    private static final String TAG = "UserFriendKeyDao";
    private static Dao<UserFriendKey, Integer> userFriendDao;

    public UserFriendReadDao() {
        Connection.getInstance();
        try {
            userFriendDao = DaoManager.createDao(Connection.getConnectionSource(), UserFriendKey.class);
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static List<UserFriendKey> getAll(User user){
        QueryBuilder<UserFriendKey, Integer> userFriendKeyIntegerQueryBuilder = userFriendDao.queryBuilder();

        try {
            userFriendKeyIntegerQueryBuilder.where().like(UserFriendKey.USER_ID, user.getId());
            return userFriendDao.query(userFriendKeyIntegerQueryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }
}
