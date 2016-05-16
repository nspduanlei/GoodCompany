package com.apec.android.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.injector.components.DaggerGoodDetailComponent;
import com.apec.android.injector.components.DaggerGoodsComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.views.activities.base.ActivityImpl;
import com.apec.android.views.activities.base.BaseActivity;
import butterknife.ButterKnife;

/**
 * Created by duanlei on 2016/5/13.
 */
public class GoodDetailActivity extends BaseActivity implements ActivityImpl {

    public final static String EXTRA_GOODS_ID = "goods_id";

    int goodId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
        initArgument();
        initDependencyInjector();
        initPresenter();
    }

    private void initArgument() {
        goodId = getIntent().getIntExtra(EXTRA_GOODS_ID, 0);
    }

    @Override
    public void initUi() {
        setContentView(R.layout.activity_good_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initDependencyInjector() {
        MyApplication myApplication = (MyApplication) getApplication();
        DaggerGoodDetailComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(myApplication.getAppComponent())
                .build().inject(this);
    }

    @Override
    public void initPresenter() {

    }
}
