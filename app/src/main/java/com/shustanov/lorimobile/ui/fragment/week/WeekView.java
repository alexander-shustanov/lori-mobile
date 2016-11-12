package com.shustanov.lorimobile.ui.fragment.week;

import com.depthguru.mvp.api.View;
import com.shustanov.lorimobile.data.timeentry.TimeEntry;

import org.joda.time.LocalDate;

import java.util.List;

interface WeekView extends View {
    long getTime();

    void setWeek(LocalDate monday);

    void update(List<TimeEntry> entries);

    void setRefreshing(boolean refreshing);

    void refreshFailed(Throwable throwable);

    void startEditing(TimeEntry timeEntry);

    void deletionFailed(Throwable throwable);
}
