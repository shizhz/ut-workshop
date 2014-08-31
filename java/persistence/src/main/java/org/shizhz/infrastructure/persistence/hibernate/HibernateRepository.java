package org.shizhz.infrastructure.persistence.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by zzshi on 8/31/14.
 */
public class HibernateRepository {
    private SessionFactory sessionFactory;

    protected Session currentSession(){
        return sessionFactory.getCurrentSession();
    }

    @Autowired
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
