package com.example.sylwia.mobileduck.db.dao;

import android.util.Log;

import com.example.sylwia.mobileduck.db.Connection;
import com.example.sylwia.mobileduck.db.tables.User;
import com.example.sylwia.mobileduck.db.tables.UserFriendKey;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by kamil on 27.11.2017.
 */

public class UserFriendKeyDAO {
    private static final String TAG = "UserFriendKeyDAO";
    private static UserFriendKeyDAO instance;
    private static Dao<UserFriendKey, Integer> userFriendDao;

    private UserFriendKeyDAO(){
        Connection.getInstance();
        UserDAO.getInstance();

        Log.i(TAG, "Creating UserFriendKey DAO");

        try {
            userFriendDao = DaoManager.createDao(Connection.getConnectionSource(), UserFriendKey.class);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        Log.i(TAG, "UserFriendKey DAO was created");
    }

    public static UserFriendKeyDAO getInstance(){
        if(instance == null){
            instance = new UserFriendKeyDAO();
        }

        return instance;
    }

    public static void addUserFriendKey(UserFriendKey userFriendKey){
        try {
            userFriendDao.create(userFriendKey);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static void removeFriend(User user, User userFriend){
        DeleteBuilder<UserFriendKey, Integer> deleteBuilder = userFriendDao.deleteBuilder();

        try {
            deleteBuilder.where().like(UserFriendKey.USER_ID, user.getId()).and().like(UserFriendKey.FRIEND_ID, userFriend.getId());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            userFriendDao.delete(deleteBuilder.prepare());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static List<User> getUserFriends(User user){
        QueryBuilder<UserFriendKey, Integer> userFriendKeyIntegerQueryBuilder = userFriendDao.queryBuilder();
        QueryBuilder<User, Integer> userIntegerQueryBuilder = UserDAO.getUserDao().queryBuilder();

        try {
            userFriendKeyIntegerQueryBuilder.where().like(UserFriendKey.USER_ID, user.getId());
            userIntegerQueryBuilder.join(userFriendKeyIntegerQueryBuilder);
            return UserDAO.getUserDao().query(userIntegerQueryBuilder.prepare());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }
}