package com.example.sylwia.mobileduck.db.dao;

import java.util.List;

/**
 * Created by pawel on 28.11.2017.
 */

public interface ReadDao<T> {
    T get(long id);
    List<T> getAll();
}
