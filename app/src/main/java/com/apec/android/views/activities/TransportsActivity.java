package com.apec.android.views.activities;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.domain.entities.transport.TransportInfo;
import com.apec.android.domain.entities.transport.TransportInfoItem;
import com.apec.android.injector.components.DaggerOrderComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.TransportPresenter;
import com.apec.android.mvp.views.TransportView;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.adapter.GoodsCAdapter;
import com.apec.android.views.fragments.TransportFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by duanlei on 2016/6/28.
 */
public class TransportsActivity extends BaseActivity implements TransportView {

    int mOrderId;

    @BindView(R.id.tabs)
    SlidingTabLayout mTabs;
    @BindView(R.id.vp_goods)
    ViewPager mVpGoods;

    GoodsCAdapter mAdapter;

    @Inject
    TransportPresenter mPresenter;

    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_transport, R.string.title_transport);
    }

    @Override
    protected void initUi() {
        mOrderId = getIntent().getIntExtra("orderId", 0);

//        setupViewPager(mVpGoods, );
//        mTabs.setViewPager(mVpGoods);
    }

    private void setupViewPager(ViewPager viewPager, List<TransportInfoItem> list) {
        mAdapter = new GoodsCAdapter(getSupportFragmentManager());
        for (int i = 0; i < list.size(); i++) {
            mAdapter.addFragment(TransportFragment.newInstance(list.get(0)), "运单" + (i + 1));
        }
        viewPager.setAdapter(mAdapter);
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        DaggerOrderComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(application.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mPresenter.attachView(this);
        mPresenter.onCreate();
        mPresenter.getTransports(mOrderId);
    }

    @Override
    public void showLoadingView() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public void bindTransports(List<TransportInfoItem> list) {
        setupViewPager(mVpGoods, list);
        mTabs.setViewPager(mVpGoods);
    }
}
