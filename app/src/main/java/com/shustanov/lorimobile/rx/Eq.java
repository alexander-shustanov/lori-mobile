package com.shustanov.lorimobile.rx;

import rx.functions.Func1;

/**
 * Eq
 * </p>
 * alexander.shustanov on 12.11.16
 */
public class Eq<T> implements Func1<T, T> {

    private static final Eq EQ = new Eq();

    private Eq() {
    }

    @Override
    public T call(T t) {
        return t;
    }

    public static <T> Eq<T> eq() {
        return EQ;
    }
}
