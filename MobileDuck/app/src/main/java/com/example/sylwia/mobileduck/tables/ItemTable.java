package com.example.sylwia.mobileduck.tables;

/**
 * Created by vegor on 22.11.2017.
 */

public class ItemTable {
    public static final String TABLE_NAME = "item";

    public class ItemColumns{
        public static final String ID = "idItem";
        public static final String NAME = "name";
        public static final String QUANTITY = "quantity";
        public static final String STATUS = "status";
        public static final String LIST_ID = "fkShopList";
    }
}
