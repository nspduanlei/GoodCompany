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
import com.apec.android.domain.entities.user.ShopCart;
import com.apec.android.injector.components.DaggerGoodsListComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.injector.modules.GoodsListModule;
import com.apec.android.mvp.presenters.GoodsListPresenter;
import com.apec.android.mvp.views.GoodsListView;
import com.apec.android.util.SPUtils;
import com.apec.android.util.T;
import com.apec.android.views.activities.GoodDetailActivity;
import com.apec.android.views.activities.TrueOrderActivity;
import com.apec.android.views.adapter.GoodsListAdapter;
import com.apec.android.views.fragments.core.BaseFragment;
import com.apec.android.views.utils.LoginUtil;
import com.apec.android.views.utils.ShopCartUtil;
import com.apec.android.views.view.DividerItemDecoration;
import com.apec.android.views.view.RecyclerClickListener;
import com.apec.android.views.view.RecyclerInsetsDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

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

    @BindView(R.id.ll_goods_empty)
    View viewEmpty;

    @Inject
    GoodsListPresenter mGoodsListPresenter;

    GoodsListAdapter mGoodsListAdapter;
    ArrayList<Sku> mGoods = new ArrayList<>();

    //快速下单id
    int mOrderSkuId;
    //快速下单数量
    int mOrderCount;

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
        mCityId = (int) SPUtils.get(getActivity(), SPUtils.LOCATION_CITY_ID, 0);
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

        if (mCityId != 0) {
            mGoodsListPresenter.getGoods();
        }
    }

    private void initRecyclerView() {
        mRvGoods.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRvGoods.addItemDecoration(new DividerItemDecoration(getActivity()));

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
        if (goods.size() == 0) {
            viewEmpty.setVisibility(View.VISIBLE);
            return;
        } else {
            viewEmpty.setVisibility(View.GONE);
        }
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
    public void updateCountSuccess(int allCount) {
        GoodsCFragment.mFragmentListener.updateCartNum(allCount);
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
        mGoodsListPresenter.onStop();
        super.onStop();
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
    public void onUpdateCount(Sku sku, int position, int num) {
        int newCount = sku.getCount() + num;
        if (newCount > Constants.MAX_GOODS_COUNT) {
            sku.setCount(Constants.MAX_GOODS_COUNT);
            mGoods.set(position, sku);
            mGoodsListAdapter.notifyDataSetChanged();
            T.showShort(getActivity(), "商品数量不能超过" + Constants.MAX_GOODS_COUNT);
            return;
        }

        mGoodsListPresenter.updateCount(sku, num);

        sku.setCount(sku.getCount() + num);
        mGoods.set(position, sku);
        mGoodsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onOrderClick(int skuId, int count) {
        mOrderSkuId = skuId;
        mOrderCount = count;

//        //立即下单， 如果用户没有登录则去登录
//        if (LoginUtil.gotoLoginNew(getActivity())) {
//            doOrder();
//        }

        doOrder();
    }

    @Override
    public void onSaveCartClick(Sku sku, int position, int count) {
        SkuData skuData = new SkuData(sku);
        skuData.setCount(count);
        skuData.setSelect(true);
        ShopCartUtil.addData(skuData);

        GoodsCFragment.mFragmentListener.updateCartNum(ShopCartUtil.querySkuNum());
        sku.setCount(count);
        mGoods.set(position, sku);
        mGoodsListAdapter.notifyDataSetChanged();
    }

    //下单
    public void doOrder() {
        ArrayList<Integer> skus = new ArrayList<>();
        skus.add(mOrderSkuId);

        Intent intent = new Intent(getActivity(), TrueOrderActivity.class);
        intent.putIntegerArrayListExtra("sku_ids", skus);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        //mGoodsListAdapter.mHolder.stopTurning();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mGoodsListAdapter != null && mGoodsListAdapter.mHolder != null) {
//            mGoodsListAdapter.mHolder.startTurning();
//        }
    }
}
