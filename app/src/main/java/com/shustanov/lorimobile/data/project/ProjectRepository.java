package com.shustanov.lorimobile.data.project;

import com.shustanov.lorimobile.data.DataSource;
import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;
import com.shustanov.lorimobile.data.task.TaskRepository;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;

/**
 * ProjectRepository
 * </p>
 * alexander.shustanov on 10.11.16
 */
@EBean(scope = EBean.Scope.Singleton)
public class ProjectRepository extends Repository<Project> {
    @Bean
    ProjectApi api;
    @Bean
    ProjectDbDataSource dbDataSource;
    @Bean
    TaskRepository taskRepository;

//    public Observable<Project> getProjectByTaskId(String taskId) {
//        if(isDirty()) {
////            return taskRepository.getById(taskId).flatMap()
//        } else {
//
//        }
//    }

    @Override
    protected DataSource<Project> getDbDataSource() {
        return dbDataSource;
    }

    @Override
    protected EntityApi<Project, ?> getApi() {
        return api;
    }
}
