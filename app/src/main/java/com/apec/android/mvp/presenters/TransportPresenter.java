package com.apec.android.mvp.presenters;

import com.apec.android.mvp.views.TransportView;
import com.apec.android.mvp.views.View;

/**
 * Created by duanlei on 2016/6/28.
 */
public class TransportPresenter implements Presenter {

    TransportView mView;

    public TransportPresenter() {


    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mView = (TransportView) v;
    }

    @Override
    public void onCreate() {

    }
}
