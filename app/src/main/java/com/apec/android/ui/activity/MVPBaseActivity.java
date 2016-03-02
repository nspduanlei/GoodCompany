package com.apec.android.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.apec.android.ui.presenter.BasePresenter;

/**
 * 通过这个基类的生命周期来控制它与Presenter的关系
 * Created by duanlei on 2016/3/2.
 */
public abstract class MVPBaseActivity<V, T extends BasePresenter<V>> extends FragmentActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter(); //创建Presenter
        mPresenter.attachView((V)this); //View与Presenter建立关联
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract T createPresenter();
}
