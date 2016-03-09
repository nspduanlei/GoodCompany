package com.apec.android.ui.fragment.user;

import android.os.Bundle;
import android.view.View;

import com.apec.android.R;
import com.apec.android.domain.goods.Sku;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.ViewHolder;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.presenter.user.RegisterPresenter;
import com.apec.android.ui.presenter.user.ShoppingCartPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Administrator on 2016/2/26.
 */
public class ShoppingCartFragment extends BaseListFragment<ShoppingCartPresenter.IView,
        ShoppingCartPresenter> implements ShoppingCartPresenter.IView {

    public static ShoppingCartFragment newInstance() {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    protected ShoppingCartPresenter createPresenter() {
        return new ShoppingCartPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    List<Sku> datas;

    private void initView(View view) {

        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(new Sku());
        }

        setListAdapter(new CommonAdapter<Sku>(getActivity(), datas, R.layout.shopping_cart_item) {
            @Override
            public void convert(ViewHolder holder, Sku s) {

            }
        });
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
