package com.apec.android.mvp.presenters;

import com.apec.android.mvp.views.GoodDetailView;
import com.apec.android.mvp.views.View;

import javax.inject.Inject;

/**
 * Created by duanlei on 2016/5/16.
 */
public class GoodDetailPresenter implements Presenter {

    GoodDetailView mGoodDetailView;

    @Inject
    public GoodDetailPresenter() {

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
        mGoodDetailView = (GoodDetailView) v;
    }

    @Override
    public void onCreate() {

    }
}
