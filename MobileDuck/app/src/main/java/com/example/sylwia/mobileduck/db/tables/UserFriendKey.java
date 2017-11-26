package com.example.sylwia.mobileduck.db.tables;

/**
 * Created by vegor on 22.11.2017.
 */

public class UserFriendKey {
    private long userId;
    private long friendId;

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
