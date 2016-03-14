package com.apec.android.ui.fragment.order;

import android.os.Bundle;
import android.view.View;

import com.apec.android.R;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.order.OrderPresenter;
import com.apec.android.ui.presenter.user.RegisterPresenter;

/**
 *
 * Created by Administrator on 2016/2/26.
 */
public class OrderFragment extends BaseFragment<OrderPresenter.IView,
        OrderPresenter> implements OrderPresenter.IView {

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_order;
    }

    @Override
    protected OrderPresenter createPresenter() {
        return new OrderPresenter(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initView(view);
    }

    private void initView(View view) {
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
