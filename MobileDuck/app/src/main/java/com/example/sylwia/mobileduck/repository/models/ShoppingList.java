package com.example.sylwia.mobileduck.repository.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vegor on 22.11.2017.
 */

public class ShoppingList extends BaseModel{
    private String name;
    private Date modificationDate;
    private List<Item> itemsList;

    public ShoppingList(long id, String name, Date modificationDate){
        super(id);
        this.name = name;
        this.modificationDate = modificationDate;
        this.itemsList = new ArrayList<>();
    }

    public ShoppingList(String name, Date modificationDate){
        super(0);
        this.name = name;
        this.modificationDate = modificationDate;
        this.itemsList = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public void setName(String newName){
        name = newName;
    }

    public Date getModificationDate(){
        return modificationDate;
    }

    public List<Item> getItems(){
        return itemsList;
    }

}
