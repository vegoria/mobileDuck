package com.example.sylwia.mobileduck.db;

import com.example.sylwia.mobileduck.db.dao.readDao.ItemReadDao;
import com.example.sylwia.mobileduck.db.dao.readDao.ShoppingListReadDao;
import com.example.sylwia.mobileduck.db.dao.readDao.UserFriendReadDao;
import com.example.sylwia.mobileduck.db.dao.readDao.UserReadDao;
import com.example.sylwia.mobileduck.db.dao.writeDao.ItemWriteDao;
import com.example.sylwia.mobileduck.db.dao.writeDao.ShoppingListWriteDao;
import com.example.sylwia.mobileduck.db.dao.writeDao.UserFriendWriteDao;
import com.example.sylwia.mobileduck.db.dao.writeDao.UserWriteDao;
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

    private UserWriteDao userWriteDao;
    private ItemWriteDao itemWriteDao;
    private ShoppingListWriteDao shoppingListWriteDao;
    private UserFriendWriteDao userFriendWriteDao;

    private UserReadDao userReadDao;
    private ItemReadDao itemReadDao;
    private ShoppingListReadDao shoppingListReadDao;
    private UserFriendReadDao userFriendReadDao;

    public Manager(){
        userWriteDao = new UserWriteDao();
        itemWriteDao = new ItemWriteDao();
        shoppingListWriteDao = new ShoppingListWriteDao();
        userFriendWriteDao = new UserFriendWriteDao();

        userReadDao = new UserReadDao();
        itemReadDao = new ItemReadDao();
        shoppingListReadDao = new ShoppingListReadDao();
        userFriendReadDao = new UserFriendReadDao();
    }

    /* Write method (add, remove) */

    public boolean addUser(String login){
        return userWriteDao.save(new User(login));
    }

    public void deleteUser(String login){
        userWriteDao.delete(new User(login));
    }

    public void addFriend(String userLogin, String friendUserLogin){
        userFriendWriteDao.save(
                new UserFriendKey(getUserByLogin(userLogin).getId(), getUserByLogin(friendUserLogin).getId()));
    }

    public void removeFriend(String userLogin, String friendUserLogin){
        userFriendWriteDao.deleteFriend(getUserByLogin(userLogin), getUserByLogin(friendUserLogin));
    }

    public void addShoppingList(String shopListName, String userLogin){
        shoppingListWriteDao.save(
                new ShoppingList(shopListName, new Date(), getUserByLogin(userLogin).getId()));
    }

    public void addItem(String name, int quantity, int status, String userLogin, String shoppingListName){
        itemWriteDao.save(
                new Item(name, quantity, status, getShoppingList(shoppingListName, userLogin).getId()));
    }
    public boolean addItem(String name, int quantity, int status, long userId, long shoppingListId){
        return itemWriteDao.save(
                new Item(name, quantity, status, shoppingListId));
    }

    public void removeShoppingList(String shopListName, String userLogin){
        shoppingListWriteDao.delete(getShoppingList(shopListName, userLogin));
    }

    /* read methods (get) */

    public User getUserById(long userId){
        return userReadDao.get(userId);
    }

    public User getUserByLogin(String login){
        return userReadDao.get(login);
    }

    public  List<User> getUserFriends(String login){
        List<UserFriendKey> userFriendKeys = userFriendReadDao.getAll(getUserByLogin(login));
        List<User> friends = new ArrayList<User>();

        for(UserFriendKey userFriendKey : userFriendKeys){
            friends.add(getUserById(userFriendKey.getFriendId()));
        }
        return friends;
    }

    public ShoppingList getShoppingList(long shoppingListId){
        return  shoppingListReadDao.get(shoppingListId);
    }

    public ShoppingList getShoppingList(String shopListName, String userLogin){
        return shoppingListReadDao.get(shopListName, getUserByLogin(userLogin).getId());
    }

    public List<ShoppingList> getUserShoppingLists(String userLogin){
        return shoppingListReadDao.getUserShoppingLists(getUserByLogin(userLogin).getId());
    }

    public List<Item> getItemsFromShoppingList(ShoppingList shoppingList){
        return itemReadDao.getShoppingListItems(shoppingList);
    }
}
