package com.shustanov.lorimobile.data.task;

import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;
import com.shustanov.lorimobile.data.project.Project;
import com.shustanov.lorimobile.data.timeentry.TimeEntry;
import com.shustanov.lorimobile.rx.BatchLoading;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import static com.shustanov.lorimobile.rx.Eq.eq;

@EBean(scope = EBean.Scope.Singleton)
public class TaskApi extends EntityApi<Task, TaskApi.Api> {
    private static final int BATCH_SIZE = 10;

    @Bean
    TaskRepository taskRepository;

    @Override
    public Observable<List<Task>> getAll() {
        return BatchLoading
                .create(first -> api().getAll(first, BATCH_SIZE), BATCH_SIZE)
                .flatMapIterable(eq())
                .map(TaskServerView::buildEntity)
                .buffer(BATCH_SIZE)
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
                .flatMapIterable(eq())
                .first()
                .map(TaskServerView::buildEntity)
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

    public Observable<List<Task>> getByProject(Project project) {
        String projectId = project.getId().replace("ts$Project-", "");
        return BatchLoading
                .create(first -> api()
                        .getByProject(first, BATCH_SIZE, projectId)
                        .compose(reLoginOnAuthError())
                        , BATCH_SIZE)
                .flatMapIterable(eq())
                .map(TaskServerView::buildEntity)
                .buffer(BATCH_SIZE);
    }

    interface Api {

        @GET("query.json" +
                "?e=ts$Task" +
                "&q=select task from ts$Task task" +
                "&view=task-full")
        Observable<List<TaskServerView>> getAll(
                @Query("first") Integer first,
                @Query("max") Integer max);

        @GET("query.json" +
                "?e=ts$Task" +
                "&q=select task from ts$Task task where task.project.id = :project" +
                "&view=task-full")
        Observable<List<TaskServerView>> getByProject(
                @Query("first") Integer first,
                @Query("max") Integer max,
                @Query("project") String projectId);

        @GET("query.json" +
                "?e=ts$Task" +
                "&view=task-full" +
                "&q=select task from ts$Task task " +
                "   where task.id = :id")
        Observable<List<TaskServerView>> getById(@Query("id") String id);
    }
}
