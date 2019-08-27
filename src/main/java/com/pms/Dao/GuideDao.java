package com.pms.Dao;

import com.pms.entity.Guide;
import com.pms.entity.Student;
import com.pms.entity.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GuideDao {
    private SessionFactory sessionFactory;

    @Autowired
    public GuideDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Transactional
    public List<Guide> getAllGuides() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("From Guide g where g.isTempExaminer = 0", Guide.class).getResultList();
    }

    @Transactional
    public List<Guide> getAllGuidesAlongTemp() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("From Guide", Guide.class).getResultList();
    }

    @Transactional
    public Guide getAllDetails(String guide) {
        Session session = sessionFactory.getCurrentSession();

        List<Guide> guides = session.createQuery("From Guide g where g.username = :guide", Guide.class)
                .setParameter("guide", guide)
                .getResultList();

        if (guides.size() == 0) {
            return null;
        }

        Guide g = guides.get(0);
        Hibernate.initialize(g.getPrimaryProjects());
        Hibernate.initialize(g.getSecondaryProjects());
        return g;
    }

    @Transactional
    public String addGuide(Guide g) {
        Session session = sessionFactory.getCurrentSession();
        List<Guide> guides = session.createQuery("From Guide g where g.username = :guide", Guide.class)
                .setParameter("guide", g.getUsername())
                .getResultList();
        if (guides.size() > 0) {
            return "clashg";
        }

        session.save(g);
        return "addg";
    }

    @Transactional
    public void addGuideDirect(Guide g) {
        Session session = sessionFactory.getCurrentSession();
        session.save(g);
    }

    @Transactional
    public List<String> getAllGuideUsernames() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Guide", Guide.class).getResultList().stream().map(User::getUsername).collect(Collectors.toList());
    }

    @Transactional
    public void deleteGuide(String username) {
        Session session = sessionFactory.getCurrentSession();
        List<Guide> guides = session.createQuery("From Guide g where g.username = :guide", Guide.class)
                .setParameter("guide", username)
                .getResultList();
        if (guides.size() > 0) {
            session.delete(guides.get(0));
        }
    }

    @Transactional
    public Guide getGuide(String username) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("From Guide g where g.username = :user", Guide.class)
                .setParameter("user", username)
                .getSingleResult();
    }
}
