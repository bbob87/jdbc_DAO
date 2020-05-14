package org.example.jdbc.dao;

import java.util.List;

/*****************************/
/** DAO-CRUD common methods
 * T: entity object dealt with
 * K: identifier or index (unique key)
 * /*****************************/
public interface DAO <T, K> {

    void insert (T s) throws DAOException;

    void edit (T s, K id) throws DAOException;

    void delete (K id) throws DAOException;

    List<T> getAll() throws DAOException;

    T get(K id) throws DAOException;

}
