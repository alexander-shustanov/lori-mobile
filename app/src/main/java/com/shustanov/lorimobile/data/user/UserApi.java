package com.shustanov.lorimobile.data.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shustanov.lorimobile.api.LoginApi;
import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EBean(scope = EBean.Scope.Singleton)
public class UserApi extends EntityApi<User, UserApi.Api> {

    UserApi() {
        init();
    }

    @GET("query.json?e=ts$ExtUser")
    public Observable<User> queryUser(String userLogin) {
        return api().queryUser(getByLoginQuery(userLogin)).map(users -> users.get(0));
    }

    private String getByLoginQuery(String login) {
        return String.format("select u from ts$ExtUser u where u.login = '%s'", login);
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

    @Override
    protected String getAllQuery() {
        throw new UnsupportedOperationException();
    }

    interface Api {
        @GET("query.json?e=ts$ExtUser")
        Observable<List<User>> query(@Query("q") String query);

        @GET("query.json?e=ts$ExtUser&view=group.browse")
        Observable<List<User>> queryUser(@Query("q") String query);
    }
}
