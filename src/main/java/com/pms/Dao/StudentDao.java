package com.pms.Dao;

import com.pms.entity.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class StudentDao {
    private SessionFactory sessionFactory;
    private S3Repository s3Repository;

    @Autowired
    public StudentDao(SessionFactory sessionFactory, S3Repository s3Repository) {
        this.sessionFactory = sessionFactory;
        this.s3Repository = s3Repository;
    }

    @Transactional
    public List<Student> getRollsActivatedAndFree(Integer curNo) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Student s where s.id != :cur and s.project is null", Student.class)
                .setParameter("cur", curNo)
                .getResultList();

    }


    @Transactional
    public Student getStudentByRollProject(String roll) {
        Session session = sessionFactory.getCurrentSession();
        Student s = null;

        if (roll != null && !roll.equals("")) {
            List<Student> slist = session.createQuery("from Student s where s.username = :roll", Student.class).setParameter("roll", roll).setMaxResults(1).getResultList();

            s = slist.size() > 0 ? slist.get(0) : null;
        }

        return s;
    }

    @Transactional
    public void setStudentStatus(int id, int i) {
        Session session = sessionFactory.getCurrentSession();

        Student s = session.get(Student.class, id);
        s.setStatus(i);
        session.save(s);
    }

    @Transactional
    public Student updateStudentProfile(int id, String fname, String mname, String lname, String email, String phone) {
        Session session = sessionFactory.getCurrentSession();

        Student student = session.get(Student.class, id);
        student.setFirstname(fname);
        student.setMiddlename(mname);
        student.setLastname(lname);
        student.setEmail(email);
        student.setPhone(phone);
        session.save(student);
        return student;
    }

    @Transactional
    public List<Student> getAllStudents() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("From Student s order by s.username asc", Student.class).getResultList();
    }

    @Transactional
    public Student getAllDetails(String roll) {
        Session session = sessionFactory.getCurrentSession();

        List<Student> students = session.createQuery("From Student s where s.username = :roll", Student.class)
                .setParameter("roll", roll)
                .getResultList();

        if (students.size() > 0) {
            Student student = students.get(0);
            Hibernate.initialize(student.getEvaluation());
            Hibernate.initialize(student.getPresentation());
            Hibernate.initialize(student.getProject());
            return student;
        }
        return null;
    }

    @Transactional
    public void deleteStudent(String suser) {
        Session session = sessionFactory.getCurrentSession();

        List<Student> students = session.createQuery("from Student s where s.username = :user", Student.class)
                .setParameter("user", suser)
                .getResultList();
        if (students.size() == 0) {
            return;
        }
        Student student = students.get(0);

        Hibernate.initialize(student.getConfLetter());
        s3Repository.deleteFile(student.getConfLetter().getKey());

        Hibernate.initialize(student.getPresentation());
        Hibernate.initialize(student.getEvaluation());

        for (Presentation presentation : student.getPresentation()) {
            s3Repository.deleteFile(presentation.getPpt().getKey());
        }

        for (Evaluation evaluation : student.getEvaluation()) {
            s3Repository.deleteFile(evaluation.getReport().getKey());
        }


        Hibernate.initialize(student.getProject());
        Project p = student.getProject();

        if (p != null) {
            Hibernate.initialize(p.getStudents());

            if (p.getStudents().size() == 1) {
                Hibernate.initialize(p.getSynopsis());
                Hibernate.initialize(p.getPrimaryGuide());
                Hibernate.initialize(p.getSecondaryGuide());
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

                session.delete(p);
                session.delete(student);
                s3Repository.deleteFile(p.getSynopsis().getKey());
            } else {
                p.getStudents().remove(student);
                session.delete(student);
                session.save(p);
            }
        } else {
            session.delete(student);
        }
    }

    @Transactional
    public List<Student> getFreeStudents() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("From Student s where s.project is null", Student.class).getResultList();
    }

    @Transactional
    public void removeProject(String roll) {
        Session session = sessionFactory.getCurrentSession();
        Student student = session.createQuery("From Student s where s.username = :roll", Student.class)
                .setParameter("roll", roll)
                .getSingleResult();

        Project p = student.getProject();

        Hibernate.initialize(p.getStudents());

        student.setProject(null);
        student.setStatus(0);
        session.save(student);
        if (p.getStudents().size() == 1) {
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
            Hibernate.initialize(p.getSynopsis());
            session.delete(p);
            s3Repository.deleteFile(p.getSynopsis().getKey());
        } else {
            p.getStudents().remove(student);
            session.save(p);
        }
    }

    @Transactional
    public String addStudent(Student s) {
        Session session = sessionFactory.getCurrentSession();
        List<Student> student = session.createQuery("From Student s where s.username = :roll", Student.class)
                .setParameter("roll", s.getUsername())
                .getResultList();
        if (student.size() > 0) {
            return "clashs";
        }

        session.save(s);
        return "adds";
    }

    @Transactional
    public List<String> getAllStudentsUsernames() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Student", Student.class).getResultList().stream().map(User::getUsername).collect(Collectors.toList());
    }

    @Transactional
    public void addStudentDirect(Student s) {
        Session session = sessionFactory.getCurrentSession();
        session.save(s);
    }

    @Transactional
    public Files getConfLetter(String student) {
        Session session = sessionFactory.getCurrentSession();
        List<Student> students = session.createQuery("From Student s where s.username = :roll", Student.class)
                .setParameter("roll", student)
                .getResultList();
        if (students.size() > 0) {
            Student s = students.get(0);
            Hibernate.initialize(s.getConfLetter());
            return s.getConfLetter();
        }
        return null;
    }

    @Transactional
    public void rejectFinRequest(String username) {
        Session session = sessionFactory.getCurrentSession();
        List<Student> students = session.createQuery("From Student s where s.username = :roll", Student.class)
                .setParameter("roll", username)
                .getResultList();
        if (students.size() > 0) {
            Student s = students.get(0);
            s.setStatus(2);
            session.save(s);
        }
    }

    @Transactional
    public void approveFinRequest(String username) {
        Session session = sessionFactory.getCurrentSession();
        List<Student> students = session.createQuery("From Student s where s.username = :roll", Student.class)
                .setParameter("roll", username)
                .getResultList();
        if (students.size() > 0) {
            Student s = students.get(0);
            s.setStatus(4);
            session.save(s);

            Hibernate.initialize(s.getProject());
            Project p = s.getProject();

            if (p != null) {
                Hibernate.initialize(p.getStudents());

                Set<Student> studentSet = p.getStudents();

                int counter = 0;
                for (Student temp : studentSet) {
                    if (!temp.getUsername().equals(s.getUsername()) && temp.getStatus() != 4) {
                        counter++;
                    }
                }

                if (counter == 0) {
                    p.setApprovalStatus(2);
                    session.save(p);
                }
            }
        }
    }

    @Transactional
    public List<Student> getAllStudentsWithProject() {
        Session session = sessionFactory.getCurrentSession();

        List<Student> students = session.createQuery("From Student s order by s.username asc", Student.class).getResultList();
        students.forEach(student -> Hibernate.initialize(student.getProject()));
        return students;
    }

    @Transactional
    public void allowEditing(String roll) {
        Session session = sessionFactory.getCurrentSession();
        List<Student> students = session.createQuery("From Student s where s.username = :roll", Student.class)
                .setParameter("roll", roll)
                .getResultList();
        if (students.size() > 0) {
            Student s = students.get(0);
            s.setAllowProjectEditing(1);
            session.save(s);
        }
    }

    @Transactional
    public void allowSubmission(String roll) {
        Session session = sessionFactory.getCurrentSession();
        List<Student> students = session.createQuery("From Student s where s.username = :roll", Student.class)
                .setParameter("roll", roll)
                .getResultList();
        if (students.size() > 0) {
            Student s = students.get(0);
            s.setWeeklySub(1);
            session.save(s);
        }
    }
}
