package com.apec.android.ui.activity.goods;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.apec.android.R;
import com.apec.android.ui.fragment.goods.GoodsFragment;
import com.apec.android.ui.presenter.goods.GoodsPresenter;

/**
 * 商品展示
 * Created by Administrator on 2016/2/26.
 */
public class GoodsActivity extends FragmentActivity implements GoodsPresenter.IView {

    GoodsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        initViews();

//        mPresenter = new GoodsPresenter(this);
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
            public int getCount() {
                return 5;
            }
        });
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