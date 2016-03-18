package com.apec.android.ui.activity.goods;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import com.apec.android.R;
import com.apec.android.ui.activity.MVPBaseActivity;
import com.apec.android.ui.adapter.IconPageViewAdapter;
import com.apec.android.ui.presenter.goods.GoodsPresenter;
import com.viewpagerindicator.TabPageIndicator;

/**
 * 商品展示
 * Created by Administrator on 2016/2/26.
 */
public class GoodsActivity extends MVPBaseActivity<GoodsPresenter.IView,
        GoodsPresenter> implements GoodsPresenter.IView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        initViews();
        mPresenter.fetchGoodTypes();
    }

    @Override
    protected GoodsPresenter createPresenter() {
        return new GoodsPresenter(this);
    }

    FrameLayout loading;

    /**
     * 初始化ui
     */
    private void initViews() {
        loading = (FrameLayout) findViewById(R.id.fl_loading);

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_goods);
        FragmentPagerAdapter adapter = new IconPageViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
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
}