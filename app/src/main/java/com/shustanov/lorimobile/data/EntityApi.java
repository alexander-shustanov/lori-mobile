package com.shustanov.lorimobile.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shustanov.lorimobile.BuildConfig;
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
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EBean
public abstract class EntityApi<Entity, Api> {

    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS").create();
    @Bean
    protected LoginApi loginApi;
    private Api api;

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
                .baseUrl(BuildConfig.DEFAULT_API_PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .client(builder.build())
                .build();

        api = retrofit.create(getApiClass());
    }

    public abstract Observable<List<Entity>> getAll();

    public abstract Observable<Entity> create(Entity entity);

    public abstract Observable<Entity> getById(String id);

    protected abstract Class<Api> getApiClass();

    protected abstract Repository<Entity> getRepository();

    public Gson gson() {
        return gson;
    }

    public Api api() {
        return api;
    }

    protected abstract String getAllQuery();
}
