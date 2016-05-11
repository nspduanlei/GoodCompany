package com.apec.android.domain.usercase;

import rx.Observable;

/**
 * Created by duanlei on 2016/5/10.
 */
public abstract class UseCase<T> {
    public abstract Observable<T> buildObservable();

    public Observable<T> execute() {
        return buildObservable();
    }
}
