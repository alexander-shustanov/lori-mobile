package com.shustanov.lorimobile.data.timeentry;

import com.shustanov.lorimobile.api.TimeEntryServerView;
import com.shustanov.lorimobile.data.Commit;
import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;
import com.shustanov.lorimobile.data.user.UserPrefsFacade;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

@EBean(scope = EBean.Scope.Singleton)
public class TimeEntryApi extends EntityApi<TimeEntry, TimeEntryApi.Api> {

    @Bean
    UserPrefsFacade userPrefs;

    @Bean
    TimeEntryRepository timeEntryRepository;

    TimeEntryApi() {
        init();
    }

    @Override
    public Observable<List<TimeEntry>> getAll() {
        return api().query(getAllQuery()).map(timeEntryServerViews -> {
            List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
            for (TimeEntryServerView serverView : timeEntryServerViews) {
                timeEntries.add(serverView.buildTimeEntry());
            }
            return timeEntries;
        });
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

    protected String getAllQuery() {
        return "select te from ts$TimeEntry te";
    }

    interface Api {
        @GET("query.json?e=ts$Task&view=timeEntry-browse")
        Observable<List<TimeEntryServerView>> query(@Query("q") String query);

        @GET("find.json?view=timeEntry-browse")
        Observable<TimeEntryServerView> getById(@Query("e") String id);

        @POST("commit")
        Observable<List<TimeEntryServerView>> commit(@Body Commit commit);
    }
}
