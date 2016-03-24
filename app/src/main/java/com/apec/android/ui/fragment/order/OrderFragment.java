package com.apec.android.ui.fragment.order;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.domain.order.Order;
import com.apec.android.ui.activity.order.OrderActivity;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.order.OrderPresenter;

/**
 * 订单详情
 * Created by Administrator on 2016/2/26.
 */
public class OrderFragment extends BaseFragment<OrderPresenter.IView,
        OrderPresenter> implements OrderPresenter.IView, View.OnClickListener {

    public static OrderFragment newInstance(int orderId) {
        OrderFragment fragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(OrderActivity.EXTRA_ORDER_ID, orderId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mOrderId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderId = getArguments().getInt(OrderActivity.EXTRA_ORDER_ID);
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
        mPresenter.getOrder(mOrderId);
    }


    //订单号
    private TextView tvOrderNum;

    //取消订单 btn_cancel_order
    private Button btnCancelOrder;

    //收货人 tv_user_name
    private TextView tvUserName;

    //手机号码tv_phone_number
    private TextView tvPhoneNumber;

    //地址
    private TextView tvAddress;

    private void initView(View view) {

        //标题
        TextView title = (TextView) view.findViewById(R.id.tv_top_title);
        title.setText("订单详情");
        view.findViewById(R.id.iv_back).setOnClickListener(this);

        //订单信息
        tvOrderNum = (TextView) view.findViewById(R.id.tv_order_num);
        btnCancelOrder = (Button) view.findViewById(R.id.btn_cancel_order);
        tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        tvPhoneNumber = (TextView) view.findViewById(R.id.tv_phone_number);
        tvAddress = (TextView) view.findViewById(R.id.tv_address);
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
    public void getOrderSuccess(Order order) {
        //订单数据获取成功， 填充数据
        tvOrderNum.setText(String.format(getString(R.string.order_number), order.getOrderNo()));
        tvUserName.setText(String.format("  %s", order.getOrderAddress().getName()));
        tvPhoneNumber.setText(String.format("  %s", order.getOrderAddress().getPhone()));
        tvAddress.setText(order.getOrderAddress().getDetail());
    }

    @Override
    public void needLogin() {

    }

    @Override
    public boolean isReady() {
        return isAdded();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
        }
    }
}
