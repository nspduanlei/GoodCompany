package com.apec.android.mvp.views;

/**
 * Created by duanlei on 2016/5/11.
 */
public interface LoginView extends View {
    void getVerCodeReceived();
    void bindUser();
    void completeData();
    void verCodeError();
}
