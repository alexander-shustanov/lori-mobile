package com.shustanov.lorimobile.data.timeentry;


import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;
import com.shustanov.lorimobile.data.task.DataSource;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;

import rx.Observable;

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
        Date from = monday.toDate();
        Date to = monday.plusWeeks(1).toDate();
        if (isDirty()) {
            return api
                    .getForWeek(from, to)
                    .doOnNext(entries -> System.out.println(entries.size()))
                    .onErrorResumeNext(throwable ->
                            timeEntryDataSource
                                    .getForWeek(from, to)
                                    .concatWith(Observable.error(throwable))
                    );
        } else {
            return timeEntryDataSource.getForWeek(from, to);
        }
    }
}
