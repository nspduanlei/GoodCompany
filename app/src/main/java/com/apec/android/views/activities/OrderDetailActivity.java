package com.apec.android.views.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.databinding.ActivityOrderDetailBinding;
import com.apec.android.domain.entities.goods.SkuData;
import com.apec.android.domain.entities.order.Order;
import com.apec.android.domain.entities.order.OrderItem;
import com.apec.android.injector.components.DaggerOrderComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.OrderDetailPresenter;
import com.apec.android.mvp.views.OrderDetailView;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.adapter.listView.CommonAdapter;
import com.apec.android.views.adapter.listView.MyViewHolder;
import com.apec.android.views.widget.NoScrollListView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by duanlei on 2016/6/2.
 */
public class OrderDetailActivity extends BaseActivity implements OrderDetailView {

    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.lv_sku)
    NoScrollListView mLvSku;

    ActivityOrderDetailBinding mBinding;
    @Inject
    OrderDetailPresenter mPresenter;

    int mOrderId;

    public final static String EXTRA_ORDER_ID = "order_id";
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;

    @Override
    protected void setUpContentView() {
        mOrderId = getIntent().getIntExtra(EXTRA_ORDER_ID, 0);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);
        ButterKnife.bind(this);
        setUpToolbar(R.string.order_detail_title, -1, MODE_BACK);
    }

    @Override
    protected void initUi() {

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
        mPresenter.mOrderId = mOrderId;
        mPresenter.onCreate();
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void bindOrder(Order order) {

        mLlContent.setVisibility(View.VISIBLE);

        mBinding.setOrder(order);

        mLvSku.setAdapter(new CommonAdapter<OrderItem>(this, order.getOrderItems(),
                R.layout.order_goods_item_new) {
            @Override
            public void convert(MyViewHolder holder, OrderItem orderItem) {

                SkuData skuData = new SkuData(orderItem.getSku());
                skuData.setCount(orderItem.getNum());

                holder.setText(R.id.tv_goods_name, skuData.getSkuName())
                        .setText(R.id.tv_price,
                                String.format(getString(R.string.true_order_2),
                                        skuData.getPrice()))
                        .setText(R.id.tv_good_num,
                                String.format(getString(R.string.true_order_6),
                                        skuData.getCount()));

                if (skuData.getAttrValue() != null) {
                    holder.setText(R.id.tv_weight,
                            String.format(getString(R.string.true_order_3), skuData.getAttrName()));
                }

            }
        });

        //订单状态
        switch (order.getOrderType()) {
            case 1: //待处理
                mTvStatus.setText("待处理");
                break;
            case 2: //处理中
                mTvStatus.setText("处理中");
                break;
            case 3: //已完成
                mTvStatus.setText("已完成");

                break;
            case 4: //订单取消
                mTvStatus.setText("订单取消");

                break;
        }

    }

    @Override
    public void cancelSuccess() {
        //取消订单成功
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


//    @OnClick(R.id.btn_cancel_order)
//    void onCancelClicked(View view) {
//        //取消订单
//        mPresenter.cancelOrder(mOrderId);
//    }

}
