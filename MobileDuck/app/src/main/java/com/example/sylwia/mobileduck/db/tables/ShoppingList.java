package com.example.sylwia.mobileduck.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vegor on 22.11.2017.
 */

@DatabaseTable(tableName = "shoplist")
public class ShoppingList {
    public static final String SHOPPING_LIST_ID = "idShopList";
    public static final String SHOPPING_LIST_OWNER = "fkUser";
    public static final String SHOPPING_LIST_MODIFICATION_DATE = "modificationDate";
    public static final String SHOPPING_LIST_NAME = "name";

    @DatabaseField(generatedId = true, columnName = SHOPPING_LIST_ID)
    private int id;

    @DatabaseField(columnName = SHOPPING_LIST_NAME)
    private String name;

    @DatabaseField(columnName = SHOPPING_LIST_MODIFICATION_DATE)
    private Date modificationDate;

    @DatabaseField(columnName = SHOPPING_LIST_OWNER)
    private long owner;

    public ShoppingList() {

    }

    public ShoppingList(String name, Date modificationDate, long owner) {
        this.name = name;
        this.modificationDate = modificationDate;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
