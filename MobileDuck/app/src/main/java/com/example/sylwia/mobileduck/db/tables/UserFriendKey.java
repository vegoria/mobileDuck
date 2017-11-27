package com.example.sylwia.mobileduck.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by vegor on 22.11.2017.
 */

@DatabaseTable(tableName = "friend")
public class UserFriendKey {
    public static final String USER_ID = "fkUser";
    public static final String FRIEND_ID = "fkFriend";

    @DatabaseField(columnName = USER_ID)
    private long userId;

    @DatabaseField(columnName = FRIEND_ID)
    private long friendId;

    public UserFriendKey() {

    }

    public UserFriendKey(long userId, long friendId){
        this.userId = userId;
        this.friendId = friendId;
    }

    public long getUserId(){
        return userId;
    }

    public long getFriendId(){
        return friendId;
    }
}
