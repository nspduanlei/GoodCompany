package com.apec.android.ui.fragment.goods;

import android.os.Bundle;
import android.view.View;

import com.apec.android.R;
import com.apec.android.domain.goods.Good;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.goods.GoodsDetailPresenter;

/**
 * 商品详情
 * Created by duanlei on 2016/3/2.
 */
public class GoodsDetailFragment extends BaseFragment<GoodsDetailPresenter.IView,
        GoodsDetailPresenter> implements GoodsDetailPresenter.IView {

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_goods_detail;
    }

    @Override
    protected GoodsDetailPresenter createPresenter() {
        return new GoodsDetailPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView() {

    }

    @Override
    public void showGoods(Good goods) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmptyCase() {

    }

    @Override
    public boolean isReady() {
        return isAdded();
    }
}
