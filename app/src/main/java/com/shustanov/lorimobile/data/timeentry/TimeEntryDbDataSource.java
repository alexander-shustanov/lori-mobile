package com.shustanov.lorimobile.data.timeentry;

import com.shustanov.lorimobile.data.DbHelper;
import com.shustanov.lorimobile.data.greendao.TimeEntryDao;
import com.shustanov.lorimobile.data.task.DataSource;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;

@EBean(scope = EBean.Scope.Singleton)
public class TimeEntryDbDataSource extends DataSource<TimeEntry> {
    @Bean
    protected DbHelper dbHelper;

    private TimeEntryDao timeEntryDao;

    @AfterInject
    protected void init() {
        timeEntryDao = dbHelper.getTimeEntryDao();
    }

    @Override
    protected AbstractDao<TimeEntry, Void> getDao() {
        return timeEntryDao;
    }

    @Override
    protected Property getIdProperty() {
        return TimeEntryDao.Properties.Id;
    }
}
