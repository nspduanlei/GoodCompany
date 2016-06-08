package com.apec.android.views.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.domain.entities.user.ShopCart;
import com.apec.android.domain.entities.user.Skus;
import com.apec.android.injector.components.DaggerShopCartComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.ShoppingCartPresenter;
import com.apec.android.mvp.views.ShoppingCartView;
import com.apec.android.views.adapter.CartListAdapter;
import com.apec.android.views.fragments.core.BaseFragment;
import com.apec.android.views.utils.LoginUtil;
import com.apec.android.views.view.CartListClickListener;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by duanlei on 2016/6/7.
 */
public class ShoppingCartFragment extends BaseFragment implements ShoppingCartView,
        CartListClickListener {

    @Inject
    ShoppingCartPresenter mPresenter;

    @BindView(R.id.cb_select_all)
    CheckBox mCbSelectAll;
    @BindView(R.id.btn_goto_pay)
    Button mBtnGotoPay;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.rv_cart)
    RecyclerView mRvCart;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    CartListAdapter mAdapter;
    ArrayList<Skus> mData = new ArrayList<>();

    @Override
    protected void initUI(View view) {
        //是否登录
        //LoginUtil.gotoLogin(getActivity());

        mRvCart.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CartListAdapter(mData, getActivity(), this);
        mRvCart.setAdapter(mAdapter);

        mSrlRefresh.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        mSrlRefresh.setEnabled(false);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {
        DaggerShopCartComponent.builder()
                .activityModule(new ActivityModule(getActivity()))
                .appComponent(myApplication.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LoginUtil.onActivityResult(requestCode, resultCode, getActivity());
        if (requestCode == Constants.REQUEST_CODE_LOGIN) {
            if (resultCode == Constants.RESULT_CODE_LOGIN_SUCCESS) {
                mPresenter.getData();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onElementClick(int position) {

    }

    @Override
    public void bindCart(ShopCart shopCart) {
        mData.addAll(shopCart.getSkus());
        mAdapter.notifyDataSetChanged();
    }
}
