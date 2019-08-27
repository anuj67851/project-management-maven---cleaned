package com.pms.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User {
    private String name;

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(name, admin.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
