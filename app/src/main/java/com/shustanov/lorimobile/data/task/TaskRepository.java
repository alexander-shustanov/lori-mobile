package com.shustanov.lorimobile.data.task;

import com.shustanov.lorimobile.data.DataSource;
import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;
import com.shustanov.lorimobile.data.project.Project;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import rx.Observable;

@EBean(scope = EBean.Scope.Singleton)
public class TaskRepository extends Repository<Task> {
    @Bean
    protected TaskDbDataSource taskDataSource;
    @Bean
    protected TaskApi api;

    public Observable<List<Task>> getByProject(Project project) {
        return api.getByProject(project);
    }

    @Override
    public void delete(Task task) {
        taskDataSource.delete(task);
    }

    @Override
    protected DataSource<Task> getDbDataSource() {
        return taskDataSource;
    }

    @Override
    protected EntityApi<Task, ?> getApi() {
        return api;
    }
}
