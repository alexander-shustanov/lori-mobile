package com.shustanov.lorimobile.rx;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

public class BatchLoading {

    public static <T> Observable<List<T>> create(Func1<Integer, Observable<List<T>>> batchLoaderFactory, int batchSize) {
        return Observable
                .just(0)
                .repeat()
                .scan((first, integer) -> first + batchSize)
                .concatMap(batchLoaderFactory::call)
                .takeUntil(timeEntryServerViews -> timeEntryServerViews.size() != batchSize);
    }
}
