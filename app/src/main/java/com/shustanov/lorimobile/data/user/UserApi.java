package com.shustanov.lorimobile.data.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shustanov.lorimobile.api.LoginApi;

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
public class UserApi {

    private final Api api;
    @Bean
    protected LoginApi loginApi;

    public UserApi() {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder().create();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("s", loginApi.getUserSessionId())
                    .build();

            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.7:8080/app/dispatch/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .client(builder.build())
                .build();

        api = retrofit.create(Api.class);
    }

    public Observable<User> getByLogin(String login) {
        return api.
                query(getByLoginQuery(login)).
                map(users -> {
                    if (!users.isEmpty()) {
                        return users.get(0);
                    }
                    throw new NoSuchUserException(login);
                }).observeOn(AndroidSchedulers.mainThread());
    }

    private String getByLoginQuery(String login) {
        return String.format("select u from ts$ExtUser u where u.login = '%s'", login);
    }

    private interface Api {

        @GET("query.json?e=ts$ExtUser")
        Observable<List<User>> query(@Query("q") String query);
    }
}
