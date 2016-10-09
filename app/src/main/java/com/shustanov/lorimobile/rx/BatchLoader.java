package com.shustanov.lorimobile.rx;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

public class BatchLoader<T> extends Observable<List<T>> {

    private BatchLoader(OnSubscribe<List<T>> f) {
        super(f);
    }

    public static <T> Observable<List<T>> create(Func1<Integer, Observable<List<T>>> batchLoaderFactory, int batchSize, Scheduler subscribeOnScheduler) {
        return new BatchLoader<T>(subscriber -> {
            int loaded;
            int first = 0;
            do {
                List<T> views = batchLoaderFactory.call(first).toBlocking().singleOrDefault(new ArrayList<T>());
                loaded = views.size();
                subscriber.onNext(views);
                first += batchSize;
            } while (loaded >= batchSize);
            subscriber.onCompleted();
        }).subscribeOn(subscribeOnScheduler);
    }
}
