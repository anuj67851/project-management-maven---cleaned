package com.pms.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Project {
    private int id;
    private String projectId;
    private String title;
    private Company company;
    private Set<Student> students;
    private Guide primaryGuide;
    private Guide secondaryGuide;
    private int approvalStatus;
    private String tools;
    private String technologies;
    private Date startingDate;
    private Files synopsis;

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
    @Column(name = "project_id")
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "approvalStatus")
    public int getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    @Basic
    @Column(name = "tools")
    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    @Basic
    @Column(name = "technologies")
    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    @Basic
    @Column(name = "startingDate")
    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Synopsis")
    public Files getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(Files synopsis) {
        this.synopsis = synopsis;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @OneToMany(mappedBy = "project")
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @ManyToOne
    @JoinColumn(name = "guide1")
    public Guide getPrimaryGuide() {
        return primaryGuide;
    }

    public void setPrimaryGuide(Guide primaryGuide) {
        this.primaryGuide = primaryGuide;
    }

    @ManyToOne
    @JoinColumn(name = "guide2")
    public Guide getSecondaryGuide() {
        return secondaryGuide;
    }

    public void setSecondaryGuide(Guide secondaryGuide) {
        this.secondaryGuide = secondaryGuide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id &&
                Objects.equals(projectId, project.projectId) &&
                Objects.equals(title, project.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, title);
    }

}
