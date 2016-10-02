package com.shustanov.lorimobile.data.timeentry;

import com.shustanov.lorimobile.data.Commit;
import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

@EBean(scope = EBean.Scope.Singleton)
public class TimeEntryApi extends EntityApi<TimeEntry, TimeEntryApi.Api> {

    @Bean
    protected TimeEntryRepository timeEntryRepository;

    public TimeEntryApi() {
        init();
    }

    @Override
    public Observable<List<TimeEntry>> getAll() {
        return api().query(getAllQuery()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<TimeEntry> create(TimeEntry timeEntry) {
        Commit commit = new Commit.Builder().commit(timeEntry).build();
        return api().
                commit(gson().toJson(commit)).
                map(entities -> entities.get(0)).
                doOnNext(getRepository()::save).
                observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<TimeEntry> getById(String id) {
        return api().getById(id);
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
        @GET("query.json?e=ts$Task")
        Observable<List<TimeEntry>> query(@Query("q") String query);

        @GET("find.json")
        Observable<TimeEntry> getById(@Query("e") String id);
//        @Headers("Content-type: application/json")
        @POST("commit")
        Observable<List<TimeEntry>> commit(@Body String commit);
    }
}
