package com.apec.android.views.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.domain.entities.transport.GoodsReceipt;
import com.apec.android.domain.entities.user.Area;
import com.apec.android.domain.entities.user.OpenCity;
import com.apec.android.injector.components.DaggerGoodsComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.GoodsPresenter;
import com.apec.android.mvp.views.GoodsView;
import com.apec.android.util.AppUtils;
import com.apec.android.util.SPUtils;
import com.apec.android.util.StringUtils;
import com.apec.android.util.T;
import com.apec.android.views.activities.MainActivity;
import com.apec.android.views.activities.ManageAddressActivity;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.adapter.GoodsCAdapter;
import com.apec.android.views.fragments.core.BaseFragment;
import com.apec.android.views.utils.LoginUtil;
import com.apec.android.views.view.CityDialog;
import com.apec.android.views.view.FragmentListener;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/5/9.
 */
public class GoodsCFragment extends BaseFragment implements GoodsView {

    GoodsCAdapter mAdapter;

    @BindView(R.id.vp_goods)
    ViewPager mVpGoods;

//    @BindView(R.id.tv_location)
//    TextView mTvLocation;

    @Inject
    GoodsPresenter mGoodsPresenter;

    LoginUtil mLoginUtil;

    List<Area> mCityData = new ArrayList<>();
    int mCityId;


    @BindView(R.id.tabs)
    SlidingTabLayout mTabs;

    ArrayList<OpenCity> mOpenCities;

    protected static final int[] IDS = new int[]{
            12, 13, 11, 15, 46
    };

    protected static final String[] CONTENT = new String[]{
            "糖品", "米品", "油品", "面品", "调味品"
    };

    boolean hasDefault = false;

    int mAddressId;

    public static FragmentListener mFragmentListener;

    @Override
    protected void initUI(View view) {
        setupViewPager(mVpGoods);
        mTabs.setViewPager(mVpGoods);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_goods_new;
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        DaggerGoodsComponent.builder()
                .activityModule(new ActivityModule(getActivity()))
                .appComponent(application.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mCityId = (int) SPUtils.get(getActivity(), SPUtils.LOCATION_CITY_ID, 0);
//        if (mCityId == 0) {
//            mGoodsPresenter.startLocation();
//        }
        mGoodsPresenter.initPresenter(mCityId);
        mGoodsPresenter.attachView(this);
        mGoodsPresenter.onCreate();
    }

    private void initUser() {
        //mLoginUtil = new LoginUtil(this, mMenu);

        mLoginUtil.updateUser();
        //注册广播, 用于监听用户信息的变化
        registerBroadcastReceiver();
    }


    private void initLocation() {
        //开放城市显示
        String cityName = (String) SPUtils.get(getActivity(), SPUtils.LOCATION_CITY_NAME,
                Constants.DEFAULT_CITY_NAME);
        if (!StringUtils.isNullOrEmpty(cityName)) {
            //mTvLocation.setText(cityName);
        }
    }

//    /**
//     * 设置抽屉布局
//     *
//     * @param navigationView
//     */
//    private void setupDrawerContent(NavigationView navigationView) {
//        navigationView.setNavigationItemSelectedListener(
//                item -> {
//                    item.setChecked(true);
//                    mDrawer.closeDrawers();
//                    return true;
//                }
//        );
//    }

//    @OnClick(R.id.tv_location)
//    void onSelectLocation(View view) {
//        //TODO 选择开放城市
//        mGoodsPresenter.getOpenCityList();
//    }

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

        new CityDialog(getActivity(), mCityData, (selectCityId, selectCityName) -> {
            if (selectCityName != null || mCityId != selectCityId) {
                mCityId = selectCityId;
                SPUtils.put(getActivity(), SPUtils.LOCATION_CITY_ID,
                        selectCityId);
                SPUtils.put(getActivity(), SPUtils.LOCATION_CITY_NAME,
                        selectCityName);
                updateGoods();
            }
        }).showSelectCityDialog();
    }

    @Override
    public void startLocation() {
        //mTvLocation.setText("正在定位");
    }

    @Override
    public void locationSuccess(ArrayList<OpenCity> openCities, String cityName) {
        mOpenCities = openCities;
        //mTvLocation.setText(cityName);
    }

    private void updateGoods() {
        mAdapter.notifyDataSetChanged();
        String cityName = (String) SPUtils.get(getActivity(), SPUtils.LOCATION_CITY_NAME,
                Constants.DEFAULT_CITY_NAME);
        //mTvLocation.setText(cityName);
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
        //registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    private boolean isExit;

    //快速点击两下返回退出app
//    private void exit() {
//        if (!isExit) {
//            isExit = true;
//            T.showShort(this, "再按一次退出" + AppUtils.getAppName(this));
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    isExit = false;
//                }
//            }, 2000);
//        } else {
//            finish();
//        }
//    }

//    @Override
//    public void onBackPressed() {
//        exit();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //unregisterReceiver(mBroadcastReceiver);
//    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                mDrawer.openDrawer(GravityCompat.START);
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * 设置 viewPager 内容
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        GoodsCAdapter adapter =
                new GoodsCAdapter(getChildFragmentManager());
        adapter.addFragment(new RecommendFragment(), "推荐");
        for (int i = 0; i < CONTENT.length; i++) {
            adapter.addFragment(GoodsFragment.newInstance(IDS[i]), CONTENT[i]);
        }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
    }

//    @OnClick(R.id.tv_top_title)
//    void onAddressClicked(View view) {
//        //TODO test
//        hasDefault = true;
//
//        Intent intent = new Intent(getActivity(), ManageAddressActivity.class);
//        intent.putExtra(ManageAddressActivity.HAS_DEFAULT, hasDefault);
//        intent.putExtra(ManageAddressActivity.IS_SELECT, true);
//        startActivityForResult(intent, Constants.REQUEST_CODE_ADDR);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == Constants.REQUEST_CODE_ADDR) {
//            if (resultCode == Constants.RESULT_CODE_SELECT_ADDRESS) {
//
//                GoodsReceipt goodsReceipt = data.getParcelableExtra("data");
//                mAddressId = goodsReceipt.getAddressId();
//
//                mTvTopTitle.setText(goodsReceipt.getAddrRes().getDetail());
//            }
//        }
//    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE_ADDR) {
            if (resultCode == Constants.RESULT_CODE_SELECT_ADDRESS) {

                GoodsReceipt goodsReceipt = data.getParcelableExtra("data");
                mAddressId = goodsReceipt.getAddressId();

                //mTvTopTitle.setText(goodsReceipt.getAddrRes().getDetail());
            }
        }
    }

    public void setListener(FragmentListener fragmentListener) {
        mFragmentListener = fragmentListener;
    }
}
