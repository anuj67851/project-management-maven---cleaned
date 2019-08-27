package com.pms.Dao;

import com.pms.entity.*;
import com.pms.utility.YearChecker;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

@Repository
public class ProjectDao {
    private SessionFactory sessionFactory;

    @Autowired
    public ProjectDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Integer addProject(Project project, Company company, Set<Student> students) {

        Session session = sessionFactory.getCurrentSession();
        company.setProject(project);
        project.setCompany(company);
        session.save(company);

        project.setStudents(students);
        session.save(project);

        for (Student temp : students) {
            Student student = session.get(Student.class, temp.getId());
            student.setProject(project);
            student.setStatus(1);
            session.save(student);
        }

        return project.getId();
    }

    @Transactional
    public Project getAllDetails(Integer id) {
        Session session = sessionFactory.getCurrentSession();

        Project p = session.get(Project.class, id);

        if (p == null) {
            return null;
        }

        Hibernate.initialize(p.getStudents());
        return p;
    }

    @Transactional
    public Files getProjectSynopsis(Integer prjId) {
        Session session = sessionFactory.getCurrentSession();

        Project p = session.get(Project.class, prjId);
        if (p == null) {
            return null;
        }
        Hibernate.initialize(p.getSynopsis());
        return p.getSynopsis();
    }

    @Transactional
    public List<Project> getAllProjectsInfo() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("From Project", Project.class).getResultList();
    }

    @Transactional
    public void addStudentToProject(String sToAdd, Integer projectId) {
        Session session = sessionFactory.getCurrentSession();

        Project p = session.get(Project.class, projectId);
        List<Student> s = session.createQuery("From Student s where s.username = :suser", Student.class)
                .setParameter("suser", sToAdd)
                .getResultList();
        if (s.size() != 0) {
            Student student = s.get(0);
            Hibernate.initialize(p.getStudents());
            if (p.getApprovalStatus() == 0) {
                student.setStatus(1);
            } else {
                student.setStatus(2);
            }
            Set<Student> allgrp = p.getStudents();
            allgrp.add(student);
            p.setStudents(allgrp);

            student.setProject(p);
            session.save(p);
            session.save(student);
        }
    }

    @Transactional
    public void removeGuide(Integer projectId, Integer gstatus) {

        Session session = sessionFactory.getCurrentSession();

        Project project = session.get(Project.class, projectId);
        if (project != null) {
            Guide g;
            if (gstatus == 1) {
                g = project.getPrimaryGuide();
                g.setProjectLoadPrimary(g.getProjectLoadPrimary() - 1);
                project.setPrimaryGuide(null);
            } else {
                g = project.getSecondaryGuide();
                g.setProjectLoadSecondary(g.getProjectLoadSecondary() - 1);
                project.setSecondaryGuide(null);
            }
            session.save(project);
            session.save(g);
        }
    }

    @Transactional
    public void setGuide(String guideUsername, Integer projectId, Integer gstatus) {
        Session session = sessionFactory.getCurrentSession();
        List<Guide> guides = session.createQuery("from Guide g where g.username = :guide", Guide.class)
                .setParameter("guide", guideUsername)
                .getResultList();

        if (guides.size() > 0) {
            Project project = session.get(Project.class, projectId);
            if (project != null) {
                Guide g = guides.get(0);
                if (gstatus == 1) {
                    project.setPrimaryGuide(g);
                    g.setProjectLoadPrimary(g.getProjectLoadPrimary() + 1);
                } else {
                    project.setSecondaryGuide(g);
                    g.setProjectLoadSecondary(g.getProjectLoadSecondary() + 1);
                }
                session.save(project);
                session.save(g);
            }
        }
    }

    @Transactional
    public String deleteProject(Integer projectId) {
        Session session = sessionFactory.getCurrentSession();

        Project p = session.get(Project.class, projectId);
        if (p != null) {

            Hibernate.initialize(p.getStudents());

            Guide pg = p.getPrimaryGuide();
            Guide sg = p.getSecondaryGuide();

            if (pg != null) {
                pg.setProjectLoadPrimary(pg.getProjectLoadPrimary() - 1);
                session.save(pg);
            }
            if (sg != null) {
                sg.setProjectLoadSecondary(sg.getProjectLoadSecondary() - 1);
                session.save(sg);
            }

            for (Student tempStudent : p.getStudents()) {
                tempStudent.setProject(null);

                tempStudent.setStatus(0);
                session.save(tempStudent);
            }

            Hibernate.initialize(p.getSynopsis());
            Files f = p.getSynopsis();
            session.delete(p);

            return f.getKey();
        }
        return null;
    }

    @Transactional
    public Set<Student> approveProject(Integer projectId) {
        Session session = sessionFactory.getCurrentSession();

        Project p = session.get(Project.class, projectId);
        if (p != null) {
            p.setApprovalStatus(1);

            List<String> projectids = ((List<String>) session.createSQLQuery("SELECT distinct project_id from project where project_id is not null and project_id != '' order by project_id asc").getResultList());
            p.setProjectId(YearChecker.generateProjectId(projectids));
            session.save(p);

            Hibernate.initialize(p.getStudents());
            for (Student tempStudent : p.getStudents()) {
                tempStudent.setStatus(2);
                session.save(tempStudent);
            }
            return p.getStudents();
        }
        return null;
    }

    @Transactional
    public Project rejectProject(Integer projectId) {
        Session session = sessionFactory.getCurrentSession();

        Project p = session.get(Project.class, projectId);
        if (p != null) {

            Hibernate.initialize(p.getStudents());
            Hibernate.initialize(p.getSynopsis());
            for (Student tempStudent : p.getStudents()) {
                tempStudent.setStatus(0);
                tempStudent.setProject(null);
                session.save(tempStudent);
            }
            session.delete(p);
            return p;
        }
        return null;
    }

    @Transactional
    public String getUniqueProjectIdCount() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(distinct p.projectId) from Project p where p.projectId is not null");
        return query.iterate().next().toString();
    }

    @Transactional
    public Project getDetailsForReport(Integer id) {
        Session session = sessionFactory.getCurrentSession();

        Project p = session.get(Project.class, id);
        if (p != null) {
            Hibernate.initialize(p.getStudents());
            p.getStudents().forEach(student -> Hibernate.initialize(student.getEvaluation()));
            p.getStudents().forEach(student -> Hibernate.initialize(student.getPresentation()));
            return p;
        }
        return p;
    }

    @Transactional
    public List<String> getUniqueProjectIds() {
        Session session = sessionFactory.getCurrentSession();
        return ((List<String>) session.createSQLQuery("SELECT distinct project_id from project where project_id is not null and project_id != '' order by project_id asc").getResultList());
    }

    @Transactional
    public Project getDetailsForExam(String projectId) {
        Session session = sessionFactory.getCurrentSession();
        Project p = session.createQuery("from Project p where p.projectId = :proId", Project.class)
                .setParameter("proId", projectId)
                .getSingleResult();
        //Hibernate.initialize(p.getStudents());
        return p;
    }
}