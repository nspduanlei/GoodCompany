package com.apec.android.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apec.android.ui.presenter.BasePresenter;

/**
 * Created by Administrator on 2016/2/29.
 */
public abstract class BaseListFragment<V, T extends BasePresenter<V>> extends ListFragment {

    protected T mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);

    }

    protected abstract int getFragmentLayout();

    protected abstract T createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
