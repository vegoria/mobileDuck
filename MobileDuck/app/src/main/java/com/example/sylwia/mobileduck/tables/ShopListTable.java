package com.example.sylwia.mobileduck.tables;

/**
 * Created by vegor on 22.11.2017.
 */

public class ShopListTable {
    public static final String TABLE_NAME = "shoplist";

    public class ShopListColumns{
        public static final String ID = "idShopList";
        public static final String USER_ID = "fkUser";
        public static final String MODIFICATION_DATE = "modificationDate";
        public static final String NAME = "name";
    }
}
