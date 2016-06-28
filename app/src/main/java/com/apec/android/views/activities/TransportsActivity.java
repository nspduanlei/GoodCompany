package com.apec.android.views.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.adapter.GoodsCAdapter;
import com.apec.android.views.fragments.GoodsFragment;
import com.flyco.tablayout.SlidingTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by duanlei on 2016/6/28.
 */
public class TransportsActivity extends BaseActivity {

    String mOrderId;

    @BindView(R.id.tabs)
    SlidingTabLayout mTabs;
    @BindView(R.id.vp_goods)
    ViewPager mVpGoods;

    GoodsCAdapter mAdapter;


    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_transport, R.string.title_transport);
    }

    @Override
    protected void initUi() {
        mOrderId = getIntent().getStringExtra("orderId");

//        setupViewPager(mVpGoods, );
//        mTabs.setViewPager(mVpGoods);
    }

//    private void setupViewPager(ViewPager viewPager) {
//        mAdapter = new GoodsCAdapter(getSupportFragmentManager());
//        //mAdapter.addFragment(new RecommendFragment(), "推荐");
//        for (int i = 0; i < CONTENT.length; i++) {
//            mAdapter.addFragment(GoodsFragment.newInstance(IDS[i]), CONTENT[i]);
//        }
//        viewPager.setAdapter(mAdapter);
//        viewPager.setOffscreenPageLimit(5);
//    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
