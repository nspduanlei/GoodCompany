package com.apec.android.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.domain.entities.goods.Sku;
import com.apec.android.domain.entities.goods.SkuData;
import com.apec.android.injector.components.DaggerGoodsListComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.injector.modules.GoodsListModule;
import com.apec.android.mvp.presenters.GoodsListPresenter;
import com.apec.android.mvp.views.GoodsListView;
import com.apec.android.util.SPUtils;
import com.apec.android.views.activities.GoodDetailActivity;
import com.apec.android.views.adapter.GoodsListAdapter;
import com.apec.android.views.fragments.core.BaseFragment;
import com.apec.android.views.utils.LoginUtil;
import com.apec.android.views.utils.ShopCartUtil;
import com.apec.android.views.view.RecyclerClickListener;
import com.apec.android.views.view.RecyclerInsetsDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/2/26.
 */
public class GoodsFragment extends BaseFragment implements GoodsListView, RecyclerClickListener {

    public static final String EXTRA_CATEGORY_ID = "CATEGORY_ID";
    private int mCid, mCityId;

    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.rv_goods)
    RecyclerView mRvGoods;
    @BindView(R.id.view_error)
    View viewError;

    @Inject
    GoodsListPresenter mGoodsListPresenter;

    GoodsListAdapter mGoodsListAdapter;
    ArrayList<Sku> mGoods = new ArrayList<>();

    /**
     * 得到商品展示的fragment
     *
     * @param cId 类型id
     * @return
     */
    public static GoodsFragment newInstance(int cId) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_CATEGORY_ID, cId);
        GoodsFragment fragment = new GoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initUI(View view) {
        initRecyclerView();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_goods_test;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {
        mCid = getArguments().getInt(EXTRA_CATEGORY_ID, -1);
        mCityId = 100;
        DaggerGoodsListComponent.builder()
                .activityModule(new ActivityModule(getActivity()))
                .appComponent(myApplication.getAppComponent())
                .goodsListModule(new GoodsListModule(mCid, mCityId))
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mGoodsListPresenter.attachView(this);
        mGoodsListPresenter.onCreate();
    }


    private void initRecyclerView() {
        mRvGoods.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvGoods.addItemDecoration(new RecyclerInsetsDecoration(getActivity()));
        mGoodsListAdapter = new GoodsListAdapter(mGoods, getActivity(), this);

        mRvGoods.setAdapter(mGoodsListAdapter);
    }

    @Override
    public void showErrorView(String msg) {

    }

    @Override
    public void hideErrorView() {

    }

    @Override
    public void bindGoods(List<Sku> goods) {
        mGoods.clear();
        mGoods.addAll(goods);
        mGoodsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDetailScreen(int goodId) {
        //跳转到商品详情
        Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
        intent.putExtra(GoodDetailActivity.EXTRA_GOODS_ID, goodId);
        startActivity(intent);
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
    public void onStop() {
        super.onStop();
        mGoodsListPresenter.onStop();
    }

    public void updateData() {
        int cityId = (int) SPUtils.get(getActivity(), SPUtils.LOCATION_CITY_ID,
                Constants.DEFAULT_CITY_ID);
        mGoodsListPresenter.GetGoodsById(cityId);
    }


    @Override
    public void onElementClick(int position) {

    }

    @Override
    public void onAddCartClick(Sku sku, int position) {

        if (LoginUtil.isLogin(getActivity())) {
            //如果用户登录了，记录 skuId和对应num， 等用户点击购物车图标后批量加入购物车

        } else {
            //如果用户没有登录将信息加入本地购物车
            SkuData skuData = new SkuData(sku);
            skuData.saveThrows();

            GoodsCFragment.mFragmentListener.updateCartNum(ShopCartUtil.queryAllNum());
            mGoodsListAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onOrderClick(int skuId, int count) {

    }

    @Override
    public void onAddCount(Sku sku) {
        if (LoginUtil.isLogin(getActivity())) {
            //如果用户登录了，记录 skuId和对应num， 等用户点击购物车图标后批量加入购物车

        } else {
            //如果用户没有登录将信息加入本地购物车
            SkuData skuData = new SkuData(sku);
            skuData.saveThrows();

            GoodsCFragment.mFragmentListener.updateCartNum(ShopCartUtil.queryAllNum());


            //mGoodsListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCutCount(Sku sku) {

        SkuData skuData = new SkuData(sku);
        //如果减到0，从购物车中删除
        if (sku.getCount() == 0) {
            ShopCartUtil.deleteSku(skuData);
        }

    }
}
