package com.pms.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Company {
    private int id;
    private String name;
    private String address;
    private String contactno;
    private String externalGuideName;
    private String externalGuideContact;
    private String externalGuideEmail;
    private String hrname;
    private String hrContact;
    private Project project;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "contactno")
    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    @Basic
    @Column(name = "externalGuideName")
    public String getExternalGuideName() {
        return externalGuideName;
    }

    public void setExternalGuideName(String externalGuideName) {
        this.externalGuideName = externalGuideName;
    }

    @Basic
    @Column(name = "externalGuideContact")
    public String getExternalGuideContact() {
        return externalGuideContact;
    }

    public void setExternalGuideContact(String externalGuideContact) {
        this.externalGuideContact = externalGuideContact;
    }

    @Basic
    @Column(name = "externalGuideEmail")
    public String getExternalGuideEmail() {
        return externalGuideEmail;
    }

    public void setExternalGuideEmail(String externalGuideEmail) {
        this.externalGuideEmail = externalGuideEmail;
    }

    @Basic
    @Column(name = "hrname")
    public String getHrname() {
        return hrname;
    }

    public void setHrname(String hrname) {
        this.hrname = hrname;
    }

    @Basic
    @Column(name = "hrContact")
    public String getHrContact() {
        return hrContact;
    }

    public void setHrContact(String hrContact) {
        this.hrContact = hrContact;
    }

    @OneToOne(mappedBy = "company")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id == company.id &&
                Objects.equals(name, company.name) &&
                Objects.equals(address, company.address) &&
                Objects.equals(contactno, company.contactno) &&
                Objects.equals(externalGuideName, company.externalGuideName) &&
                Objects.equals(externalGuideContact, company.externalGuideContact) &&
                Objects.equals(externalGuideEmail, company.externalGuideEmail) &&
                Objects.equals(hrname, company.hrname) &&
                Objects.equals(hrContact, company.hrContact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, contactno, externalGuideName, externalGuideContact, externalGuideEmail, hrname, hrContact);
    }

}
