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

import rx.Observable;
import rx.schedulers.Schedulers;

import static rx.Observable.defer;
import static rx.Observable.just;

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

    Observable<List<TimeEntry>> getForWeek(Date from, Date to) {
        return defer(() -> just(
                timeEntryDao
                        .queryBuilder()
                        .where(TimeEntryDao.Properties.Date.between(from, to))
                        .list()
        )).subscribeOn(Schedulers.io());
    }
}
