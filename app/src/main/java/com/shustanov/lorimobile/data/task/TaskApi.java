package com.shustanov.lorimobile.data.task;

import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;
import com.shustanov.lorimobile.data.timeentry.TimeEntry;
import com.shustanov.lorimobile.rx.BatchLoading;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

@EBean(scope = EBean.Scope.Singleton)
public class TaskApi extends EntityApi<Task, TaskApi.Api> {
    private static final int BATCH_SIZE = 10;

    @Bean
    TaskRepository taskRepository;

    @Override
    public Observable<List<Task>> getAll() {
        return BatchLoading
                .create(first -> api().getAll(first, BATCH_SIZE), BATCH_SIZE)
                .compose(reLoginOnAuthError());
    }

    @Override
    public Observable<Task> commit(Task task) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Task> getById(String id) {
        return api()
                .getById(id)
                .compose(reLoginOnAuthError());

    }

    @Override
    public Observable<TimeEntry> delete(Task task) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Class<Api> getApiClass() {
        return Api.class;
    }

    @Override
    protected Repository<Task> getRepository() {
        return taskRepository;
    }

    interface Api {

        @GET("query.json?e=ts$Task&q=select task from ts$Task task")
        Observable<List<Task>> getAll(
                @Query("first") Integer first,
                @Query("max") Integer max);

        @GET("find.json?")
        Observable<Task> getById(@Query("e") String id);
    }
}
