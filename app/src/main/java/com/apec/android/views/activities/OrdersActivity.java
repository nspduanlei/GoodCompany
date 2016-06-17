package com.apec.android.views.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
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
    CommonTabLayout mTabs;
    @BindView(R.id.vp_orders)
    ViewPager mVpOrders;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    /**
     * 订单状态id
     */
    protected static final int[] IDS = new int[]{
            12, 13, 11, 15, 46
    };

    protected static final String[] CONTENT = new String[]{
            "待付款", "待处理", "备货中", "配送中", "已完成"
    };

    @Override
    protected void setUpContentView() {
        LoginUtil.gotoLogin(this);
        setContentView(R.layout.activity_orders_new, R.string.my_orders_title);
    }

    @Override
    protected void initUi() {
        setupViewPager(mVpOrders);

        tabLayout();
    }

    private void tabLayout() {
        for (int i = 0; i < CONTENT.length; i++) {
            mTabEntities.add(new TabEntity(CONTENT[i], 0, 0));
        }
        mTabs.setTabData(mTabEntities);

        mTabs.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mVpOrders.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mVpOrders.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabs.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mVpOrders.setCurrentItem(0);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LoginUtil.onActivityResult(requestCode, resultCode, this);
    }

}
