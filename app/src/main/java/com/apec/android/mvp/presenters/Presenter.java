package com.apec.android.mvp.presenters;


import com.apec.android.mvp.views.View;

public interface Presenter {
    void onStart();

    void onStop();

    void onPause();

    void attachView(View v);

    void onCreate();
}
