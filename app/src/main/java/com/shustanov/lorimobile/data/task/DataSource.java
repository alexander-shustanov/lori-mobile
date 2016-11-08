package com.shustanov.lorimobile.data.task;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

public abstract class DataSource<Entity> {
    public void save(Entity entity) {
        getDao().insertOrReplace(entity);
    }

    public void delete(Entity entity) {
        getDao().delete(entity);
    }

    public Observable<List<Entity>> getAll() {
        return Observable.defer(() -> Observable.just(getDao().loadAll()))
                .subscribeOn(Schedulers.io());
    }

    public Observable<Entity> getById(String id) {
        return Observable.defer(() -> Observable.just(getDao().queryBuilder().where(getIdProperty().eq(id)).build().list()))
                .subscribeOn(Schedulers.io())
                .flatMapIterable(entities -> entities)
                .first();
    }

    public void saveAll(List<Entity> entities) {
        getDao().insertOrReplaceInTx(entities);
    }

    public void clear() {
        getDao().deleteAll();
    }

    protected abstract Property getIdProperty();

    protected abstract AbstractDao<Entity, Void> getDao();
}
