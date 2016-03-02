package com.apec.android.ui.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * 通过弱引用和Activity，Fragment的生命周期来解决内存泄露的问题
 * @param <T> view接口对象
 */
public abstract class BasePresenter<T> {

    protected Reference<T> mViewRef;

    public void attachView(T view) {
        mViewRef = new WeakReference<>(view);
    }

    protected T getView(){
        return mViewRef.get();
    }

    public boolean isViewAttached(){
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
