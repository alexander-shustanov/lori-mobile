package com.shustanov.lorimobile.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shustanov.lorimobile.BuildConfig;
import com.shustanov.lorimobile.data.user.User;
import com.shustanov.lorimobile.data.user.UserApi;
import com.shustanov.lorimobile.data.user.UserPrefsFacade;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;

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

    private final Api loginApi;
    @Bean
    UserPrefsFacade userPrefs;
    @Bean
    UserApi userApi;
    private String userSessionId;

    public LoginApi() {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.DEFAULT_API_PATH)
                .addCallAdapterFactory(rxAdapter)
                .build();

        loginApi = retrofit.create(Api.class);
    }

    public Observable<User> login(String userName, String password) {
        return loginApi.login(userName, password).
                doOnNext(responseBody -> {
                    try {
                        this.userSessionId = responseBody.string();
                    } catch (IOException e) {
                        throw new InvalidLoginException();
                    }
                }).
                flatMap(body -> userApi.queryUser(userName)).
                doOnNext(user -> userPrefs.setUserId(user.getId())).
                observeOn(AndroidSchedulers.mainThread());
    }

    private String createRequestUserQuery(String userLogin) {
        return String.format("select us from ts$ExtUser us where us.login='%s'", userLogin);
    }

    public String getUserSessionId() {
        return userSessionId;
    }

    private interface Api {
        @GET("login")
        Observable<ResponseBody> login(@Query("u") String userName, @Query("p") String password);
    }
}
