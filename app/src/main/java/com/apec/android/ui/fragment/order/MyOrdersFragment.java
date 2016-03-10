package com.apec.android.ui.fragment.order;

import android.os.Bundle;
import android.view.View;

import com.apec.android.R;
import com.apec.android.domain.order.Order;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.ViewHolder;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.presenter.order.MyOrdersPresenter;
import com.apec.android.ui.presenter.user.RegisterPresenter;

import java.util.ArrayList;

/**
 *
 * Created by Administrator on 2016/2/26.
 */
public class MyOrdersFragment extends BaseListFragment<MyOrdersPresenter.IView,
        MyOrdersPresenter> implements MyOrdersPresenter.IView {

    public static MyOrdersFragment newInstance() {
        MyOrdersFragment fragment = new MyOrdersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my_orders;
    }

    @Override
    protected MyOrdersPresenter createPresenter() {
        return new MyOrdersPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private ArrayList<Order> datas;

    private void initView(View view) {

        datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(new Order());
        }

        setListAdapter(new CommonAdapter<Order>(getActivity(), datas, R.layout.my_order_item) {
            @Override
            public void convert(ViewHolder holder, Order order) {

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
