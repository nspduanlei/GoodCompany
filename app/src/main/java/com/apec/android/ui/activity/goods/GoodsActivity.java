package com.apec.android.ui.activity.goods;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.config.Constants;
import com.apec.android.domain.entities.user.Area;
import com.apec.android.ui.activity.MVPBaseActivity;
import com.apec.android.ui.activity.order.MyOrdersActivity;
import com.apec.android.ui.activity.user.ManageAddrActivity;
import com.apec.android.ui.activity.user.MyAccountActivity;
import com.apec.android.ui.activity.user.RegisterFActivity;
import com.apec.android.ui.activity.user.ShoppingCartActivity;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.IconPageViewAdapter;
import com.apec.android.ui.adapter.MyViewHolder;
import com.apec.android.ui.presenter.goods.GoodsPresenter;
import com.apec.android.util.AppUtils;
import com.apec.android.util.SPUtils;
import com.apec.android.util.StringUtils;
import com.apec.android.util.T;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private Button loginOut, btnLogin;
    private TextView tvUserC, tvUserName;

    private int mCityId;

    FragmentPagerAdapter mAdapter;
    /**
     * 初始化ui
     */
    private void initViews() {
        initToolbar();

        //注册广播
        registerBroadcastReceiver();

        loading = (FrameLayout) findViewById(R.id.fl_loading);

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_goods);
        mAdapter = new IconPageViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        //菜单
        findViewById(R.id.tv_my_account).setOnClickListener(this);
        findViewById(R.id.tv_my_order).setOnClickListener(this);
        findViewById(R.id.tv_manage_address).setOnClickListener(this);
        findViewById(R.id.tv_my_shopping_cart).setOnClickListener(this);

        loginOut = (Button) findViewById(R.id.btn_login_out);
        btnLogin = (Button) findViewById(R.id.btn_login);

        tvUserC = (TextView) findViewById(R.id.tv_user_c);
        tvUserName = (TextView) findViewById(R.id.tv_user_name);

        updateUser();

        mCityId = (int)SPUtils.get(this, SPUtils.LOCATION_CITY_ID, 0);
        if (mCityId == 0) {
            mPresenter.startLocation();
        }
    }

    private void updateUser() {
        //验证是否登录
        if (StringUtils.isNullOrEmpty(
                (String) SPUtils.get(this, SPUtils.SESSION_ID, ""))) {
            loginOut();
        } else {
            login();
        }
    }

    private void login() {
        loginOut.setVisibility(View.VISIBLE);
        loginOut.setOnClickListener(this);
        btnLogin.setVisibility(View.GONE);
        tvUserC.setVisibility(View.VISIBLE);
        tvUserName.setText((String)SPUtils.get(this, SPUtils.USER_NAME, ""));
    }

    private void loginOut() {
        //没有登录
        loginOut.setVisibility(View.GONE);
        btnLogin.setVisibility(View.VISIBLE);
        btnLogin.setOnClickListener(this);
        tvUserC.setVisibility(View.GONE);
        tvUserName.setText("请登录");
    }

    private DrawerLayout drawerLayout;
    private TextView tvLocation;

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarTop);
        toolbar.findViewById(R.id.iv_shopping_cart).setOnClickListener(this);
        tvLocation = (TextView) toolbar.findViewById(R.id.tv_location);
        String cityName = (String) SPUtils.get(this, SPUtils.LOCATION_CITY_NAME,
                Constants.DEFAULT_CITY_NAME);
        tvLocation.setText(cityName);
        tvLocation.setOnClickListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.open_string, R.string.close_string);
        actionBarDrawerToggle.syncState();

        //drawerLayout.setDrawerListener(actionBarDrawerToggle);
        toolbar.setNavigationIcon(R.drawable.menu_icon);
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
            case R.id.btn_login_out:
                //退出登录确定
                showLoginOutDialog();
                break;
            case R.id.btn_login:
                Intent intent4 = new Intent(this, RegisterFActivity.class);
                startActivity(intent4);
                break;

            case R.id.tv_location:
                //updateGoods();
                //城市选择
                mPresenter.getOpenCityListArea();
                break;
        }
    }


    /**
     * 显示退出登录弹窗
     */
    private void showLoginOutDialog() {
        View dialogView = getLayoutInflater()
                .inflate(R.layout.dialog_cancel_order, null);

        TextView title = (TextView) dialogView.findViewById(R.id.tv_title);
        title.setText(String.format("    %s", "确定退出登录吗？"));
        ViewHolder viewHolder = new ViewHolder(dialogView);
        DialogPlus dialog = new DialogPlus.Builder(this)
                .setContentHolder(viewHolder)
                .setCancelable(true)
                .setGravity(DialogPlus.Gravity.CENTER)
                .setBackgroundColorResourceId(R.color.transparency)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()) {
                            case R.id.tv_cancel:
                                dialog.dismiss();
                                break;
                            case R.id.tv_sure:
                                dialog.dismiss();

                                //退出登录
                                SPUtils.clear(GoodsActivity.this);
                                SPUtils.put(GoodsActivity.this, SPUtils.IS_FIRST_LAUNCH, 1);
                                loginOut();
                                break;
                            default:
                                break;
                        }
                    }
                })
                .create();
        dialog.show();
    }

    List<Area> mCityData = new ArrayList<>();
    CommonAdapter mCityAdapter;
    int mSelectCityId;
    String mSelectCityName;
    /**
     * 显示城市选择弹窗
     */
    private void showSelectCityDialog() {
        View dialogView = getLayoutInflater()
                .inflate(R.layout.dialog_select_city, null);

        GridView gridView = (GridView) dialogView.findViewById(R.id.gv_open_city);

        mCityAdapter = new CommonAdapter<Area>(GoodsActivity.this,
                mCityData, R.layout.item_open_city) {
            @Override
            public void convert(final MyViewHolder holder, final Area city) {
                holder.setText(R.id.rb_city_name, city.getAreaName());
                if (city.isSelect()) {
                    holder.setSelected(R.id.rb_city_name, true);
                } else {
                    holder.setSelected(R.id.rb_city_name, false);
                }

                holder.setOnCheckChangeListerRadio(R.id.rb_city_name,
                        new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            for (Area area:mCityData) {
                                mSelectCityId = mCityData.get(holder.getMPosition()).getId();
                                mSelectCityName = mCityData.get(holder.getMPosition()).getAreaName();

                                if (area.getId() == mSelectCityId) {
                                    area.setSelect(true);
                                } else {
                                    area.setSelect(false);
                                }
                            }
                            mCityAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        };

        gridView.setAdapter(mCityAdapter);

        ViewHolder viewHolder = new ViewHolder(dialogView);
        DialogPlus dialog = new DialogPlus.Builder(this)
                .setContentHolder(viewHolder)
                .setCancelable(true)
                .setGravity(DialogPlus.Gravity.CENTER)
                .setBackgroundColorResourceId(R.color.transparency)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()) {
                            case R.id.tv_cancel:
                                dialog.dismiss();
                                break;

                            case R.id.tv_sure:
                                dialog.dismiss();

                                if (mCityId != mSelectCityId && mSelectCityName != null) {
                                    mCityId = mSelectCityId;
                                    SPUtils.put(GoodsActivity.this, SPUtils.LOCATION_CITY_ID,
                                            mSelectCityId);
                                    SPUtils.put(GoodsActivity.this, SPUtils.LOCATION_CITY_NAME,
                                            mSelectCityName);
                                    updateGoods();
                                }

                                break;
                            default:
                                break;
                        }
                    }
                })
                .create();
        dialog.show();
    }

    public static final String ACTION_USER_UPDATE = "用户修改";

    //定义广播
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_USER_UPDATE:
                    updateUser();
                    break;
            }
        }
    };

    private void updateGoods() {
        Log.e("test001", "cityId---->" + SPUtils.get(this, SPUtils.LOCATION_CITY_ID, 0));
        mAdapter.notifyDataSetChanged();
        String cityName = (String) SPUtils.get(this, SPUtils.LOCATION_CITY_NAME,
                Constants.DEFAULT_CITY_NAME);
        tvLocation.setText(cityName);
    }

    //注册广播
    public void registerBroadcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_USER_UPDATE);
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    public void locationSuccess(int cityId, String cityName) {
        //城市id获取成功
        if (cityId != 0) {
            mCityId = cityId;
            SPUtils.put(this, SPUtils.LOCATION_CITY_ID, cityId);
            SPUtils.put(this, SPUtils.LOCATION_CITY_NAME, cityName);
            if (cityId != Constants.DEFAULT_CITY_ID) {
                updateGoods();
            }
        } else {
            mCityId = Constants.DEFAULT_CITY_ID;
            SPUtils.put(this, SPUtils.LOCATION_CITY_ID, Constants.DEFAULT_CITY_ID);
            SPUtils.put(this, SPUtils.LOCATION_CITY_NAME, Constants.DEFAULT_CITY_NAME);
        }
    }

    @Override
    public void getCitySuccess(List<Area> areas) {
        mCityData.clear();
        mCityData.addAll(areas);
        for (Area area:mCityData) {
            if (area.getId() == mCityId) {
                area.setSelect(true);
            }

        }
        showSelectCityDialog();
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
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            exit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}