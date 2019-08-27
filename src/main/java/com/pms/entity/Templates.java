package com.pms.entity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Templates {
    private int id;
    private String name;
    private byte[] file;
    private String description;

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
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "file")
    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Templates templates = (Templates) o;
        return id == templates.id &&
                Objects.equals(name, templates.name) &&
                Arrays.equals(file, templates.file) &&
                Objects.equals(description, templates.description);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, description);
        result = 31 * result + Arrays.hashCode(file);
        return result;
    }
}
