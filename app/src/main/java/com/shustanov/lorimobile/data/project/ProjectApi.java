package com.shustanov.lorimobile.data.project;

import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;
import com.shustanov.lorimobile.data.timeentry.TimeEntry;
import com.shustanov.lorimobile.data.user.UserPrefsFacade;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * ProjectApi
 * </p>
 * alexander.shustanov on 10.11.16
 */
@EBean(scope = EBean.Scope.Singleton)
public class ProjectApi extends EntityApi<Project, ProjectApi.Api> {

    @Bean
    ProjectRepository repository;
    @Bean
    UserPrefsFacade userPrefs;

    @Override
    public Observable<List<Project>> getAll() {
        return api().getAll(userPrefs.getUserName()).compose(reLoginOnAuthError());
    }

    @Override
    public Observable<Project> commit(Project project) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Project> getById(String id) {
        return api().getById(id).compose(reLoginOnAuthError()).flatMapIterable(projects -> projects).first();
    }

    @Override
    public Observable<TimeEntry> delete(Project project) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Class<Api> getApiClass() {
        return Api.class;
    }

    @Override
    protected Repository<Project> getRepository() {
        return repository;
    }

    interface Api {
        @GET("query.json" +
                "?e=ts$Project" +
                "&q=select pr from " +
                "   ts$Project pr, ts$ProjectParticipant pp " +
                "where " +
                "   pp.project.id = pr.id " +
                "   and " +
                "   pp.user.login = :login")
        Observable<List<Project>> getAll(@Query("login") String login);

        @GET("query.json" +
                "?e=ts$Project" +
                "&q=select pr from ts$Project pr where pr.id = :id")
        Observable<List<Project>> getById(@Query("id") String id);
    }
}
