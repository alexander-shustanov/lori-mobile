package com.shustanov.lorimobile.data.timeentry;

import android.text.format.DateFormat;

import com.shustanov.lorimobile.data.Commit;
import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;
import com.shustanov.lorimobile.data.user.UserPrefsFacade;
import com.shustanov.lorimobile.rx.BatchLoading;
import com.shustanov.lorimobile.rx.Eq;

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

import static com.shustanov.lorimobile.rx.Eq.eq;

@EBean(scope = EBean.Scope.Singleton)
public class TimeEntryApi extends EntityApi<TimeEntry, TimeEntryApi.Api> {
    private static final int BATCH_SIZE = 10;

    @Bean
    UserPrefsFacade userPrefs;

    @Bean
    TimeEntryRepository timeEntryRepository;

    @Override
    public Observable<List<TimeEntry>> getAll() {
        return BatchLoading
                .create(first -> api().getAll(first, BATCH_SIZE, userPrefs.getUserName()), BATCH_SIZE)
                .compose(reLoginOnAuthError())
                .observeOn(Schedulers.computation())
                .flatMapIterable(eq())
                .map(TimeEntryServerView::buildEntity)
                .buffer(BATCH_SIZE);
    }

    @Override
    public Observable<TimeEntry> commit(TimeEntry timeEntry) {
        Commit commit = new Commit.Builder().commit(TimeEntryServerView.build(timeEntry, userPrefs.getUserId())).build();
        return api()
                .commit(commit)
                .compose(reLoginOnAuthError())
                .flatMapIterable(eq())
                .map(TimeEntryServerView::buildEntity)
                .doOnNext(getRepository()::save);
    }

    @Override
    public Observable<TimeEntry> getById(String id) {
        return api()
                .getById(id.replace(TimeEntry.ID_PREFIX, ""))
                .compose(reLoginOnAuthError())
                .flatMapIterable(eq())
                .first()
                .map(TimeEntryServerView::buildEntity);
    }

    @Override
    public Observable<TimeEntry> delete(TimeEntry timeEntry) {
        Commit commit = new Commit.Builder().remove(TimeEntryServerView.buildForRemove(timeEntry)).build();
        return api()
                .commit(commit)
                .compose(reLoginOnAuthError())
                .flatMapIterable(eq())
                .map(TimeEntryServerView::buildEntity)
                .firstOrDefault(timeEntry);
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
        return BatchLoading
                .create(
                        first -> api().queryForWeek(startDate, endDate, first, BATCH_SIZE).compose(reLoginOnAuthError()),
                        BATCH_SIZE
                )
                .observeOn(Schedulers.computation())
                .flatMapIterable(eq())
                .map(TimeEntryServerView::buildEntity)
                .buffer(BATCH_SIZE);
    }

    interface Api {
        @GET("query.json" +
                "?e=ts$TimeEntry" +
                "&view=timeEntry-browse&q=select te from ts$TimeEntry te where te.createdBy = :userName")
        Observable<List<TimeEntryServerView>> getAll(
                @Query("first") Integer first,
                @Query("max") Integer max,
                @Query("userName") String userName);

        @GET("query.json" +
                "?e=ts$TimeEntry" +
                "&view=timeEntry-browse" +
                "&q=select te from ts$TimeEntry te where te.date between :start and :end" +
                "&start_type=date" +
                "&end_type=date")
        Observable<List<TimeEntryServerView>> queryForWeek(
                @Query("start") CharSequence start,
                @Query("end") CharSequence end,
                @Query("first") Integer first,
                @Query("max") Integer max);

        @GET("query.json" +
                "?e=ts$TimeEntry" +
                "&view=timeEntry-browse" +
                "&q=select te from ts$TimeEntry te where te.id = :id")
        Observable<List<TimeEntryServerView>> getById(@Query("id") String id);

        @POST("commit?view=timeEntry-browse")
        Observable<List<TimeEntryServerView>> commit(@Body Commit commit);
    }
}
