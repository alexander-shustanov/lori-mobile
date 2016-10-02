package com.shustanov.lorimobile.data.timeentry;


import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;
import com.shustanov.lorimobile.data.task.DataSource;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

@EBean(scope = EBean.Scope.Singleton)
public class TimeEntryRepository extends Repository<TimeEntry> {

    @Bean
    protected TimeEntryDbDataSource timeEntryDataSource;

    @Bean
    protected TimeEntryApi api;

    @Override
    protected DataSource<TimeEntry> getDbDataSource() {
        return timeEntryDataSource;
    }

    @Override
    protected EntityApi<TimeEntry, ?> getApi() {
        return api;
    }
}
