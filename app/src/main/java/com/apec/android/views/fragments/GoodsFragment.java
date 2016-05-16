package com.apec.android.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.domain.entities.goods.Good;
import com.apec.android.injector.components.DaggerGoodsListComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.injector.modules.GoodsListModule;
import com.apec.android.mvp.presenters.GoodsListPresenter;
import com.apec.android.mvp.views.GoodsListView;
import com.apec.android.ui.activity.goods.GoodsDetailActivity;
import com.apec.android.util.SPUtils;
import com.apec.android.views.activities.GoodDetailActivity;
import com.apec.android.views.adapter.GoodsListAdapter;
import com.apec.android.views.fragments.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/26.
 */
public class GoodsFragment extends BaseFragment implements GoodsListView {

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
    List<Good> mGoods = new ArrayList<>();

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
    protected int getFragmentLayout() {
        return R.layout.fragment_goods_test;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initUi(view);
        initRecyclerView();

        initDependencyInjector();
        initPresenter();
    }

    private void initRecyclerView() {
        mRvGoods.setLayoutManager(new LinearLayoutManager(getActivity()));
        mGoodsListAdapter = new GoodsListAdapter(mGoods, getActivity(),
                position -> mGoodsListPresenter.onElementClick(position));

        mRvGoods.setAdapter(mGoodsListAdapter);
    }

    private void initPresenter() {
        mGoodsListPresenter.attachView(this);
        mGoodsListPresenter.onCreate();
    }

    private void initDependencyInjector() {
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        mCid = getArguments().getInt(EXTRA_CATEGORY_ID, -1);
        mCityId = 100;

        DaggerGoodsListComponent.builder()
                .activityModule(new ActivityModule(getActivity()))
                .appComponent(myApplication.getAppComponent())
                .goodsListModule(new GoodsListModule(mCid, mCityId))
                .build().inject(this);
    }

    private void initUi(View view) {
        ButterKnife.bind(this, view);
    }


    @Override
    public void showErrorView(String msg) {

    }

    @Override
    public void hideErrorView() {

    }

    @Override
    public void bindGoods(List<Good> goods) {
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
}
