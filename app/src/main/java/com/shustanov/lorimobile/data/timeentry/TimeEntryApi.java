package com.shustanov.lorimobile.data.timeentry;

import android.text.format.DateFormat;

import com.shustanov.lorimobile.api.TimeEntryServerView;
import com.shustanov.lorimobile.data.Commit;
import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;
import com.shustanov.lorimobile.data.user.UserPrefsFacade;
import com.shustanov.lorimobile.rx.BatchLoader;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Date;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.schedulers.Schedulers;

@EBean(scope = EBean.Scope.Singleton)
public class TimeEntryApi extends EntityApi<TimeEntry, TimeEntryApi.Api> {
    private static final int BATCH_SIZE = 10;

    @Bean
    UserPrefsFacade userPrefs;

    @Bean
    TimeEntryRepository timeEntryRepository;

    TimeEntryApi() {
        init();
    }

    @Override
    public Observable<List<TimeEntry>> getAll() {
        return BatchLoader.
                create(first -> api().getAll(first, BATCH_SIZE), BATCH_SIZE, Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMapIterable(timeEntryServerViews -> timeEntryServerViews)
                .map(TimeEntryServerView::buildTimeEntry)
                .buffer(10);
    }

    @Override
    public Observable<TimeEntry> create(TimeEntry timeEntry) {
        Commit commit = new Commit.Builder().commit(TimeEntryServerView.build(timeEntry, userPrefs.getUserId())).build();
        return api().
                commit(commit).
                map(entities -> entities.get(0)).
                map(TimeEntryServerView::buildTimeEntry).
                doOnNext(getRepository()::save);
    }

    @Override
    public Observable<TimeEntry> getById(String id) {
        return api().getById(id).map(TimeEntryServerView::buildTimeEntry);
    }

    @Override
    protected Class<Api> getApiClass() {
        return Api.class;
    }

    @Override
    protected Repository<TimeEntry> getRepository() {
        return timeEntryRepository;
    }

    Observable<List<TimeEntry>> getForWeek(Date start, Date end) {
        CharSequence startDate = DateFormat.format("yyyy-MM-dd", start);
        CharSequence endDate = DateFormat.format("yyyy-MM-dd", end);
        return BatchLoader
                .create(
                        first -> api().queryForWeek(startDate, endDate, first, BATCH_SIZE),
                        BATCH_SIZE,
                        Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMapIterable(self -> self)
                .map(TimeEntryServerView::buildTimeEntry)
                .buffer(5);
    }

    interface Api {
        @GET("query.json?e=ts$TimeEntry&view=timeEntry-browse&q=select te from ts$TimeEntry te")
        Observable<List<TimeEntryServerView>> getAll(
                @Query("first") Integer first,
                @Query("max") Integer max);

        @GET("query.json?e=ts$TimeEntry&view=timeEntry-browse&q=select te from ts$TimeEntry te where te.date between :start and :end&start_type=date&end_type=date")
        Observable<List<TimeEntryServerView>> queryForWeek(
                @Query("start") CharSequence start,
                @Query("end") CharSequence end,
                @Query("first") Integer first,
                @Query("max") Integer max);

        @GET("find.json?view=timeEntry-browse")
        Observable<TimeEntryServerView> getById(@Query("e") String id);

        @POST("commit")
        Observable<List<TimeEntryServerView>> commit(@Body Commit commit);
    }
}
