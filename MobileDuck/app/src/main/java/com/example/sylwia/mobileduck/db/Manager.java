package com.example.sylwia.mobileduck.db;

import com.example.sylwia.mobileduck.db.dao.ItemDAO;
import com.example.sylwia.mobileduck.db.dao.ShoppingListDAO;
import com.example.sylwia.mobileduck.db.dao.UserFriendKeyDAO;
import com.example.sylwia.mobileduck.db.dao.readDao.ReadDao;
import com.example.sylwia.mobileduck.db.dao.readDao.UserReadDao;
import com.example.sylwia.mobileduck.db.dao.writeDao.ItemWriteDao;
import com.example.sylwia.mobileduck.db.dao.writeDao.ShoppingListWriteDao;
import com.example.sylwia.mobileduck.db.dao.writeDao.UserFriendWriteDao;
import com.example.sylwia.mobileduck.db.dao.writeDao.UserWriteDao;
import com.example.sylwia.mobileduck.db.dao.writeDao.WriteDao;
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

    private WriteDao<User> userWriteDao;
    private WriteDao<Item> itemWriteDao;
    private WriteDao<ShoppingList> shoppingListWriteDao;
    private UserFriendWriteDao userFriendWriteDao;

    private ReadDao<User> userReadDao;

    public Manager(){
        userWriteDao = new UserWriteDao();
        itemWriteDao = new ItemWriteDao();
        shoppingListWriteDao = new ShoppingListWriteDao();
        userFriendWriteDao = new UserFriendWriteDao();

        userReadDao = new UserReadDao();
        ShoppingListDAO.getInstance();
        UserFriendKeyDAO.getInstance();
    }

    /* Write method (add, remove) */

    public void addUser(String login){
        userWriteDao.save(new User(login));
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

    public static List<User> getUserFriends(String login){
        List<UserFriendKey> userFriendKeys = UserFriendKeyDAO.getUserFriendKeys(getUser(login));
        List<User> friends = new ArrayList<User>();

        for(UserFriendKey userFriendKey : userFriendKeys){
            friends.add(getUser(userFriendKey.getFriendId()));
        }
        return friends;
    }

    public static ShoppingList getShoppingList(String shopListName, String userLogin){
        return ShoppingListDAO.getShoppingList(shopListName, getUser(userLogin).getId());
    }

    public static List<ShoppingList> getShoppingListsForSpecifiedUser(String userLogin){
        return ShoppingListDAO.getShoppingListForSpecifiedUser(getUser(userLogin).getId());
    }

    public static List<Item> getItemsFromShoppingList(ShoppingList shoppingList){
        return ItemDAO.getItemsFromShoppingList(shoppingList);
    }
}
