package com.apec.android.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.domain.entities.order.Order;
import com.apec.android.domain.entities.order.OrderList;
import com.apec.android.injector.components.DaggerOrderComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.MyOrdersPresenter;
import com.apec.android.mvp.views.MyOrdersView;
import com.apec.android.util.T;
import com.apec.android.views.activities.OrderDetailActivity;
import com.apec.android.views.activities.OrdersActivity;
import com.apec.android.views.activities.TransportsActivity;
import com.apec.android.views.adapter.OrderListAdapter;
import com.apec.android.views.fragments.core.BaseFragment;
import com.apec.android.views.utils.AlertDialog;
import com.apec.android.views.view.OrderListClickListener;
import com.apec.android.views.view.RecyclerInsetsDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    public static final String EXTRA_POSITION = "position";


    ArrayList<Order> mOrders = new ArrayList<>();

    int mStatus;
    int mPosition;
    @BindView(R.id.iv_empty)
    ImageView mIvEmpty;
    @BindView(R.id.tv_hint)
    TextView mTvHint;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;

    @Override
    protected void initUI(View view) {
        mStatus = getArguments().getInt(EXTRA_STATUS_ID, 0);
        mPosition = getArguments().getInt(EXTRA_POSITION, 0);

        mRvOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvOrder.addItemDecoration(new RecyclerInsetsDecoration(getActivity()));
        mOrderListAdapter = new OrderListAdapter(mOrders, getActivity(), this, mStatus);

        mRvOrder.setAdapter(mOrderListAdapter);

        mIvEmpty.setImageResource(R.drawable.order_empty);
        mTvHint.setText("亲！无相关订单哦");
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
    public static Fragment newInstance(int id, int position) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_STATUS_ID, id);
        args.putInt(EXTRA_POSITION, position);
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
        new AlertDialog(getActivity(), "确定取消订单", () -> mPresenter.cancelOrder(order.getId()))
                .showAlertDialog();
    }

    @Override
    public void onLookSend(Order order) {
        //TODO  查看物流
        Intent intent = new Intent(getActivity(), TransportsActivity.class);
        intent.putExtra("orderId", order.getId());
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
    public void bindOrders(OrderList orderList) {
        mOrders.clear();
        mOrders.addAll(orderList.getData());

        mOrderListAdapter.notifyDataSetChanged();

        ((OrdersActivity) getActivity()).setMsgCount(mOrders.size(), mPosition);
    }

    @Override
    public void cancelSuccess() {
        T.showShort(getActivity(), "取消订单成功");
        mPresenter.getOrderList(mStatus);
    }

    @Override
    public void showEmpty() {
        mLlEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        mLlEmpty.setVisibility(View.GONE);
    }
}
