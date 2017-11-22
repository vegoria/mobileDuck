package com.example.sylwia.mobileduck.tables;

/**
 * Created by vegor on 22.11.2017.
 */

public class FriendTable {
    public static final String TABLE_NAME = "friend";

    public class UserFriendColumns{
        public static final String USER_ID = "fkUser";
        public static final String FRIEND_ID = "fkFriend";
    }
}
