package com.gmugu.happytour.dao.impl;

import com.gmugu.happytour.dao.IBaseDao;
import org.hibernate.*;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.util.List;

/**
 * Created by mugu on 16-4-25.
 */
public class BaseDao implements IBaseDao {
    private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();

    protected SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Returns the ThreadLocal Session instance. Lazy initialize the
     * <code>SessionFactory</code> if needed.
     *
     * @return Session
     * @throws HibernateException
     */
    @Override
    public Session getSession() throws HibernateException {
        Session session = threadLocal.get();

        if (session == null || !session.isOpen()) {
            session = (sessionFactory != null) ? sessionFactory.openSession()
                    : null;
            threadLocal.set(session);
        }
        return session;
    }

    /**
     * Close the single hibernate session instance.
     *
     * @throws HibernateException
     */
    @Override
    public void closeSession() throws HibernateException {
        Session session = threadLocal.get();
        threadLocal.set(null);

        if (session != null) {
            session.close();
        }
    }

    @Override
    public Transaction beginTransaction() {
        return getSession().beginTransaction();
    }


    public List query(String hql, Object... values) {
        Query query = getSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; ++i) {
                query.setParameter(i, values);
            }
        }
        return query.list();
    }

}
