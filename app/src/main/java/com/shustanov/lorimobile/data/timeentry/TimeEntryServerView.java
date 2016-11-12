package com.shustanov.lorimobile.data.timeentry;

import java.util.Date;

class TimeEntryServerView {

    String id;
    Date date;
    String timeInMinutes;
    String status;
    String description;
    Task task;
    User user;

    public static TimeEntryServerView build(TimeEntry timeEntry, String userId) {
        TimeEntryServerView entryCommit = new TimeEntryServerView();
        entryCommit.id = timeEntry.getId();
        entryCommit.date = timeEntry.getDate();
        entryCommit.timeInMinutes = String.valueOf(timeEntry.getTimeInMinutes());
        entryCommit.status = timeEntry.getStatus();
        entryCommit.description = timeEntry.getDescription();

        entryCommit.user = new User(userId);
        if (timeEntry.getTaskId() != null) {
            entryCommit.task = new Task(timeEntry.getTaskId());
        }

        return entryCommit;
    }

    TimeEntry buildEntity() {
        return new TimeEntry(id, date, Integer.parseInt(timeInMinutes), status, task != null ? task.id : null, task != null ? task.name : null, description);
    }

    public static TimeEntryServerView buildForRemove(TimeEntry timeEntry) {
        TimeEntryServerView entryCommit = new TimeEntryServerView();
        entryCommit.id = timeEntry.getId();
        return entryCommit;
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
