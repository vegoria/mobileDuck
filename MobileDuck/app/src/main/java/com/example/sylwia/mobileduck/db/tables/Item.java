package com.example.sylwia.mobileduck.db.tables;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by vegor on 22.11.2017.
 */

@DatabaseTable(tableName = "item")
public class Item {
    public static final String ITEM_ID = "idItem";
    public static final String ITEM_NAME = "name";
    public static final String ITEM_QUANTITY = "quantity";
    public static final String ITEM_STATUS = "status";
    public static final String ITEM_SHOP_LIST_ID = "fkShopList";

    @DatabaseField(generatedId = true, columnName = ITEM_ID)
    private int id;

    @DatabaseField(columnName = ITEM_NAME)
    private String name;

    @DatabaseField(columnName = ITEM_QUANTITY)
    private int quantity;

    @DatabaseField(columnName = ITEM_STATUS)
    private int status;

    @DatabaseField(columnName = ITEM_SHOP_LIST_ID)
    private long shopListId;

    public Item() {

    }

    public Item(String name, int quantity, int status, long shopListId) {
        this.name = name;
        this.quantity = quantity;
        this.status = status;
        this.shopListId = shopListId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getShopListId() {
        return shopListId;
    }

    public void setShopListId(int shopListId) {
        this.shopListId = shopListId;
    }
}
