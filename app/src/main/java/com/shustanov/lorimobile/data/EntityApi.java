package com.shustanov.lorimobile.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shustanov.lorimobile.api.ApiPrefsFacade;
import com.shustanov.lorimobile.api.LoginApi;
import com.shustanov.lorimobile.data.timeentry.TimeEntry;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

@EBean
public abstract class EntityApi<Entity, Api> {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS").create();
    @Bean
    protected LoginApi loginApi;
    @Bean
    protected ApiPrefsFacade apiPrefs;

    private Api api;

    @AfterInject
    protected void init() {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

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
                .baseUrl(apiPrefs.getEndpoint())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .client(builder.build())
                .build();

        api = retrofit.create(getApiClass());
    }

    public abstract Observable<List<Entity>> getAll();

    public abstract Observable<Entity> commit(Entity entity);

    public abstract Observable<Entity> getById(String id);

    public abstract Observable<TimeEntry> delete(Entity entity);

    protected abstract Class<Api> getApiClass();

    protected abstract Repository<Entity> getRepository();

    protected Gson gson() {
        return gson;
    }

    public Api api() {
        return api;
    }

    protected <T> Observable.Transformer<T, T> reLoginOnAuthError() {
        return observable -> observable.retryWhen(
                errors -> {
                    Observable<Integer> attempts = Observable.just(0).repeat().scan((attempt, next) -> attempt + 1);
                    return errors
                            .zipWith(attempts, (e, integer) -> {
                                boolean isSessionExpired = e instanceof HttpException && ((HttpException) e).code() == 401;
                                if (isSessionExpired && integer <= 1) {
                                    return e;
                                } else {
                                    throw new RuntimeException(e);
                                }
                            })
                            .flatMap(e -> loginApi.reLogin());
                }
        );
    }
}
