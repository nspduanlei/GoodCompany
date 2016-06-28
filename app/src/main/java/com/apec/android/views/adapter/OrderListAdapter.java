package com.apec.android.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.domain.entities.order.Order;
import com.apec.android.views.view.OrderListClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.GoodsViewHolder> {

    private final OrderListClickListener mClickListener;
    private final ArrayList<Order> mData;
    private Context mContext;
    private int mStatus;

    public OrderListAdapter(ArrayList<Order> data, Context context,
                            OrderListClickListener clickListener, int status) {
        mData = data;
        mContext = context;
        mClickListener = clickListener;
        mStatus = status;
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

        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_status)
        TextView mTvStatus;
        @BindView(R.id.tv_goods_num)
        TextView mTvGoodsNum;
        @BindView(R.id.tv_total_price)
        TextView mTvTotalPrice;
        @BindView(R.id.tv_hint)
        TextView mTvHint;
        @BindView(R.id.btn_func)
        Button mBtnFunc;

        public GoodsViewHolder(View itemView, final OrderListClickListener recyclerClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bindListener(itemView, recyclerClickListener);
        }

        public void bindData(Order order) {

            mTvTime.setText(order.getOrderDate());

            mTvGoodsNum.setText(String.format(mContext.getString(R.string.order_goods_num),
                    order.getOrderItems().size()));
            mTvTotalPrice.setText(String.format(mContext.getString(R.string.order_goods_price),
                    order.getOrderAmount()));

            mTvTime.setText(order.getOrderDate());

            switch (mStatus) {
                case 1:
                    mTvHint.setVisibility(View.GONE);
                    mTvStatus.setText("待处理");
                    mBtnFunc.setVisibility(View.VISIBLE);
                    mBtnFunc.setText("取消订单");

                    mBtnFunc.setOnClickListener(view -> mClickListener.onCancelOrder(order));
                    break;
                case 2:
                    mTvHint.setVisibility(View.GONE);
                    mTvStatus.setText("备货中");
                    mBtnFunc.setVisibility(View.VISIBLE);
                    mBtnFunc.setText("提醒发货");

                    mBtnFunc.setOnClickListener(view -> {
                                mTvHint.setVisibility(View.VISIBLE);
                                mBtnFunc.setVisibility(View.GONE);
                            }
                    );

                    break;
                case 3:
                    mTvHint.setVisibility(View.GONE);
                    mTvStatus.setText("配送中");
                    mBtnFunc.setVisibility(View.VISIBLE);
                    mBtnFunc.setText("查看物流");

                    mBtnFunc.setOnClickListener(view -> mClickListener.onLookSend(order)
                    );

                    break;
                case 4:
                    mTvHint.setVisibility(View.GONE);
                    mTvStatus.setText("已签收");
                    mBtnFunc.setVisibility(View.GONE);
                    break;
                case 5:
                    mTvHint.setVisibility(View.GONE);
                    mTvStatus.setText("已取消");
                    mBtnFunc.setVisibility(View.GONE);
                    break;
            }
        }

        private void bindListener(View itemView,
                                  final OrderListClickListener clickListener) {
            itemView.setOnClickListener(v ->
                    clickListener.onElementClick(getAdapterPosition()));
        }
    }
}
