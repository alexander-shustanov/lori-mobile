package com.shustanov.lorimobile.view.timeentry;


import com.shustanov.lorimobile.data.timeentry.TimeEntry;

public class TimeEntryViewUtils {
    public static String getElapsedTime(TimeEntry timeEntry) {
        int hours = timeEntry.getTimeInMinutes() / 60;
        int minutes = timeEntry.getTimeInMinutes() - hours * 60;
        return String.format("%02d:%02d", hours, minutes);
    }
}
