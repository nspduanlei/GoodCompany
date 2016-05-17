package com.apec.android.views.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.domain.entities.user.Area;
import com.apec.android.injector.components.DaggerGoodsComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.GoodsPresenter;
import com.apec.android.mvp.views.GoodsView;
import com.apec.android.ui.activity.user.ShoppingCartActivity;
import com.apec.android.util.AppUtils;
import com.apec.android.util.SPUtils;
import com.apec.android.util.StringUtils;
import com.apec.android.util.T;
import com.apec.android.views.activities.base.BaseActivity;
import com.apec.android.views.adapter.IconPageViewAdapter;
import com.apec.android.views.utils.LoginUtil;
import com.apec.android.views.view.CityDialog;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/5/9.
 */
public class GoodsActivity extends BaseActivity implements GoodsView {

    IconPageViewAdapter mAdapter;

    @BindView(R.id.vp_goods)
    ViewPager mVpGoods;
    @BindView(R.id.indicator)
    TabPageIndicator mIndicator;

    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.drawer)
    DrawerLayout mDrawer;

    @BindView(R.id.view_menu)
    View mMenu;

    @Inject
    GoodsPresenter mGoodsPresenter;

    LoginUtil mLoginUtil;

    List<Area> mCityData = new ArrayList<>();
    int mCityId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initViewPager();
        initLocation();
        initUser();
    }

    @Override
    protected void initUi() {
        setContentView(R.layout.activity_goods);
        mLoginUtil = new LoginUtil(this, mMenu);
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        DaggerGoodsComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(application.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mCityId = (int)SPUtils.get(this, SPUtils.LOCATION_CITY_ID, 0);
        if (mCityId == 0) {
            mGoodsPresenter.startLocation();
        }
        mGoodsPresenter.initPresenter(mCityId);
        mGoodsPresenter.attachView(this);
        mGoodsPresenter.onCreate();
    }

    private void initUser() {
        mLoginUtil.updateUser();
        //注册广播, 用于监听用户信息的变化
        registerBroadcastReceiver();
    }


    private void initLocation() {
        //开放城市显示
        String cityName = (String) SPUtils.get(this, SPUtils.LOCATION_CITY_NAME,
                Constants.DEFAULT_CITY_NAME);
        if (!StringUtils.isNullOrEmpty(cityName)) {
            mTvLocation.setText(cityName);
        }
    }

    private void initToolbar() {
        setSupportActionBar(mToolBar);
        if (getSupportActionBar()!= null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer,
                mToolBar, R.string.open_string, R.string.close_string);
        actionBarDrawerToggle.syncState();
        mToolBar.setNavigationIcon(R.drawable.menu_icon);
    }

    private void initViewPager() {
        mAdapter = new IconPageViewAdapter(getSupportFragmentManager());
        mVpGoods.setAdapter(mAdapter);
        mIndicator.setViewPager(mVpGoods);
    }

    @OnClick(R.id.iv_shopping_cart)
    void onShoppingCartClicked(View view) {
        //跳转到购物车
        Intent intent = new Intent(this, ShoppingCartActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_location)
    void onSelectLocation(View view) {
        //TODO 选择开放城市
        mGoodsPresenter.getOpenCityList();
    }

    @Override
    public void showErrorView(String s) {

    }

    @Override
    public void hideErrorView(String s) {

    }

    @Override
    public void bindLocation(String cityId, String cityName) {

    }

    @Override
    public void bindCity(List<Area> areas) {
        mCityData.clear();
        mCityData.addAll(areas);

        new CityDialog(this, mCityData, (selectCityId, selectCityName) -> {
            if (selectCityName != null || mCityId != selectCityId) {
                mCityId = selectCityId;
                SPUtils.put(this, SPUtils.LOCATION_CITY_ID,
                        selectCityId);
                SPUtils.put(this, SPUtils.LOCATION_CITY_NAME,
                        selectCityName);
                updateGoods();
            }
        }).showSelectCityDialog();
    }

    private void updateGoods() {
        mAdapter.notifyDataSetChanged();
        String cityName = (String) SPUtils.get(this, SPUtils.LOCATION_CITY_NAME,
                Constants.DEFAULT_CITY_NAME);
        mTvLocation.setText(cityName);
    }

    public static final String ACTION_USER_UPDATE = "用户修改";
    //定义广播
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_USER_UPDATE:
                    mLoginUtil.updateUser();
                    break;
            }
        }
    };

    //注册广播
    public void registerBroadcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_USER_UPDATE);
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    private boolean isExit;
    //快速点击两下返回退出app
    private void exit() {
        if (!isExit) {
            isExit = true;
            T.showShort(this, "再按一次退出" + AppUtils.getAppName(this));
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(Gravity.LEFT)) {
            mDrawer.closeDrawer(Gravity.LEFT);
        } else {
            exit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }
}
