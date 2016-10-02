package com.shustanov.lorimobile.data;

import java.util.List;

import rx.Observable;

public interface RetrofitApi<Entity> {
    Observable<List<Entity>> query(String query);

    Observable<Entity> getById(String id);

    Observable<List<Entity>> commit(String commit);
}
