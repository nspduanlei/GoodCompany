package com.apec.android.domain;

/**
 * Created by duanlei on 2016/3/18.
 */
public class CommonBack<T> {
    private H h;
    private T b;

    public H getH() {
        return h;
    }

    public void setH(H h) {
        this.h = h;
    }

    public T getB() {
        return b;
    }

    public void setB(T b) {
        this.b = b;
    }
}
