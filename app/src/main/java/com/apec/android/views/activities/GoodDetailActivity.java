package com.apec.android.views.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.databinding.ActivityGoodDetailBinding;
import com.apec.android.domain.entities.goods.Good;
import com.apec.android.injector.components.DaggerGoodDetailComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.injector.modules.GoodDetailModule;
import com.apec.android.mvp.presenters.GoodDetailPresenter;
import com.apec.android.mvp.views.GoodDetailView;
import com.apec.android.views.activities.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by duanlei on 2016/5/13.
 */
public class GoodDetailActivity extends BaseActivity implements GoodDetailView {

    public final static String EXTRA_GOODS_ID = "goods_id";
    int goodId;
    @Inject
    GoodDetailPresenter mGoodDetailPresenter;

    ActivityGoodDetailBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    private void initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_good_detail);
    }

    @Override
    protected void initUi() {
        setContentView(R.layout.activity_good_detail);
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        goodId = getIntent().getIntExtra(EXTRA_GOODS_ID, 0);
        DaggerGoodDetailComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(application.getAppComponent())
                .goodDetailModule(new GoodDetailModule(goodId))
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mGoodDetailPresenter.attachView(this);
        mGoodDetailPresenter.onCreate();
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoodDetailPresenter.onStop();
    }

    @Override
    public void bindGood(Good good) {
        mBinding.setGood(good);
    }
}
