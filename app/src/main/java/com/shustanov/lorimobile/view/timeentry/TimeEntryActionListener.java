package com.shustanov.lorimobile.view.timeentry;

import com.shustanov.lorimobile.data.timeentry.TimeEntry;

public interface TimeEntryActionListener {
    void edit(TimeEntry timeEntry);

    void delete(TimeEntry timeEntry);
}
