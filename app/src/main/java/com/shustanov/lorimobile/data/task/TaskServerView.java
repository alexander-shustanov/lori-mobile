package com.shustanov.lorimobile.data.task;

import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

/**
 * TaskServerView
 * </p>
 * alexander.shustanov on 12.11.16
 */
public class TaskServerView {
    String id ;
    String name;
    String status;
    Project project;

    public Task buildEntity() {
        return new Task(id, name, status, project.id);
    }

    static class Project {
        String id;
    }
}
