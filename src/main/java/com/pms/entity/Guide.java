package com.pms.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Guide extends User {
    private String name;
    private List<Project> primaryProjects;
    private List<Project> secondaryProjects;
    private int projectLoadPrimary;
    private int projectLoadSecondary;
    private int isExaminer;
    private int isTempExaminer;

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "projectLoadPrimary")
    public int getProjectLoadPrimary() {
        return projectLoadPrimary;
    }

    public void setProjectLoadPrimary(int projectLoadPrimary) {
        this.projectLoadPrimary = projectLoadPrimary;
    }

    @Basic
    @Column(name = "projectLoadSecondary")
    public int getProjectLoadSecondary() {
        return projectLoadSecondary;
    }

    public void setProjectLoadSecondary(int projectLoadSecondary) {
        this.projectLoadSecondary = projectLoadSecondary;
    }

    @Basic
    @Column(name = "isExaminer")
    public int getIsExaminer() {
        return isExaminer;
    }

    public void setIsExaminer(int isExaminer) {
        this.isExaminer = isExaminer;
    }

    @Basic
    @Column(name = "isTempExaminer")
    public int getIsTempExaminer() {
        return isTempExaminer;
    }

    public void setIsTempExaminer(int isTempExaminer) {
        this.isTempExaminer = isTempExaminer;
    }

    @OneToMany(mappedBy = "primaryGuide")
    public List<Project> getPrimaryProjects() {
        return primaryProjects;
    }

    public void setPrimaryProjects(List<Project> primaryProjects) {
        this.primaryProjects = primaryProjects;
    }

    @OneToMany(mappedBy = "secondaryGuide")
    public List<Project> getSecondaryProjects() {
        return secondaryProjects;
    }

    public void setSecondaryProjects(List<Project> secondaryProjects) {
        this.secondaryProjects = secondaryProjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guide guide = (Guide) o;
        return Objects.equals(name, guide.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + isExaminer;
        return result;
    }

}
