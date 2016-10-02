package com.shustanov.lorimobile.data.task;

import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

@EBean(scope = EBean.Scope.Singleton)
public class TaskApi extends EntityApi<Task, TaskApi.Api> {

    @Bean
    protected TaskRepository taskRepository;

    public TaskApi() {
        init();
    }

    @Override
    public Observable<List<Task>> getAll() {
        return api().query(getAllQuery()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Task> create(Task task) {
        return api().commit(gson().toJson(task)).map(entities -> entities.get(0)).doOnNext(getRepository()::save).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Task> getById(String id) {
        return api().getById(id);
    }

    protected String getAllQuery() {
        return "select task from ts$Task task";
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
        @GET("query.json?e=ts$Task")
        Observable<List<Task>> query(@Query("q") String query);

        @GET("find.json?")
        Observable<Task> getById(@Query("e") String id);

        @POST("commit?")
        Observable<List<Task>> commit(@Body String commit);
    }
}
