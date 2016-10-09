package com.shustanov.lorimobile.data.user;

import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;

import org.androidannotations.annotations.EBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

@EBean(scope = EBean.Scope.Singleton)
public class UserApi extends EntityApi<User, UserApi.Api> {

    UserApi() {
        init();
    }

    public Observable<User> queryUser(String userLogin) {
        return api().queryUser(userLogin).map(users -> users.get(0));
    }

    @Override
    protected Class<Api> getApiClass() {
        return Api.class;
    }

    @Override
    public Observable<List<User>> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<User> create(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<User> getById(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Repository<User> getRepository() {
        throw new UnsupportedOperationException();
    }

    interface Api {
        @GET("query.json?e=ts$ExtUser")
        Observable<List<User>> query(@Query("q") String query);

        @GET("query.json?e=ts$ExtUser&view=group.browse&q=select u from ts$ExtUser u where u.login = :login")
        Observable<List<User>> queryUser(@Query("login") String login);
    }
}
