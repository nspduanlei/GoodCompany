package com.apec.android.views.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.mvp.presenters.ShoppingCartPresenter;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.fragments.GoodsCFragment;
import com.apec.android.views.fragments.MeFragment;
import com.apec.android.views.fragments.ShoppingCartFragment;
import com.apec.android.views.utils.TabEntity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by duanlei on 2016/6/7.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tl_main)
    CommonTabLayout mTlMain;

    private String[] mTitles = {"首页", "购物车", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select};


    GoodsCFragment mGoodsCFragment;
    ShoppingCartFragment mShoppingCartFragment;
    MeFragment mMeFragment;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_main_new, -1, MODE_NONE);
    }

    @Override
    protected void initUi() {
        mGoodsCFragment = new GoodsCFragment();
        mShoppingCartFragment = new ShoppingCartFragment();
        mMeFragment = new MeFragment();

        mFragments.add(mGoodsCFragment);
        mFragments.add(mShoppingCartFragment);
        mFragments.add(mMeFragment);

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mTlMain.setTabData(mTabEntities, this, R.id.fl_change, mFragments);
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGoodsCFragment.onActivityResult(requestCode, resultCode, data);
        mShoppingCartFragment.onActivityResult(requestCode, resultCode, data);
    }
}
