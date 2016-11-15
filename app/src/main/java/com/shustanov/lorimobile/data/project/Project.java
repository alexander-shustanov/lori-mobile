package com.shustanov.lorimobile.data.project;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Project
 * </p>
 * alexander.shustanov on 10.11.16
 */
@Entity
public class Project {
    @Unique
    private String id;
    @Property
    private String name;

    @Generated(hash = 869174935)
    public Project(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 1767516619)
    public Project() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Keep
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (id != null ? !id.equals(project.id) : project.id != null) return false;
        return name != null ? name.equals(project.name) : project.name == null;

    }

    @Keep
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
