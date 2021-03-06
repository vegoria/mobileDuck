package com.example.sylwia.mobileduck.db.dao.writeDao;

import android.util.Log;

import com.example.sylwia.mobileduck.db.Connection;
import com.example.sylwia.mobileduck.db.tables.User;
import com.example.sylwia.mobileduck.db.tables.UserFriendKey;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;

/**
 * Created by pawel on 28.11.2017.
 */

public class UserFriendWriteDao {
    private static final String TAG = "UserFriendKeyDao";
    private static Dao<UserFriendKey, Integer> userFriendDao;

    public UserFriendWriteDao(){

        try {
            userFriendDao = DaoManager.createDao(Connection.getInstance().getConnectionSource(), UserFriendKey.class);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public boolean save(UserFriendKey userFriendKey) {
        if(userFriendKey == null) {
            return false;
        }

        try {
            userFriendDao.create(userFriendKey);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

    public void deleteFriend(User user, User friend) {
        if(user == null || friend == null) {
            return;
        }

        DeleteBuilder<UserFriendKey, Integer> deleteBuilder = userFriendDao.deleteBuilder();

        try {
            deleteBuilder.where().like(UserFriendKey.USER_ID, user.getId()).and().like(UserFriendKey.FRIEND_ID, friend.getId());
            userFriendDao.delete(deleteBuilder.prepare());

        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
