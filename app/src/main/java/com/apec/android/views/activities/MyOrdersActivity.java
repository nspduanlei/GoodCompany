package com.apec.android.views.activities;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.domain.entities.order.Order;
import com.apec.android.domain.entities.order.OrderList;
import com.apec.android.injector.components.DaggerOrderComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.MyOrdersPresenter;
import com.apec.android.mvp.views.MyOrdersView;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.adapter.OrderListAdapter;
import com.apec.android.views.view.OrderListClickListener;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by duanlei on 2016/6/2.
 */
public class MyOrdersActivity extends BaseActivity implements MyOrdersView, OrderListClickListener {

    @Inject
    MyOrdersPresenter mPresenter;

    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.rc_list)
    RecyclerView mRcList;

    public final static String EXTRA_ORDER_ID = "order_id";

    OrderListAdapter mAdapter;
    ArrayList<Order> mData = new ArrayList<>();

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_orders, R.string.my_orders_title);
    }

    @Override
    protected void initUi() {
        mRcList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new OrderListAdapter(mData, this, this);
        mRcList.setAdapter(mAdapter);
        mRcList.addOnScrollListener(mOnScrollListener);
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        DaggerOrderComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(application.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onGetAllSuccess(OrderList orderList) {
        mData.clear();
        mData.addAll(orderList.getData());
        mAdapter.notifyDataSetChanged();
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
    public void onElementClick(int position) {

    }

    @Override
    public void onGoodsItemClick(int goods) {

    }

    @Override
    public void onDetailClick(int orderId) {
        //跳转到订单详情
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra(EXTRA_ORDER_ID, orderId);
        startActivityForResult(intent, Constants.REQUEST_CODE_ORDER_DETAIL);
    }


    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemsCount   = layoutManager.getChildCount();
            int totalItemsCount     = layoutManager.getItemCount();
            int firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition();

            if (visibleItemsCount + firstVisibleItemPos >= totalItemsCount) {
                mPresenter.onListEndReached();
            }
        }
    };
}
