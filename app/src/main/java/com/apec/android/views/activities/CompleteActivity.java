package com.apec.android.views.activities;

import android.databinding.DataBindingUtil;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.databinding.ActivityCompleteBinding;
import com.apec.android.databinding.ActivityGoodDetailBinding;
import com.apec.android.injector.components.ActivityComponent;
import com.apec.android.views.activities.core.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by duanlei on 2016/6/10.
 */
public class CompleteActivity extends BaseActivity {

    ActivityCompleteBinding mBinding;

    @Override
    protected void setUpContentView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_complete);
        ButterKnife.bind(this);
        setUpToolbar(R.string.title_complete, -1, MODE_BACK);
    }

    @Override
    protected void initUi() {

    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }
}
