package com.apec.android.mvp.presenters;

import com.apec.android.mvp.views.CompleteView;
import com.apec.android.mvp.views.View;

/**
 * Created by duanlei on 2016/6/10.
 */
public class CompletePresenter implements Presenter {

    CompleteView mCompleteView;

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
        mCompleteView = (CompleteView) v;
    }

    @Override
    public void onCreate() {

    }
}
