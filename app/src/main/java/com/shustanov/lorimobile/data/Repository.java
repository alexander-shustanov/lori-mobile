package com.shustanov.lorimobile.data;

import com.shustanov.lorimobile.data.task.DataSource;
import com.shustanov.lorimobile.data.timeentry.TimeEntry;

import org.androidannotations.annotations.EBean;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

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
        Observable<List<Entity>> observable;
        if (dirty) {
            observable = getApi().
                    getAll().
                    doOnSubscribe(getDbDataSource()::clear).
                    doOnNext(this::saveAll).
                    doOnCompleted(() -> dirty = false).
                    observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = Observable.<List<Entity>>create(
                    subscriber -> {
                        subscriber.onNext(getDbDataSource().getAll());
                        subscriber.onCompleted();
                    }
            );
        }
        return observable.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Entity> getById(String id) {
        Observable<Entity> observable;
        if (dirty) {
            observable = getApi().getById(id);
        } else {
            observable = Observable.create(subscriber -> {
                subscriber.onNext(getDbDataSource().getById(id));
                subscriber.onCompleted();
            });
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
        return getApi().create(entity).doOnNext(this::save).observeOn(AndroidSchedulers.mainThread());
    }
}
