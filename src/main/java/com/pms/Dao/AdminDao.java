package com.pms.Dao;

import com.pms.entity.Admin;
import com.pms.entity.Guide;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AdminDao {
    private SessionFactory sessionFactory;

    @Autowired
    public AdminDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Transactional
    public List<Admin> getAllAdmins() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("From Admin", Admin.class).getResultList();
    }

    @Transactional
    public String addAdmin(Admin a) {
        Session session = sessionFactory.getCurrentSession();
        List<Admin> admins = session.createQuery("From Admin a where a.username = :admin", Admin.class)
                .setParameter("admin", a.getUsername())
                .getResultList();
        if (admins.size() > 0) {
            return "clasha";
        }

        session.save(a);
        return "adda";
    }

    @Transactional
    public void updateProfile(int id, String email, String name, String phone) {
        Session session = sessionFactory.getCurrentSession();
        Admin admin = session.get(Admin.class, id);
        admin.setEmail(email);
        admin.setName(name);
        admin.setPhone(phone);
        session.save(admin);
    }
}

