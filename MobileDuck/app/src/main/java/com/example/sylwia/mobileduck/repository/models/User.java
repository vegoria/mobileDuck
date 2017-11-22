package com.example.sylwia.mobileduck.repository.models;


import java.util.ArrayList;
import java.util.List;

public class User extends BaseModel {
    private String login;
    private List<User> friendsList;
    private List<ShoppingList> shoppingLists;

    public User(long id, String login){
        super(id);
        this.login = login;
        friendsList = new ArrayList<>();
        shoppingLists = new ArrayList<>();
    }

    public User(String login){
        super(0);
        this.login = login;
    }

    public String getLogin(){
        return login;
    }

    public List<User> getFriends(){
        return friendsList;
    }

    public List<ShoppingList> getShoppingLists(){
        return shoppingLists;
    }
}
