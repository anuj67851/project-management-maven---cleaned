package com.pms.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Evaluation {
    private int id;
    private Date subdate;
    private Student student;
    private Review ratings;
    private Files report;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "subdate")
    public Date getSubdate() {
        return subdate;
    }

    public void setSubdate(Date subdate) {
        this.subdate = subdate;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report")
    public Files getReport() {
        return report;
    }

    public void setReport(Files report) {
        this.report = report;
    }

    @ManyToOne
    @JoinColumn(name = "student_id")
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @OneToOne
    @JoinColumn(name = "ratings")
    public Review getRatings() {
        return ratings;
    }

    public void setRatings(Review ratings) {
        this.ratings = ratings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evaluation that = (Evaluation) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
