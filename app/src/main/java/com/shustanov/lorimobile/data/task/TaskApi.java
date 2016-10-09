package com.shustanov.lorimobile.data.task;

import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;
import com.shustanov.lorimobile.rx.BatchLoader;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EBean(scope = EBean.Scope.Singleton)
public class TaskApi extends EntityApi<Task, TaskApi.Api> {
    private static final int BATCH_SIZE = 10;
    @Bean
    TaskRepository taskRepository;

    public TaskApi() {
        init();
    }

    @Override
    public Observable<List<Task>> getAll() {
        return BatchLoader.create(first -> api().getAll(first, BATCH_SIZE),BATCH_SIZE, Schedulers.io());
    }

    @Override
    public Observable<Task> create(Task task) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Task> getById(String id) {
        return api().getById(id);
    }

    @Override
    protected Class<Api> getApiClass() {
        return Api.class;
    }

    @Override
    protected Repository<Task> getRepository() {
        return taskRepository;
    }

    public interface Api {

        @GET("query.json?e=ts$Task&q=select task from ts$Task task")
        Observable<List<Task>> getAll(
                @Query("first") Integer first,
                @Query("max") Integer max);

        @GET("find.json?")
        Observable<Task> getById(@Query("e") String id);
    }
}
