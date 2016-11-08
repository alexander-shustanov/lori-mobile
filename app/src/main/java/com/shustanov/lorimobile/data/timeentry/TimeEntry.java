package com.shustanov.lorimobile.data.timeentry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;

@Entity
public class TimeEntry {
    public static final String ID_PREFIX = "ts$TimeEntry-";
    @Unique
    private String id;
    @Property
    private Date date;
    @Property
    private int timeInMinutes;
    @Property
    private String status;
    @Property
    private String taskId;
    @Property
    private String taskName;
    @Property
    private String description;

    @Keep
    public TimeEntry(Date date,
                     int timeInMinutes, String status, String taskId, String taskName, String description) {
        this.id = "NEW-ts$TimeEntry";
        this.date = date;
        this.timeInMinutes = timeInMinutes;
        this.status = status;
        this.taskId = taskId;
        this.description = description;
        this.taskName = taskName;
    }

    @Generated(hash = 1130902128)
    public TimeEntry(String id, Date date, int timeInMinutes, String status, String taskId, String taskName,
            String description) {
        this.id = id;
        this.date = date;
        this.timeInMinutes = timeInMinutes;
        this.status = status;
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
    }

    @Generated(hash = 561589019)
    public TimeEntry() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTimeInMinutes() {
        return this.timeInMinutes;
    }

    public void setTimeInMinutes(int timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
