package com.pms.Dao;

import com.pms.entity.Admin;
import com.pms.entity.Guide;
import com.pms.entity.Student;
import com.pms.entity.User;
import com.pms.utility.BCrypt;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class LoginDao {

    private SessionFactory sessionFactory;

    @Autowired
    public LoginDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public User getUser(String username) {
        Session session = sessionFactory.getCurrentSession();

        List<User> users = session.createQuery("From User u where u.username = :username", User.class)
                .setParameter("username", username)
                .setMaxResults(1)
                .getResultList();
        return users.size() > 0 ? users.get(0) : null;
    }

    @Transactional
    public Admin getAdmin(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Admin.class, id);
    }

    @Transactional
    public Student getStudent(int id) {
        Session session = sessionFactory.getCurrentSession();
        Student s = session.get(Student.class, id);
        Hibernate.initialize(s.getProject());
        return s;
    }

    @Transactional
    public Guide getGuide(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Guide.class, id);
    }

    @Transactional
    public boolean changePassword(String username, String pass, String salt) {
        Session session = sessionFactory.getCurrentSession();

        List<User> users = session.createQuery("From User u where u.username = :username", User.class)
                .setParameter("username", username)
                .setMaxResults(1)
                .getResultList();
        User user = users.size() > 0 ? users.get(0) : null;
        if (user != null) {
            user.setPassword(BCrypt.hashpw(pass, salt));
            user.setSalt(salt);
            return true;
        }
        return false;
    }

    @Transactional
    public void activateAccount(String user, String pass, String salt) {
        Session session = sessionFactory.getCurrentSession();

        List<User> users = session.createQuery("From User u where u.username = :username", User.class)
                .setParameter("username", user)
                .setMaxResults(1)
                .getResultList();
        if (users.size() > 0) {
            User u = users.get(0);
            u.setPassword(pass);
            u.setActivated(1);
            u.setPassword(BCrypt.hashpw(pass, salt));
            u.setSalt(salt);
            session.save(u);
        }
    }
}
