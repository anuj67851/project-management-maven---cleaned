package com.pms.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Review {
    private int id;
    private int ts;
    private int d;
    private int t;
    private int m;
    private String comment;

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
    @Column(name = "ts")
    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    @Basic
    @Column(name = "d")
    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    @Basic
    @Column(name = "t")
    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    @Basic
    @Column(name = "m")
    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    @Basic
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id &&
                ts == review.ts &&
                d == review.d &&
                t == review.t &&
                m == review.m &&
                Objects.equals(comment, review.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ts, d, t, m, comment);
    }
}
