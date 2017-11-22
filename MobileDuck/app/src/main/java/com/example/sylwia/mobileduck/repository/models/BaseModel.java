package com.example.sylwia.mobileduck.repository.models;

/**
 * Created by vegor on 22.11.2017.
 */

public class BaseModel {
    private long id;
    public BaseModel(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }
}
