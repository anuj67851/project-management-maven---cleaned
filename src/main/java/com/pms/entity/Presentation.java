package com.pms.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Presentation {
    private int id;
    private Date subdate;
    private Student student;
    private Review ratings;
    private Files ppt;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ppt")
    public Files getPpt() {
        return ppt;
    }

    public void setPpt(Files ppt) {
        this.ppt = ppt;
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
        Presentation that = (Presentation) o;
        return id == that.id &&
                Objects.equals(subdate, that.subdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subdate);
    }
}
