package com.shustanov.lorimobile.data.timeentry;


import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;
import com.shustanov.lorimobile.data.task.DataSource;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.joda.time.LocalDate;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

@EBean(scope = EBean.Scope.Singleton)
public class TimeEntryRepository extends Repository<TimeEntry> {

    @Bean
    TimeEntryDbDataSource timeEntryDataSource;
    @Bean
    TimeEntryApi api;

    @Override
    protected DataSource<TimeEntry> getDbDataSource() {
        return timeEntryDataSource;
    }

    @Override
    protected EntityApi<TimeEntry, ?> getApi() {
        return api;
    }

    public Observable<List<TimeEntry>> getForWeek(LocalDate monday) {
        if (isDirty()) {
            return api.getForWeek(monday.toDate(), monday.plusWeeks(1).toDate());
        } else {
            return Observable.<List<TimeEntry>>create(subscriber -> {
                subscriber.onNext(timeEntryDataSource.getForWeek(monday.toDate(), monday.plusWeeks(1).toDate()));
                subscriber.onCompleted();
            }).subscribeOn(Schedulers.io());
        }
    }
}
