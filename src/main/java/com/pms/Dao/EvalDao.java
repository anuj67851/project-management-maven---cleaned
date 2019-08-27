package com.pms.Dao;

import com.pms.entity.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Set;

@Repository
public class EvalDao {
    private SessionFactory sessionFactory;

    @Autowired
    public EvalDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Files getReport(int evalId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Files.class, evalId);
    }

    @Transactional
    public Files getPpt(Integer pptId) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Files.class, pptId);
    }
}
