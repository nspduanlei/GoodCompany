package com.apec.android.views.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.domain.entities.transport.GoodsReceipt;
import com.apec.android.domain.entities.user.OpenCity;
import com.apec.android.injector.components.DaggerGoodsComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.GoodsPresenter;
import com.apec.android.mvp.views.GoodsView;
import com.apec.android.util.SPUtils;
import com.apec.android.util.StringUtils;
import com.apec.android.views.activities.ManageAddressActivity;
import com.apec.android.views.adapter.GoodsCAdapter;
import com.apec.android.views.fragments.core.BaseFragment;
import com.apec.android.views.utils.CityUtil;
import com.apec.android.views.utils.LoginUtil;
import com.apec.android.views.view.CityDialog;
import com.apec.android.views.view.FragmentListener;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.tabs)
    SlidingTabLayout mTabs;

    @Inject
    GoodsPresenter mGoodsPresenter;

    List<OpenCity> mCityData = new ArrayList<>();
    int mCityId;

    //ArrayList<OpenCity> mOpenCities;

    protected static final int[] IDS = new int[]{
            12, 13, 11, 15, 46
    };

    protected static final String[] CONTENT = new String[]{
            "糖品", "米品", "油品", "面品", "调味品"
    };

    boolean hasDefault = false;

    public static GoodsReceipt mGoodsReceipt;

    //登录成功，继续执行下面的操作
    public boolean mIsOrderLoginSuccess;

    public static FragmentListener mFragmentListener;

    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.tv_send_address)
    TextView mTvSendAddress;

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
        if (mCityId == 0) {
            mGoodsPresenter.startLocation();
        }
        //mGoodsPresenter.initPresenter(mCityId);

        mGoodsPresenter.attachView(this);
        mGoodsPresenter.onCreate();
        updateUser();
        initLocation();
    }

    private void initLocation() {
        //开放城市显示
        String cityName = (String) SPUtils.get(getActivity(), SPUtils.LOCATION_CITY_NAME, "");
        if (!StringUtils.isNullOrEmpty(cityName)) {
            mTvLocation.setText(cityName);
        }
    }

    @OnClick(R.id.tv_location)
    void onSelectLocation(View view) {
        if(CityUtil.queryAll().size() > 0) {
            bindCity(CityUtil.queryAll());
        }
    }

    @Override
    public void showErrorView(String s) {

    }

    @Override
    public void hideErrorView(String s) {

    }

    @Override
    public void bindDefaultAddress(GoodsReceipt goodsReceipt) {
        mGoodsReceipt = goodsReceipt;
        mTvSendAddress.setText(String.format(getString(R.string.send_address),
                goodsReceipt.getAddrRes().getDetail()));
        //显示默认地址
        mTvSendAddress.setVisibility(View.VISIBLE);

        if (mIsOrderLoginSuccess) {
            GoodsFragment fragment =
                    (GoodsFragment)mAdapter.mFragments.get(mVpGoods.getCurrentItem());
            fragment.doOrder();
        }
    }

    public void bindCity(List<OpenCity> areas) {
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
        mTvLocation.setText("正在定位");
    }

    @Override
    public void locationSuccess(int cityId, String cityName) {
        mCityId = cityId;
        mTvLocation.setText(cityName);
    }

    /**
     * 选择了城市
     */
    private void updateGoods() {
        mAdapter.notifyDataSetChanged();
        String cityName = (String) SPUtils.get(getActivity(), SPUtils.LOCATION_CITY_NAME,
                Constants.DEFAULT_CITY_NAME);

        mTvLocation.setText(cityName);
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    /**
     * 设置 viewPager 内容
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        mAdapter = new GoodsCAdapter(getChildFragmentManager());
        mAdapter.addFragment(new RecommendFragment(), "推荐");
        for (int i = 0; i < CONTENT.length; i++) {
            mAdapter.addFragment(GoodsFragment.newInstance(IDS[i]), CONTENT[i]);
        }
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(5);
    }

    @OnClick(R.id.tv_send_address)
    void onAddressClicked(View view) {
        //TODO test
        hasDefault = true;
        Intent intent = new Intent(getActivity(), ManageAddressActivity.class);
        intent.putExtra(ManageAddressActivity.HAS_DEFAULT, hasDefault);
        intent.putExtra(ManageAddressActivity.IS_SELECT, true);
        startActivityForResult(intent, Constants.REQUEST_CODE_ADDR);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE_ADDR) {
            if (resultCode == Constants.RESULT_CODE_SELECT_ADDRESS) {
                GoodsReceipt goodsReceipt = data.getParcelableExtra("data");

                mGoodsReceipt = goodsReceipt;
                mTvSendAddress.setText(String.format(getString(R.string.send_address),
                        mGoodsReceipt.getAddrRes().getDetail()));
            }
        } else if (requestCode == Constants.REQUEST_CODE_LOGIN_ORDER) {
            if (resultCode == Constants.RESULT_CODE_LOGIN_SUCCESS) {

                mIsOrderLoginSuccess = true;
                updateUser();
            }
        }
    }

    public void setListener(FragmentListener fragmentListener) {
        mFragmentListener = fragmentListener;
    }

    public void updateUser() {
        if (LoginUtil.isLogin(getActivity())) {
            mGoodsPresenter.getDefaultAddress();
        } else {
            mTvSendAddress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoodsReceipt = null;
    }
}
