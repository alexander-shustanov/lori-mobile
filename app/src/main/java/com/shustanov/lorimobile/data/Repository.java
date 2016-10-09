package com.shustanov.lorimobile.data;

import com.shustanov.lorimobile.data.task.DataSource;

import org.androidannotations.annotations.EBean;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EBean
public abstract class Repository<Entity> {
    private boolean dirty = false;

    public void save(Entity entity) {
        getDbDataSource().save(entity);
    }

    public void saveAll(List<Entity> entity) {
        getDbDataSource().saveAll(entity);
    }

    public void delete(Entity entity) {
        getDbDataSource().delete(entity);
    }

    public Observable<List<Entity>> getAll() {
        if (dirty) {
            return getApi().
                    getAll().
                    doOnSubscribe(getDbDataSource()::clear).
                    doOnNext(this::saveAll).
                    doOnCompleted(() -> dirty = false).
                    observeOn(AndroidSchedulers.mainThread());
        } else {
            return Observable.<List<Entity>>create(
                    subscriber -> {
                        subscriber.onNext(getDbDataSource().getAll());
                        subscriber.onCompleted();
                    }
            )
                    .subscribeOn(Schedulers.io());
        }
    }

    public Observable<Entity> getById(String id) {
        Observable<Entity> observable;
        if (dirty) {
            observable = getApi().getById(id);
        } else {
            observable = Observable.<Entity>create(subscriber -> {
                subscriber.onNext(getDbDataSource().getById(id));
                subscriber.onCompleted();
            }).subscribeOn(Schedulers.io());
        }
        return observable.observeOn(AndroidSchedulers.mainThread());
    }

    protected abstract DataSource<Entity> getDbDataSource();

    public boolean isDirty() {
        return dirty;
    }

    public void refresh() {
        dirty = true;
    }

    protected abstract EntityApi<Entity, ?> getApi();

    public Observable<Entity> create(Entity entity) {
        return getApi().create(entity).doOnNext(this::save);
    }
}
