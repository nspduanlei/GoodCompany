package com.apec.android.ui.fragment.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apec.android.R;
import com.apec.android.config.Constants;
import com.apec.android.domain.goods.SkuAttribute;
import com.apec.android.domain.order.Order;
import com.apec.android.domain.order.OrderItem;
import com.apec.android.ui.activity.goods.GoodsActivity;
import com.apec.android.ui.activity.order.OrderActivity;
import com.apec.android.ui.activity.user.RegisterFActivity;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.MyViewHolder;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.fragment.user.ShoppingCartFragment;
import com.apec.android.ui.presenter.order.MyOrdersPresenter;
import com.apec.android.util.ColorPhrase;

import java.util.ArrayList;

/**
 * 我的订单
 * Created by Administrator on 2016/2/26.
 */
public class MyOrdersFragment extends BaseListFragment<MyOrdersPresenter.IView,
        MyOrdersPresenter> implements MyOrdersPresenter.IView, View.OnClickListener {

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
        return new MyOrdersPresenter(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        mPresenter.getMyOrders();
    }

    private ArrayList<Order> mData;
    private BaseAdapter mAdapter;

    private FrameLayout loading;
    private LinearLayout empty;

    private final static int REQUEST_CODE_DETAIL = 1002;
    public final static int RESULT_CODE_DETAIL = 101;

    private void initView(View view) {
        //标题
        TextView title = (TextView) view.findViewById(R.id.tv_top_title);
        title.setText("我的订单");
        view.findViewById(R.id.iv_back).setOnClickListener(this);

        loading = (FrameLayout) view.findViewById(R.id.fl_loading);
        empty = (LinearLayout) view.findViewById(R.id.ll_empty);
        empty.findViewById(R.id.btn_goto).setOnClickListener(this);

        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<Order>(getActivity(), mData,
                R.layout.my_order_item) {
            @Override
            public void convert(final MyViewHolder holder, Order order) {
                //商品list适配器
                final BaseAdapter itemAdapter = new CommonAdapter<OrderItem>(getActivity(),
                        order.getOrderItems(), R.layout.order_goods_item) {
                    @Override
                    public void convert(MyViewHolder holder, OrderItem orderItem) {

                        //净含量
                        if (orderItem.getSku().getNonkeyAttr().size() > 0) {
                            holder.setVisibility(R.id.tv_goods_net, View.VISIBLE);
                            holder.setText(R.id.tv_goods_net, String.format("%s : %s",
                                    orderItem.getSku().getNonkeyAttr().get(0).getName(),
                                    orderItem.getSku().getNonkeyAttr().get(0)
                                            .getAttributeValues().get(0).getName()));
                        } else {
                            holder.setVisibility(R.id.tv_goods_net, View.GONE);
                        }


                        if (orderItem.getSku().getPics().size() > 0) {
                            holder.setImageUrl(R.id.iv_goods_pic,
                                    orderItem.getSku().getPics().get(0).getUrl());
                        }

                        holder.setText(R.id.tv_goods_name, orderItem.getSku().getSkuName())
                                .setText(R.id.tv_price, String.format("￥%s",
                                        orderItem.getSku().getPrice()))
                                .setText(R.id.tv_num, String.format("x %d", orderItem.getNum()));
                    }
                };
                holder.setListView(R.id.lv_sku, itemAdapter)
                        .setListViewItemClick(R.id.lv_sku, new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView,
                                                    View view, int i, long l) {
                                //商品列表点击
                            }
                        });

                //订单号
                holder.setText(R.id.tv_order_num, String.format(getString(R.string.order_number),
                        order.getOrderNo()));

                //订单总价
                String total = String.format(
                        getString(R.string.total_price_order), order.getOrderAmount());

                CharSequence chars = ColorPhrase.from(total).withSeparator("{}")
                        .innerColor(getResources().getColor(R.color.color_5))
                        .outerColor(getResources().getColor(R.color.color_text_1)).format();
                holder.setTextChar(R.id.tv_total_price, chars);

                holder.setOnClickLister(R.id.tv_detail, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //跳转到订单详情
                        Intent intent = new Intent(getActivity(), OrderActivity.class);
                        intent.putExtra(OrderActivity.EXTRA_ORDER_ID,
                                mData.get(holder.getMPosition()).getId());
                        startActivityForResult(intent, REQUEST_CODE_DETAIL);
                    }
                });

                //订单状态
                switch (order.getOrderType()) {
                    case 1://待处理
                        holder.setText(R.id.tv_order_pro, String.format("   %s ", "订单待确认"));
                        holder.setTextBackground(R.id.tv_order_pro, R.drawable.order_pro_bg_1);
                        break;

                    case 2://处理中
                        holder.setText(R.id.tv_order_pro, String.format("   %s ", "订单处理中"));
                        holder.setTextBackground(R.id.tv_order_pro, R.drawable.order_pro_bg_2);
                        break;

                    case 3://已完成
                        holder.setText(R.id.tv_order_pro, String.format("   %s ", "订单已完成"));
                        holder.setTextBackground(R.id.tv_order_pro, R.drawable.order_pro_bg_3);
                        break;

                    case 4://订单取消
                        holder.setText(R.id.tv_order_pro, String.format("   %s ", "订单取消"));
                        holder.setTextBackground(R.id.tv_order_pro, R.drawable.order_pro_bg_1);
                        break;
                }
            }
        };

        setListAdapter(mAdapter);
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
        empty.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean isReady() {
        return isAdded();
    }

    @Override
    public void needLogin() {
        Toast.makeText(getActivity(), R.string.please_login, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), RegisterFActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_LOGIN);
    }

    /**
     * 订单列表获取成功
     *
     * @param orders
     */
    @Override
    public void obtainOrdersSuccess(ArrayList<Order> orders) {
        mData.clear();
        mData.addAll(orders);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;

            case R.id.btn_goto:
                //订单为空，跳转到主页去下单
                Intent intent = new Intent(getActivity(), GoodsActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_CODE_LOGIN:
                if (resultCode == Constants.RESULT_CODE_LOGIN_SUCCESS) {
                    mPresenter.getMyOrders();
                } else {
                    getActivity().finish();
                }
                break;
            case REQUEST_CODE_DETAIL:
                if (resultCode == RESULT_CODE_DETAIL) { //订单取消
                    mPresenter.getMyOrders();
                }
                break;
        }
    }
}
