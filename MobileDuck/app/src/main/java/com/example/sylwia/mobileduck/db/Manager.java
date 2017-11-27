package com.example.sylwia.mobileduck.db;

import com.example.sylwia.mobileduck.db.dao.ItemDAO;
import com.example.sylwia.mobileduck.db.dao.ShoppingListDAO;
import com.example.sylwia.mobileduck.db.dao.UserDAO;
import com.example.sylwia.mobileduck.db.dao.UserFriendKeyDAO;
import com.example.sylwia.mobileduck.db.tables.Item;
import com.example.sylwia.mobileduck.db.tables.ShoppingList;
import com.example.sylwia.mobileduck.db.tables.User;
import com.example.sylwia.mobileduck.db.tables.UserFriendKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kamil on 27.11.2017.
 */

public class Manager {
    private static Manager instance;

    private Manager(){
        ItemDAO.getInstance();
        ShoppingListDAO.getInstance();
        UserDAO.getInstance();
        UserFriendKeyDAO.getInstance();
    }

    public static Manager getInstance(){
        if(instance == null){
            instance = new Manager();
        }

        return instance;
    }

    public static void addUser(String login){
        UserDAO.addUser(new User(login));
    }

    public static User getUser(long userId){
        return UserDAO.getUser(userId);
    }

    public static User getUser(String login){
        return UserDAO.getUser(login);
    }

    public static void removeUser(String login){
        UserDAO.removeUser(login);
    }

    public static void addFriend(String userLogin, String friendUserLogin){
        UserFriendKeyDAO.addUserFriendKey(new UserFriendKey(getUser(userLogin).getId(), getUser(friendUserLogin).getId()));
    }

    public static void removeFriend(String userLogin, String friendUserLogin){
        UserFriendKeyDAO.removeFriend(getUser(userLogin), getUser(friendUserLogin));
    }

    public static List<User> getUserFriends(String login){
        List<UserFriendKey> userFriendKeys = UserFriendKeyDAO.getUserFriendKeys(getUser(login));
        List<User> friends = new ArrayList<User>();

        for(UserFriendKey userFriendKey : userFriendKeys){
            friends.add(getUser(userFriendKey.getFriendId()));
        }

        return friends;
    }

    public static void addShoppingList(String shopListName, String userLogin){
        ShoppingListDAO.addShoppingList(new ShoppingList(shopListName, new Date(), getUser(userLogin).getId()));
    }

    public static ShoppingList getShoppingList(String shopListName, String userLogin){
        return ShoppingListDAO.getShoppingList(shopListName, getUser(userLogin).getId());
    }

    public static void removeShoppingList(String shopListName, String userLogin){
        ShoppingListDAO.removeShoppingList(getShoppingList(shopListName, userLogin));
    }

    public static List<ShoppingList> getShoppingListsForSpecifiedUser(String userLogin){
        return ShoppingListDAO.getShoppingListForSpecifiedUser(getUser(userLogin).getId());
    }

    public static void addItem(String name, int quantity, int status, String userLogin, String shoppingListName){
        ItemDAO.addItem(new Item(name, quantity, status, getShoppingList(shoppingListName, userLogin).getId()));
    }

    public static List<Item> getItemsFromShoppingList(ShoppingList shoppingList){
        return ItemDAO.getItemsFromShoppingList(shoppingList);
    }
}
