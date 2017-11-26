package com.example.sylwia.mobileduck.db.tables;


/**
 * Created by vegor on 22.11.2017.
 */

public class Item extends BaseModel {
    private String name;
    private int quantity;
    private boolean isChecked;

    public Item(long id, String name, int quantity, boolean isChecked){
        super(id);
        this.name = name;
        this.quantity = quantity;
        this.isChecked = isChecked;
    }

    public Item(String name, int quantity){
        super(0);
        this.name = name;
        this.quantity = quantity;
        this.isChecked = false;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name= name;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public boolean isChecked(){
        return isChecked;
    }

    public void check(){
        this.isChecked = true;
    }

    public void uncheck(){
        this.isChecked = false;
    }

}
