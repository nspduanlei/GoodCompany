package com.apec.android.views.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.domain.entities.transport.GoodsReceipt;
import com.apec.android.support.rest.RestDataSource;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.fragments.GoodsCFragment;
import com.apec.android.views.fragments.MeFragment;
import com.apec.android.views.fragments.ShoppingCartFragment;
import com.apec.android.views.utils.ShopCartUtil;
import com.apec.android.views.utils.TabEntity;
import com.apec.android.views.view.FragmentListener;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.loveplusplus.update.UpdateChecker;

import java.util.ArrayList;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by duanlei on 2016/6/7.
 */
public class MainActivity extends BaseActivity implements FragmentListener{

    @BindView(R.id.tl_main)
    CommonTabLayout mTlMain;

    //是否在前台
    public static boolean isForeground = false;

    private String[] mTitles = {"首页", "购物车", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_cart_unselect,
            R.mipmap.tab_me_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_cart_select,
            R.mipmap.tab_me_select};


    GoodsCFragment mGoodsCFragment;
    ShoppingCartFragment mShoppingCartFragment;
    MeFragment mMeFragment;

    public static final String ACTION_USER_UPDATE = "用户修改";
    public static final String ACTION_GOOD_UPDATE = "数据更新";
    public static final String MESSAGE_RECEIVED_ACTION = "推送消息";

    public static final String ADDRESS_EDIT_ACTION = "地址编辑";

    //推送字段
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_main_new, -1, MODE_NONE);
    }

    @Override
    protected void initUi() {
        init();

        mGoodsCFragment = new GoodsCFragment();
        mGoodsCFragment.setListener(this);

        mShoppingCartFragment = new ShoppingCartFragment();
        mMeFragment = new MeFragment();

        mFragments.add(mGoodsCFragment);
        mFragments.add(mShoppingCartFragment);
        mFragments.add(mMeFragment);

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mTlMain.setTabData(mTabEntities, this, R.id.fl_change, mFragments);

        int cartCount = ShopCartUtil.querySkuNum();
        updateCartNum(cartCount);

        mTlMain.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 1) {
                    mShoppingCartFragment.getData();
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        registerBroadcastReceiver();
    }

    private void init() {
        // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
        JPushInterface.init(getApplicationContext());
        // 版本自动更新
        UpdateChecker.checkForDialog(this, RestDataSource.END_POINT + "appVersion");
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

    @Override
    public void updateCartNum(int num) {
        if (num > 0) {
            mTlMain.showMsg(1, num);
        } else {
            mTlMain.hideMsg(1);
        }
    }

    //定义广播
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_USER_UPDATE:
                    updateUser();
                    break;
                case ACTION_GOOD_UPDATE:
                    updateGoods();
                    break;
                case MESSAGE_RECEIVED_ACTION:
                    mGoodsCFragment.hasNewMessage();
                    break;
                case ADDRESS_EDIT_ACTION:

                    GoodsReceipt goodsReceipt = intent.getParcelableExtra("address");
                    if (goodsReceipt.isDefalut()) {
                        mGoodsCFragment.bindDefaultAddress(goodsReceipt);
                    }
                    break;
            }
        }
    };

    public void updateGoods() {
        updateCartNum(ShopCartUtil.querySkuNum());
        mGoodsCFragment.updateGoods();
    }

    //注册广播
    public void registerBroadcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_USER_UPDATE);
        myIntentFilter.addAction(ACTION_GOOD_UPDATE);
        myIntentFilter.addAction(MESSAGE_RECEIVED_ACTION);
        myIntentFilter.addAction(ADDRESS_EDIT_ACTION);
        // 注册广播
        registerReceiver( mBroadcastReceiver, myIntentFilter) ;
    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super .onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    /**
     * 更新用户信息
     */
    private void updateUser() {
        mGoodsCFragment.updateUser();
        mMeFragment.updateUser();
    }
}
