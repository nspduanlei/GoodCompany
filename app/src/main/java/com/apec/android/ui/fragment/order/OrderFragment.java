package com.apec.android.ui.fragment.order;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.domain.order.Order;
import com.apec.android.domain.order.OrderItem;
import com.apec.android.ui.activity.order.OrderActivity;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.MyViewHolder;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.order.OrderPresenter;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

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

    //取消订单 ll_cancel_order
    private TextView tvCancelOrder;

    //商品列表 lv_sku
    private ListView lvSku;

    //总价 tv_total_price
    private TextView tvTotalPrice;

    //订单状态  fl_state_text  ll_state
    private FrameLayout flStateText;
    private LinearLayout llState;

    //view_circle_1  view_line_1
    private View circle1, circle2, circle3, line1, line2;
    private TextView tvStateText1, tvStateText2, tvStateText3;

    private FrameLayout loading;

    private void initView(View view) {
        //标题
        TextView title = (TextView) view.findViewById(R.id.tv_top_title);
        title.setText("订单详情");
        view.findViewById(R.id.iv_back).setOnClickListener(this);
        loading = (FrameLayout) view.findViewById(R.id.fl_loading);

        //订单信息
        tvOrderNum = (TextView) view.findViewById(R.id.tv_order_num);
        btnCancelOrder = (Button) view.findViewById(R.id.btn_cancel_order);
        btnCancelOrder.setOnClickListener(this);

        tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        tvPhoneNumber = (TextView) view.findViewById(R.id.tv_phone_number);
        tvAddress = (TextView) view.findViewById(R.id.tv_address);

        tvCancelOrder = (TextView) view.findViewById(R.id.tv_cancel_order);

        lvSku = (ListView) view.findViewById(R.id.lv_sku);

        tvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);

        //订单状态
        flStateText = (FrameLayout) view.findViewById(R.id.fl_state_text);
        llState = (LinearLayout) view.findViewById(R.id.ll_state);
        circle1 = view.findViewById(R.id.view_circle_1);
        circle2 = view.findViewById(R.id.view_circle_2);
        circle3 = view.findViewById(R.id.view_circle_3);
        line1 = view.findViewById(R.id.view_line_1);
        line2 = view.findViewById(R.id.view_line_2);
        tvStateText1 = (TextView) view.findViewById(R.id.tv_state_text_1);
        tvStateText2 = (TextView) view.findViewById(R.id.tv_state_text_2);
        tvStateText3 = (TextView) view.findViewById(R.id.tv_state_text_3);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyCase() {

    }

    @Override
    public void cancelOrderSuccess() {
        //取消订单成功
        tvCancelOrder.setVisibility(View.VISIBLE);
        flStateText.setVisibility(View.GONE);
        llState.setVisibility(View.GONE);
        btnCancelOrder.setVisibility(View.GONE);
        getActivity().setResult(MyOrdersFragment.RESULT_CODE_DETAIL);
    }

    @Override
    public void getOrderSuccess(final Order order) {
        //订单数据获取成功， 填充数据
        tvOrderNum.setText(String.format(getString(R.string.order_number), order.getOrderNo()));
        tvUserName.setText(String.format("  %s", order.getOrderAddress().getName()));
        tvPhoneNumber.setText(String.format("  %s", order.getOrderAddress().getPhone()));
        tvAddress.setText(order.getOrderAddress().getDetail());

        tvTotalPrice.setText(String.format("￥%s", order.getOrderAmount()));

        //商品列表
        lvSku.setAdapter(new CommonAdapter<OrderItem>(getActivity(), order.getOrderItems(),
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

                //净含量
//                if (orderItem.getSku().getNonkeyAttr().size() > 0) {
//                    holder.setVisibility(R.id.tv_goods_net, View.VISIBLE);
//                    holder.setText(R.id.tv_goods_net, String.format("%s : %s",
//                            orderItem.getSku().getNonkeyAttr().get(0).getName(),
//                            orderItem.getSku().getNonkeyAttr().get(0)
//                                    .getAttributeValues().get(0).getName()));
//                } else {
//                    holder.setVisibility(R.id.tv_goods_net, View.GONE);
//                }
            }
        });

        //订单状态
        switch (order.getOrderType()) {
            case 1: //待处理
                break;
            case 2: //处理中
                circle2.setBackgroundResource(R.drawable.circle_order_state);
                line1.setBackgroundResource(R.color.color_5);

                tvStateText2.setTextColor(getResources().getColor(R.color.color_5));
                break;
            case 3: //已完成
                circle2.setBackgroundResource(R.drawable.circle_order_state);
                line1.setBackgroundResource(R.color.color_5);
                circle3.setBackgroundResource(R.drawable.circle_order_state);
                line2.setBackgroundResource(R.color.color_5);

                tvStateText3.setTextColor(getResources().getColor(R.color.color_5));
                tvStateText2.setTextColor(getResources().getColor(R.color.color_5));

                btnCancelOrder.setVisibility(View.GONE);
                break;
            case 4: //订单取消
                cancelOrderSuccess();
                break;
        }
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

            case R.id.btn_cancel_order:
                //取消订单弹窗
                View dialogView = getActivity().getLayoutInflater()
                        .inflate(R.layout.dialog_cancel_order, null);
                ViewHolder viewHolder = new ViewHolder(dialogView);
                DialogPlus dialog = new DialogPlus.Builder(getActivity())
                        .setContentHolder(viewHolder)
                        .setCancelable(true)
                        .setGravity(DialogPlus.Gravity.CENTER)
                        .setBackgroundColorResourceId(R.color.transparency)
                        .setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(DialogPlus dialog, View view) {
                                switch (view.getId()) {
                                    case R.id.tv_cancel:
                                        dialog.dismiss();
                                        break;
                                    case R.id.tv_sure:
                                        dialog.dismiss();
                                        mPresenter.cancelOrder(mOrderId);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        })
                        .create();
                dialog.show();
                break;
        }
    }
}
