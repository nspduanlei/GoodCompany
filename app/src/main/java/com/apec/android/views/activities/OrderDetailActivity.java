package com.apec.android.views.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.databinding.ActivityOrderDetailBinding;
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
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/6/2.
 */
public class OrderDetailActivity extends BaseActivity implements OrderDetailView {
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.tv_cancel_order)
    TextView mTvCancelOrder;

    @BindView(R.id.tv_state_text_1)
    TextView mTvStateText1;
    @BindView(R.id.tv_state_text_2)
    TextView mTvStateText2;
    @BindView(R.id.tv_state_text_3)
    TextView mTvStateText3;
    @BindView(R.id.fl_state_text)
    FrameLayout mFlStateText;
    @BindView(R.id.view_circle_1)
    View mViewCircle1;
    @BindView(R.id.view_line_1)
    View mViewLine1;
    @BindView(R.id.view_circle_2)
    View mViewCircle2;
    @BindView(R.id.view_line_2)
    View mViewLine2;
    @BindView(R.id.view_circle_3)
    View mViewCircle3;
    @BindView(R.id.ll_state)
    LinearLayout mLlState;
    @BindView(R.id.lv_sku)
    NoScrollListView mLvSku;
    @BindView(R.id.tv_text_pay)
    TextView mTvTextPay;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;

    ActivityOrderDetailBinding mBinding;
    @Inject
    OrderDetailPresenter mPresenter;

    int mOrderId;
    @BindView(R.id.btn_cancel_order)
    Button mBtnCancelOrder;

    @Override
    protected void setUpContentView() {
        mOrderId = getIntent().getIntExtra(MyOrdersActivity.EXTRA_ORDER_ID, 0);
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
        mBinding.setOrder(order);

        //商品列表
        mLvSku.setAdapter(new CommonAdapter<OrderItem>(this, order.getOrderItems(),
                R.layout.goods_item_order) {
            @Override
            public void convert(MyViewHolder holder, OrderItem orderItem) {
                if (orderItem.getSku().getPics().size() > 0) {
                    holder.setImageUrl(R.id.iv_goods_pic,
                            orderItem.getSku().getPics().get(0).getUrl());
                }
                holder.setText(R.id.tv_goods_name, orderItem.getSku().getSkuName())
                        .setText(R.id.tv_goods_price,
                                String.format(getString(R.string.price_and_num),
                                        orderItem.getSku().getPrice(), orderItem.getNum()));
            }
        });

        //订单状态
        switch (order.getOrderType()) {
            case 1: //待处理
                break;
            case 2: //处理中
                mViewCircle2.setBackgroundResource(R.drawable.circle_order_state);
                mViewLine1.setBackgroundResource(R.color.color_5);

                mTvStateText2.setTextColor(getResources().getColor(R.color.color_5));
                break;
            case 3: //已完成
                mViewCircle1.setBackgroundResource(R.drawable.circle_order_state);
                mViewLine1.setBackgroundResource(R.color.color_5);
                mViewCircle3.setBackgroundResource(R.drawable.circle_order_state);
                mViewLine2.setBackgroundResource(R.color.color_5);

                mTvStateText3.setTextColor(getResources().getColor(R.color.color_5));
                mTvStateText2.setTextColor(getResources().getColor(R.color.color_5));

                mBtnCancelOrder.setVisibility(View.GONE);
                break;
            case 4: //订单取消
                cancelSuccess();
                break;
        }

    }

    @Override
    public void cancelSuccess() {
        //取消订单成功
        mTvCancelOrder.setVisibility(View.VISIBLE);
        mFlStateText.setVisibility(View.GONE);
        mLlState.setVisibility(View.GONE);
        mBtnCancelOrder.setVisibility(View.GONE);
        setResult(Constants.RESULT_CODE_CANCEL_ORDER);
    }

    @Override
    public void showLoadingView() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mPbLoading.setVisibility(View.GONE);
    }


    @OnClick(R.id.btn_cancel_order)
    void onCancelClicked(View view) {
        //取消订单
        mPresenter.cancelOrder(mOrderId);
    }

}
