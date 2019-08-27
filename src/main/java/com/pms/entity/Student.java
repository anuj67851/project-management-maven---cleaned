package com.pms.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Student extends User {
    private String firstname;
    private String middlename;
    private String lastname;
    private Project project;
    private List<Evaluation> evaluation;
    private List<Presentation> presentation;
    private int weeklySub;
    private int status;
    private String reasonSubOp;
    private Files confLetter;
    private int confLetterStatus;
    private int weeklyAlerts;
    private int allowProjectEditing;

    @Basic
    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "middlename")
    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    @Basic
    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "weeklySub")
    public int getWeeklySub() {
        return weeklySub;
    }

    public void setWeeklySub(int weeklySub) {
        this.weeklySub = weeklySub;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "confLetterStatus")
    public int getConfLetterStatus() {
        return confLetterStatus;
    }

    public void setConfLetterStatus(int confLetterStatus) {
        this.confLetterStatus = confLetterStatus;
    }

    @Basic
    @Column(name = "weeklyAlerts")
    public int getWeeklyAlerts() {
        return weeklyAlerts;
    }

    public void setWeeklyAlerts(int weeklyAlerts) {
        this.weeklyAlerts = weeklyAlerts;
    }

    @Basic
    @Column(name = "allowProjectEditing")
    public int getAllowProjectEditing() {
        return allowProjectEditing;
    }

    public void setAllowProjectEditing(int allowProjectEditing) {
        this.allowProjectEditing = allowProjectEditing;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "confLetter")
    public Files getConfLetter() {
        return confLetter;
    }

    public void setConfLetter(Files confLetter) {
        this.confLetter = confLetter;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Evaluation> getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(List<Evaluation> evaluation) {
        this.evaluation = evaluation;
    }

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Presentation> getPresentation() {
        return presentation;
    }

    public void setPresentation(List<Presentation> presentation) {
        this.presentation = presentation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(firstname, student.firstname) &&
                Objects.equals(middlename, student.middlename) &&
                Objects.equals(lastname, student.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, middlename, lastname);
    }

}
