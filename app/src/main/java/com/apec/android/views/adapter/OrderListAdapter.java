package com.apec.android.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.domain.entities.order.Order;
import com.apec.android.domain.entities.order.OrderItem;

import com.apec.android.util.ColorPhrase;
import com.apec.android.views.adapter.listView.CommonAdapter;
import com.apec.android.views.adapter.listView.MyViewHolder;
import com.apec.android.views.view.OrderListClickListener;
import com.apec.android.views.widget.NoScrollListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.GoodsViewHolder> {

    private final OrderListClickListener mClickListener;
    private final ArrayList<Order> mData;
    private Context mContext;

    public OrderListAdapter(ArrayList<Order> data, Context context,
                            OrderListClickListener clickListener) {
        mData = data;
        mContext = context;
        mClickListener = clickListener;
    }

    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(
                R.layout.item_order_list, parent, false);
        return new GoodsViewHolder(rootView, mClickListener);
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.tv_order_pro)
//        TextView mTvOrderPro;
//
//        @BindView(R.id.tv_order_num)
//        TextView mTvOrderNum;
//        @BindView(R.id.lv_sku)
//        NoScrollListView mLvSku;
//        @BindView(R.id.tv_total_price)
//        TextView mTvTotalPrice;
//        @BindView(R.id.tv_detail)
//        TextView mTvDetail;

        public GoodsViewHolder(View itemView, final OrderListClickListener recyclerClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bindListener(itemView, recyclerClickListener);
        }

        public void bindData(Order order) {

            //订单中的购买的商品列表
//            final BaseAdapter itemAdapter = new CommonAdapter<OrderItem>(mContext,
//                    order.getOrderItems(), R.layout.order_goods_item) {
//                @Override
//                public void convert(MyViewHolder holder, OrderItem orderItem) {
//                    if (orderItem.getSku().getPics().size() > 0) {
//                        holder.setImageUrl(R.id.iv_goods_pic,
//                                orderItem.getSku().getPics().get(0).getUrl());
//                    }
//                    holder.setText(R.id.tv_goods_name, orderItem.getSku().getSkuName())
//                            .setText(R.id.tv_price, String.format("￥%s",
//                                    orderItem.getSku().getPrice()))
//                            .setText(R.id.tv_num, String.format("x %d", orderItem.getNum()));
//                }
//            };
//
//            mLvSku.setAdapter(itemAdapter);
//            mLvSku.setOnItemClickListener((adapterView, view, i, l) -> {
//                mClickListener.onGoodsItemClick(order.getOrderItems().get(i).getId());
//            });
//
//            //订单号
//            mTvOrderNum.setText(String.format(mContext.getString(R.string.order_number),
//                    order.getOrderNo()));
//
//            //订单总价
//            String total = String.format(
//                    mContext.getString(R.string.total_price_order), order.getOrderAmount());
//
//            CharSequence chars = ColorPhrase.from(total).withSeparator("{}")
//                    .innerColor(mContext.getResources().getColor(R.color.color_5))
//                    .outerColor(mContext.getResources().getColor(R.color.color_text_1)).format();
//
//            mTvTotalPrice.setText(chars);
//
//            mTvDetail.setOnClickListener(view -> mClickListener.onDetailClick(order.getId()));
//
//
//            //订单状态
//            switch (order.getOrderType()) {
//                case 1://待处理
//                    mTvOrderPro.setText(String.format("   %s ", "订单待确认"));
//                    mTvOrderPro.setBackgroundResource(R.drawable.order_pro_bg_1);
//                    break;
//
//                case 2://处理中
//                    mTvOrderPro.setText(String.format("   %s ", "订单处理中"));
//                    mTvOrderPro.setBackgroundResource(R.drawable.order_pro_bg_2);
//                    break;
//
//                case 3://已完成
//                    mTvOrderPro.setText(String.format("   %s ", "订单已完成"));
//                    mTvOrderPro.setBackgroundResource(R.drawable.order_pro_bg_3);
//                    break;
//
//                case 4://订单取消
//                    mTvOrderPro.setText(String.format("   %s ", "订单取消"));
//                    mTvOrderPro.setBackgroundResource(R.drawable.order_pro_bg_1);
//                    break;
//            }
        }


        private void bindListener(View itemView,
                                  final OrderListClickListener clickListener) {
            itemView.setOnClickListener(v ->
                    clickListener.onElementClick(getAdapterPosition()));
        }
    }
}
