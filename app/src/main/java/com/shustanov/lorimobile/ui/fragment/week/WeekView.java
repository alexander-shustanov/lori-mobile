package com.shustanov.lorimobile.ui.fragment.week;

import com.depthguru.mvp.annotations.EMvpView;
import com.depthguru.mvp.annotations.stretegy.Once;
import com.depthguru.mvp.annotations.stretegy.Single;
import com.depthguru.mvp.api.View;
import com.shustanov.lorimobile.data.timeentry.TimeEntry;

import org.joda.time.LocalDate;

import java.util.List;

@EMvpView
interface WeekView extends View {
    @Single
    void setWeek(LocalDate monday);

    @Single
    void update(List<TimeEntry> entries);

    @Single
    void setRefreshing(boolean refreshing);

    @Once
    void refreshFailed(Throwable throwable);

    @Once
    void startEditing(TimeEntry timeEntry);

    @Once
    void deletionFailed(Throwable throwable);
}
