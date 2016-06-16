package com.apec.android.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.domain.entities.goods.SkuData;
import com.apec.android.domain.entities.user.ShopCartData;
import com.apec.android.injector.components.DaggerShopCartComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.ShoppingCartPresenter;
import com.apec.android.mvp.views.ShoppingCartView;
import com.apec.android.views.activities.MainActivity;
import com.apec.android.views.adapter.CartListAdapter;
import com.apec.android.views.fragments.core.BaseFragment;
import com.apec.android.views.utils.LoginUtil;
import com.apec.android.views.utils.ShopCartUtil;
import com.apec.android.views.view.CartListClickListener;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;

    CartListAdapter mAdapter;
    ArrayList<SkuData> mData = new ArrayList<>();

    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;

    @Override
    protected void initUI(View view) {
        mRvCart.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CartListAdapter(mData, getActivity(), this);
        mRvCart.setAdapter(mAdapter);
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

        if (LoginUtil.isLogin(getActivity())) {
            //TODO 如果用户登录了 将本地没有加入购物车的商品，加入购物车

            //TODO 登录成功将远程 购物车的数据加入到本地

            //TODO 退出登录时 清空购物车数据 用户数据 和 session_id
        }
    }

    @Override
    public void showLoadingView() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public void onDataEmpty() {
        mLlEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        mLlEmpty.setVisibility(View.GONE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //LoginUtil.onActivityResult(requestCode, resultCode, getActivity());
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
    public void onAddClick(String skuId) {
        //购物车数量加1
        ShopCartUtil.updateCount(skuId, 1);
        GoodsCFragment.mFragmentListener.updateCartNum(ShopCartUtil.querySkuNum());
        mPresenter.getData();
    }

    @Override
    public void onCutClick(String skuId) {
        //购物车数量减1
        ShopCartUtil.updateCount(skuId, -1);
        GoodsCFragment.mFragmentListener.updateCartNum(ShopCartUtil.querySkuNum());
        mPresenter.getData();
    }

    @Override
    public void onCheckChange(String skuId, boolean isCheck) {
        if (isCheck) {
            ShopCartUtil.updateCheck(skuId, true);
        }
    }

    @Override
    public void bindCart(ShopCartData shopCart) {
        mData.clear();
        mData.addAll(shopCart.getSkus());
        mAdapter.notifyDataSetChanged();
    }

    public void getData() {
        mPresenter.getData();
    }
}
