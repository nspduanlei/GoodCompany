package com.apec.android.ui.presenter;

/**
 * Created by duanlei on 2016/3/2.
 */
public interface BaseViewInterface {

    void hideLoading();

    void showLoading();

    void showEmptyCase();

    void showNoConnection();

    void hideNoConnection();
}
