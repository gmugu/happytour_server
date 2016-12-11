package com.gmugu.happytour.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by mugu on 16-4-25.
 */
public interface IBaseDao {

    Session getSession() throws HibernateException;

    void closeSession() throws HibernateException;

    Transaction beginTransaction();
}
