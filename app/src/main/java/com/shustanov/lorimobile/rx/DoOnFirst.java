package com.shustanov.lorimobile.rx;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * DoOnFirst
 * </p>
 * alexander.shustanov on 04.11.16
 */
public final class DoOnFirst<T> implements Observable.Transformer<T,T> {

    private boolean done = false;
    private final Action0 action0;
    private final Action1 action1;

    private DoOnFirst(Action0 action) {
        this.action0 = action;
        this.action1 = null;
    }

    private DoOnFirst(Action1 action1) {
        this.action0 = null;
        this.action1 = action1;
    }

    @Override
    public Observable<T> call(Observable<T> observable) {
        return observable.doOnNext(t -> {
            if(!done) {
                if (action0 != null) {
                    action0.call();
                } else {
                    action1.call(t);
                }
                done = true;
            }
        });
    }

    public static <T> DoOnFirst<T> onFirstDo(Action1<T> action1) {
        return new DoOnFirst<>(action1);
    }

    public static <T> DoOnFirst<T> onFirstDo(Action0 action0) {
        return new DoOnFirst<>(action0);
    }
}
