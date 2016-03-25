package com.apec.android.ui.activity.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.apec.android.R;
import com.apec.android.ui.activity.MVPBaseActivity;
import com.apec.android.ui.activity.order.MyOrdersActivity;
import com.apec.android.ui.activity.order.OrderActivity;
import com.apec.android.ui.activity.user.ManageAddrActivity;
import com.apec.android.ui.activity.user.MyAccountActivity;
import com.apec.android.ui.activity.user.RegisterActivity;
import com.apec.android.ui.activity.user.RegisterFActivity;
import com.apec.android.ui.activity.user.ShoppingCartActivity;
import com.apec.android.ui.adapter.IconPageViewAdapter;
import com.apec.android.ui.presenter.goods.GoodsPresenter;
import com.viewpagerindicator.TabPageIndicator;

/**
 * 商品展示
 * Created by Administrator on 2016/2/26.
 */
public class GoodsActivity extends MVPBaseActivity<GoodsPresenter.IView,
        GoodsPresenter> implements GoodsPresenter.IView, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        initViews();
        //mPresenter.fetchGoodTypes();
    }

    @Override
    protected GoodsPresenter createPresenter() {
        return new GoodsPresenter(this);
    }

    private FrameLayout loading;

    /**
     * 初始化ui
     */
    private void initViews() {
        initToolbar();

        loading = (FrameLayout) findViewById(R.id.fl_loading);

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_goods);
        FragmentPagerAdapter adapter = new IconPageViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        //菜单
        findViewById(R.id.tv_my_account).setOnClickListener(this);
        findViewById(R.id.tv_my_order).setOnClickListener(this);
        findViewById(R.id.tv_manage_address).setOnClickListener(this);
        findViewById(R.id.tv_my_shopping_cart).setOnClickListener(this);

        //我的订单
        //findViewById(R.id.btn_my_order).setOnClickListener(this);
    }

    DrawerLayout drawerLayout;

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.findViewById(R.id.iv_shopping_cart).setOnClickListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.open_string, R.string.close_string);
        actionBarDrawerToggle.syncState();

        //drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //toolbar.setNavigationIcon(R.drawable.menu_icon);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyCase() {

    }

    @Override
    public void showGoodTypes() {
    }

    @Override
    public void onClick(View view) {
//        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
//            drawerLayout.closeDrawer(Gravity.LEFT);
//        }
        switch (view.getId()) {
            case R.id.iv_shopping_cart:
            case R.id.tv_my_shopping_cart:
                Intent intent = new Intent(this, ShoppingCartActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_my_account:
                Intent intent1 = new Intent(this, MyAccountActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_my_order:
                Intent intent2 = new Intent(this, MyOrdersActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_manage_address:
                Intent intent3 = new Intent(this, ManageAddrActivity.class);
                startActivity(intent3);
                break;
        }
    }
}