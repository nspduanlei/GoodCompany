package com.apec.android.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.domain.entities.order.Order;
import com.apec.android.domain.entities.order.OrderList;
import com.apec.android.injector.components.DaggerOrderComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.MyOrdersPresenter;
import com.apec.android.mvp.views.MyOrdersView;
import com.apec.android.util.DialogUtils;
import com.apec.android.util.T;
import com.apec.android.views.activities.OrderDetailActivity;
import com.apec.android.views.activities.OrdersActivity;
import com.apec.android.views.adapter.OrderListAdapter;
import com.apec.android.views.fragments.core.BaseFragment;
import com.apec.android.views.utils.AlertDialog;
import com.apec.android.views.view.OrderListClickListener;
import com.apec.android.views.view.RecyclerInsetsDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by duanlei on 2016/6/10.
 */
public class OrderFragment extends BaseFragment implements OrderListClickListener, MyOrdersView {

    @BindView(R.id.rv_order)
    RecyclerView mRvOrder;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;

    @Inject
    MyOrdersPresenter mPresenter;

    OrderListAdapter mOrderListAdapter;

    public static final String EXTRA_STATUS_ID = "status_id";

    ArrayList<Order> mOrders = new ArrayList<>();

    int mStatus;

    @Override
    protected void initUI(View view) {
        mStatus = getArguments().getInt(EXTRA_STATUS_ID, 0);

        mRvOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvOrder.addItemDecoration(new RecyclerInsetsDecoration(getActivity()));
        mOrderListAdapter = new OrderListAdapter(mOrders, getActivity(), this, mStatus);

        mRvOrder.setAdapter(mOrderListAdapter);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_order_new;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {
        DaggerOrderComponent.builder()
                .activityModule(new ActivityModule(getActivity()))
                .appComponent(myApplication.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {

        if (mStatus == 0) {
            return;
        }

        mPresenter.attachView(this);
        mPresenter.onCreate();
        mPresenter.getOrderList(mStatus);
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    /**
     * @param id 订单状态id
     * @return
     */
    public static Fragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_STATUS_ID, id);
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onElementClick(int position) {
        //跳转到订单详情
        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        intent.putExtra(OrderDetailActivity.EXTRA_ORDER_ID, mOrders.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onGoodsItemClick(int goods) {

    }

    @Override
    public void onDetailClick(int orderId) {

    }

    @Override
    public void onCancelOrder(Order order) {
        //TODO  确定取消订单
        new AlertDialog(getActivity(), "确定取消订单", () -> mPresenter.cancelOrder(order.getId()));
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
    public void bindOrders(OrderList orderList) {
        mOrders.clear();
        mOrders.addAll(orderList.getData());
        mOrderListAdapter.notifyDataSetChanged();

        ((OrdersActivity)getActivity()).setMsgCount(mOrders.size(), mStatus - 1);
    }

    @Override
    public void cancelSuccess() {
        //TODO 取消订单
        T.showShort(getActivity(), "取消订单成功");
        mPresenter.getOrderList(mStatus);
    }
}
