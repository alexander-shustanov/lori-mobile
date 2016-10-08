package com.shustanov.lorimobile.api;

import com.shustanov.lorimobile.data.timeentry.TimeEntry;

import java.util.Date;

public class TimeEntryServerView {

    public String id;
    public Date date;
    public String timeInHours;
    public String timeInMinutes;
    public String status;
    public Task task;
    public User user;

    public static TimeEntryServerView build(TimeEntry timeEntry, String userId) {
        TimeEntryServerView entryCommit = new TimeEntryServerView();
        entryCommit.id = timeEntry.getId();
        entryCommit.date = timeEntry.getDate();
        entryCommit.timeInHours = timeEntry.getTimeInHours();
        entryCommit.timeInMinutes = timeEntry.getTimeInMinutes();
        entryCommit.status = timeEntry.getStatus();

        entryCommit.user = new User(userId);
        entryCommit.task = new Task(timeEntry.getTaskId());

        return entryCommit;
    }

    public TimeEntry buildTimeEntry() {
        return new TimeEntry(id, date, timeInHours, timeInMinutes, status, task.id);
    }

    static class Task {
        String id;

        public Task(String id) {
            this.id = id;
        }
    }

    static class User {
        String id;

        public User(String id) {
            this.id = id;
        }
    }
}
