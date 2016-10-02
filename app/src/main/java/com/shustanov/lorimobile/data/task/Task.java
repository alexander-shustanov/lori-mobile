package com.shustanov.lorimobile.data.task;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Task {
    @Unique
    private String id ;
    @Property
    private String name;
    @Property
    private String status;

    @Keep
    public Task(String id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    @Generated(hash = 733837707)
    public Task() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
