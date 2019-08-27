package com.pms.Dao;

import com.pms.entity.Files;
import com.pms.entity.Project;
import com.pms.entity.Templates;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class FilesDao {

    private SessionFactory sessionFactory;

    @Autowired
    public FilesDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Transactional
    public Templates getSynopsis() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("From Templates t where t.name = :file ", Templates.class)
                .setParameter("file", "synopsis.docx")
                .getSingleResult();
    }

    @Transactional
    public Templates getVenueFile() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("From Templates t where t.name = :file ", Templates.class)
                .setParameter("file", "venues.csv")
                .getSingleResult();
    }

    @Transactional
    public List<Templates> getallTemplates() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("From Templates", Templates.class).getResultList();
    }

    @Transactional
    public Templates getTemplate(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Templates t = session.get(Templates.class, id);
        Hibernate.initialize(t.getFile());
        return t;
    }

    @Transactional
    public void updateTemplate(Integer id, Templates t) {
        Session session = sessionFactory.getCurrentSession();
        Templates template = session.get(Templates.class, id);
        template.setFile(t.getFile());
        session.save(template);
    }

    @Transactional
    public void saveSynopsis(Files synop, Integer projectId) {
        Session session = sessionFactory.getCurrentSession();
        Project project = session.get(Project.class, projectId);
        session.save(synop);
        project.setSynopsis(synop);
        session.save(project);
    }
}
