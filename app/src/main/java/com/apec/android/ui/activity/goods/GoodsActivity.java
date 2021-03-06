package com.apec.android.ui.activity.goods;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.ui.fragment.goods.GoodsFragment;
import com.apec.android.ui.presenter.goods.GoodsPresenter;
import com.apec.android.util.DensityUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.viewpagerindicator.TabPageIndicator;

/**
 * 商品展示
 * Created by Administrator on 2016/2/26.
 */
public class GoodsActivity extends FragmentActivity implements GoodsPresenter.IView {

//    RegisterPresenter mPresenter;

    private static final String[] CONTENT =
            new String[] {"  糖  ","  米  ","  油  ","  面  ","  调味品  "
                    ,"  糖  ","  米  ","  油  ","  面  "," 调味品 "};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        initViews();

//        mPresenter = new RegisterPresenter(this);
//        mPresenter.fetchGoodTypes();

    }

    /**
     * 初始化ui
     */
    private void initViews() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_goods);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return GoodsFragment.newInstance(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return CONTENT[position % CONTENT.length].toUpperCase();
            }

            @Override
            public int getCount() {
                return CONTENT.length;
            }
        });


        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmptyCase() {

    }

    @Override
    public void showGoodTypes() {

    }
}