package com.example.sylwia.mobileduck.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by vegor on 22.11.2017.
 */

@DatabaseTable(tableName = "user")
public class User {
    public static final String USER_ID = "idUser";
    public static final String USER_LOGIN = "login";

    @DatabaseField(generatedId = true, columnName = USER_ID)
    public long id;

    @DatabaseField(columnName = USER_LOGIN)
    public String login;

    public User(){
        // musi być domyślny konstruktor zdefiniowany
    }

    public User(String login){
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
