package com.apec.android.views.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.util.DensityUtils;
import com.apec.android.util.ScreenUtils;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.adapter.GoodsCAdapter;
import com.apec.android.views.fragments.OrderFragment;
import com.apec.android.views.utils.LoginUtil;
import com.apec.android.views.utils.TabEntity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by duanlei on 2016/6/10.
 */
public class OrdersActivity extends BaseActivity {

    @BindView(R.id.tabs)
    SlidingTabLayout mTabs;
    @BindView(R.id.vp_orders)
    ViewPager mVpOrders;

    /**
     * 订单状态id
     */
    protected static final int[] IDS = new int[]{
            1, 2, 5, 3, 4
    };

    protected static final String[] CONTENT = new String[]{
            "待处理", "备货中", "配送中", "已完成", "已取消"
    };


    @Override
    protected void setUpContentView() {
        LoginUtil.gotoLogin(this);
        setContentView(R.layout.activity_orders_new, R.string.my_orders_title);
    }

    @Override
    protected void initUi() {
        setupViewPager(mVpOrders);
        mTabs.setViewPager(mVpOrders);
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

    GoodsCAdapter mAdapter;

    private void setupViewPager(ViewPager viewPager) {
        mAdapter =
                new GoodsCAdapter(getSupportFragmentManager());

        for (int i = 0; i < CONTENT.length; i++) {
            mAdapter.addFragment(OrderFragment.newInstance(IDS[i], i), CONTENT[i]);
        }
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LoginUtil.onActivityResult(requestCode, resultCode, this);

        if (requestCode == Constants.REQUEST_CODE_ORDER_DETAIL) {
            //updateData();
        }
    }

    public void setMsgCount(int count, int position) {
        if (count == 0) {
            mTabs.hideMsg(position);
        } else {
            mTabs.showMsg(position, count);
        }
    }

    public void updateData() {
        mAdapter.notifyDataSetChanged();
    }

    public void hideMsgCount(int position) {
        mTabs.hideMsg(position);
    }

}
