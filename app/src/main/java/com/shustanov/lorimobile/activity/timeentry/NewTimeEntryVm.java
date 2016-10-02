package com.shustanov.lorimobile.activity.timeentry;


import android.databinding.BaseObservable;

import com.shustanov.lorimobile.data.task.Task;
import com.shustanov.lorimobile.data.timeentry.TimeEntry;

import org.joda.time.LocalDate;

public class NewTimeEntryVm extends BaseObservable {
    private Task task;

    private LocalDate date = new LocalDate();

    private String description;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
        notifyChange();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        notifyChange();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TimeEntry createTimeEntry() {
        return new TimeEntry(date.toDate(), "0", "0", "new", task.getName());
    }
}
