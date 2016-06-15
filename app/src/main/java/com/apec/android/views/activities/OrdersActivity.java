package com.apec.android.views.activities;

import android.support.v4.view.ViewPager;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.adapter.GoodsCAdapter;
import com.apec.android.views.fragments.OrderFragment;
import com.flyco.tablayout.SlidingTabLayout;

import butterknife.BindView;

/**
 * Created by duanlei on 2016/6/10.
 */
public class OrdersActivity extends BaseActivity {

    @BindView(R.id.tabs)
    SlidingTabLayout mTabs;
    @BindView(R.id.vp_orders)
    ViewPager mVpOrders;


    protected static final int[] IDS = new int[]{
            12, 13, 11, 15, 46
    };

    protected static final String[] CONTENT = new String[]{
            "待付款", "待处理", "备货中", "配送中", "已完成"
    };

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_orders_new, R.string.my_orders_title);
    }

    @Override
    protected void initUi() {
        setupViewPager(mVpOrders);
        mTabs.setViewPager(mVpOrders);

        //TODO Test
        mTabs.showMsg(1, 8);
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

    private void setupViewPager(ViewPager viewPager) {
        GoodsCAdapter adapter =
                new GoodsCAdapter(getSupportFragmentManager());

        for (int i = 0; i < CONTENT.length; i++) {
            adapter.addFragment(OrderFragment.newInstance(IDS[i]), CONTENT[i]);
        }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
    }

}
