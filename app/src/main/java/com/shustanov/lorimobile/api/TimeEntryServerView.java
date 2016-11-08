package com.shustanov.lorimobile.api;

import com.shustanov.lorimobile.data.timeentry.TimeEntry;

import java.util.Date;

public class TimeEntryServerView {

    public String id;
    public Date date;
    public String timeInMinutes;
    public String status;
    public String description;
    public Task task;
    public User user;

    public static TimeEntryServerView build(TimeEntry timeEntry, String userId) {
        TimeEntryServerView entryCommit = new TimeEntryServerView();
        entryCommit.id = timeEntry.getId();
        entryCommit.date = timeEntry.getDate();
        entryCommit.timeInMinutes = String.valueOf(timeEntry.getTimeInMinutes());
        entryCommit.status = timeEntry.getStatus();
        entryCommit.description = timeEntry.getDescription();

        entryCommit.user = new User(userId);
        entryCommit.task = new Task(timeEntry.getTaskId());

        return entryCommit;
    }

    public static TimeEntryServerView buildForRemove(TimeEntry timeEntry) {
        TimeEntryServerView entryCommit = new TimeEntryServerView();
        entryCommit.id = timeEntry.getId();
        return entryCommit;
    }

    public TimeEntry buildTimeEntry() {
        return new TimeEntry(id,date, Integer.parseInt(timeInMinutes), status, task != null ? task.id : null, task != null ? task.name : null, description);
    }

    static class Task {
        String id;
        String name;

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
