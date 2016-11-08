package com.shustanov.lorimobile.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shustanov.lorimobile.data.user.User;
import com.shustanov.lorimobile.data.user.UserApi;
import com.shustanov.lorimobile.data.user.UserPrefsFacade;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EBean(scope = EBean.Scope.Singleton)
public class LoginApi {
    private Api loginApi;

    @Bean
    UserPrefsFacade userPrefs;
    @Bean
    ApiPrefsFacade apiPrefs;
    @Bean
    UserApi userApi;


    private String userSessionId;

    @AfterInject
    void init() {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiPrefs.getEndpoint())
                .addCallAdapterFactory(rxAdapter)
                .build();

        loginApi = retrofit.create(Api.class);
    }

    public Observable<User> login(String userName, String password) {
        userPrefs.setUserName(userName);
        userPrefs.setPass(password);
        return loginApi.login(userName, password)
                .doOnNext(responseBody -> {
                    try {
                        this.userSessionId = responseBody.string();
                    } catch (IOException e) {
                        throw new InvalidLoginException();
                    }
                })
                .flatMap(body -> userApi.queryUser(userName))
                .doOnNext(user -> {
                    userPrefs.setUserId(user.getId());
                    userPrefs.setAuthorised(true);
                })
                .doOnError(throwable -> userPrefs.setAuthorised(false))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<User> reLogin() {
        String userName = userPrefs.getUserName();
        String password = userPrefs.getPass();
        return loginApi.login(userName, password)
                .doOnNext(responseBody -> {
                    try {
                        this.userSessionId = responseBody.string();
                    } catch (IOException e) {
                        throw new InvalidLoginException();
                    }
                })
                .flatMap(body -> userApi.queryUser(userName))
                .doOnNext(user -> {
                    userPrefs.setUserId(user.getId());
                    userPrefs.setAuthorised(true);
                })
                .doOnError(throwable -> userPrefs.setAuthorised(false))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Void> logout() {
        userPrefs.setAuthorised(false);
        return loginApi
                .logout(userSessionId)
                .timeout(2000, TimeUnit.MILLISECONDS)
                .onErrorReturn(noMatterThrowable -> null)
                .map(body -> ((Void) null))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public String getUserSessionId() {
        return userSessionId;
    }

    private interface Api {
        @GET("login")
        Observable<ResponseBody> login(@Query("u") String userName, @Query("p") String password);

        @GET("logout")
        Observable<ResponseBody> logout(@Query("s") String session);
    }
}
