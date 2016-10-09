package com.shustanov.lorimobile.data.timeentry;

import com.shustanov.lorimobile.data.DbHelper;
import com.shustanov.lorimobile.data.greendao.TimeEntryDao;
import com.shustanov.lorimobile.data.task.DataSource;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;

import java.util.Date;
import java.util.List;

@EBean(scope = EBean.Scope.Singleton)
class TimeEntryDbDataSource extends DataSource<TimeEntry> {
    @Bean
    DbHelper dbHelper;

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

    List<TimeEntry> getForWeek(Date from, Date to) {
        return timeEntryDao.queryBuilder().where(TimeEntryDao.Properties.Date.between(from, to)).list();
    }
}
