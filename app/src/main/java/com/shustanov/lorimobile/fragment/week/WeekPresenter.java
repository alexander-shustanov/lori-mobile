package com.shustanov.lorimobile.fragment.week;

import android.support.v4.widget.SwipeRefreshLayout;

import com.depthguru.mvp.annotations.EPresenter;
import com.depthguru.mvp.api.Presenter;
import com.shustanov.lorimobile.data.timeentry.TimeEntry;
import com.shustanov.lorimobile.data.timeentry.TimeEntryRepository;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

@EBean
@EPresenter
class WeekPresenter extends Presenter<WeekView> implements SwipeRefreshLayout.OnRefreshListener {
    @Bean
    TimeEntryRepository timeEntryRepository;

    private CompositeSubscription subscription = new CompositeSubscription();

    private LocalDate monday;

    private List<TimeEntry> entries = new ArrayList<>();
    private boolean refreshing = false;
    private boolean pendingClear = false;

    @Override
    protected void onViewAttached() {
        super.onViewAttached();

        if (monday == null) {
            monday = LocalDate.fromDateFields(new Date(getView().getTime()));
            buildEntriesObservable()
                    .subscribe(this::entriesReceived, this::refreshFailed);
        }

        getView().setWeek(monday);
        updateView();
    }

    private void updateView() {
        WeekView view = getView();
        if (view != null) {
            view.update(this.entries);
            view.setRefreshing(refreshing);
        }
    }

    private void entriesReceived(List<TimeEntry> entries) {
        if (pendingClear) {
            this.entries.clear();
            pendingClear = false;
        }
        this.entries.addAll(entries);
        updateView();
    }

    @Override
    public void onRefresh() {
        pendingClear = true;
        timeEntryRepository.refresh();
        buildEntriesObservable()
                .subscribe(this::entriesReceived, this::refreshFailed, this::refreshCompleted);
    }

    private void refreshFailed(Throwable throwable) {
        refreshing = false;
        WeekView view = getView();
        if (view != null) {
            view.refreshFailed(throwable);
            view.setRefreshing(refreshing);
        }
    }

    private void refreshCompleted() {
        refreshing = false;
        updateView();
    }


    private Observable<List<TimeEntry>> buildEntriesObservable() {
        return timeEntryRepository
                .getForWeek(monday)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }
}
