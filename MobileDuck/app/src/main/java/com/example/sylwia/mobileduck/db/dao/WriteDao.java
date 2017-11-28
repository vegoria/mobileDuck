package com.example.sylwia.mobileduck.db.dao;

/**
 * Created by pawel on 28.11.2017.
 */

public interface WriteDao<T> {
    void save(T entity);
    void update(T entity);
    void delete(T entity);
}
