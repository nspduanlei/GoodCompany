package com.apec.android.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.domain.entities.order.Order;
import com.apec.android.views.adapter.GoodsListAdapter;
import com.apec.android.views.adapter.OrderListAdapter;
import com.apec.android.views.fragments.core.BaseFragment;
import com.apec.android.views.view.OrderListClickListener;
import com.apec.android.views.view.RecyclerInsetsDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by duanlei on 2016/6/10.
 */
public class OrderFragment extends BaseFragment implements OrderListClickListener {


    @BindView(R.id.rv_order)
    RecyclerView mRvOrder;

    OrderListAdapter mOrderListAdapter;

    ArrayList<Order> mOrders = new ArrayList<>();

    @Override
    protected void initUI(View view) {
        for (int i = 0; i < 10; i++) {
            mOrders.add(new Order());
        }

        mRvOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvOrder.addItemDecoration(new RecyclerInsetsDecoration(getActivity()));
        mOrderListAdapter = new OrderListAdapter(mOrders, getActivity(), this);

        mRvOrder.setAdapter(mOrderListAdapter);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_order_new;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {

    }

    @Override
    protected void initPresenter() {

    }

    public static Fragment newInstance(int id) {
        return new OrderFragment();
    }


    @Override
    public void onElementClick(int position) {

    }

    @Override
    public void onGoodsItemClick(int goods) {

    }

    @Override
    public void onDetailClick(int orderId) {

    }
}
