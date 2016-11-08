package com.shustanov.lorimobile.data;

import com.shustanov.lorimobile.data.task.DataSource;

import org.androidannotations.annotations.EBean;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

import static com.shustanov.lorimobile.rx.DoOnFirst.onFirstDo;

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
            return getApi()
                    .getAll()
                    .compose(onFirstDo(getDbDataSource()::clear))
                    .doOnNext(this::saveAll)
                    .doOnCompleted(() -> dirty = false)
                    .onErrorResumeNext(
                            throwable -> getDbDataSource()
                                    .getAll()
                                    .concatWith(Observable.error(throwable))
                    )
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            return getDbDataSource()
                    .getAll();
        }
    }

    public Observable<Entity> getById(String id) {
        Observable<Entity> observable;
        if (dirty) {
            observable = getApi()
                    .getById(id)
                    .onErrorResumeNext(
                            throwable -> getDbDataSource()
                                    .getById(id)
                                    .concatWith(Observable.error(throwable))
                    );
        } else {
            observable = getDbDataSource()
                    .getById(id);
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

    public Observable<Entity> commit(Entity entity) {
        return getApi()
                .commit(entity)
                .doOnNext(this::save);
    }


}
