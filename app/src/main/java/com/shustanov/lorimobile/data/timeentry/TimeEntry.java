package com.shustanov.lorimobile.data.timeentry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;

@Entity
public class TimeEntry {
    @Unique
    private String id;
    @Property
    private Date date;
    @Property
    private String timeInHours;
    @Property
    private String timeInMinutes;
    @Property
    private String status;
    @Property
    private String taskId;

    @Keep
    public TimeEntry(Date date, String timeInHours,
                     String timeInMinutes, String status, String taskId) {
        this.id = "NEW-ts$TimeEntry";
        this.date = date;
        this.timeInHours = timeInHours;
        this.timeInMinutes = timeInMinutes;
        this.status = status;
        this.taskId = taskId;
    }

    @Generated(hash = 561589019)
    public TimeEntry() {
    }

    @Generated(hash = 1914244752)
    public TimeEntry(String id, Date date, String timeInHours,
            String timeInMinutes, String status, String taskId) {
        this.id = id;
        this.date = date;
        this.timeInHours = timeInHours;
        this.timeInMinutes = timeInMinutes;
        this.status = status;
        this.taskId = taskId;
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

    public String getTimeInHours() {
        return this.timeInHours;
    }

    public void setTimeInHours(String timeInHours) {
        this.timeInHours = timeInHours;
    }

    public String getTimeInMinutes() {
        return this.timeInMinutes;
    }

    public void setTimeInMinutes(String timeInMinutes) {
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


}
