package com.apec.android.views.fragments;

import android.view.View;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.fragments.core.BaseFragment;

/**
 * Created by duanlei on 2016/6/7.
 */
public class MeFragment extends BaseFragment {
    @Override
    protected void initUI(View view) {

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {

    }

    @Override
    protected void initPresenter() {

    }
}
