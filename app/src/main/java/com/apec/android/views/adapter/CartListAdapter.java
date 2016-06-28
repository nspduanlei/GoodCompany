package com.apec.android.views.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.apec.android.R;
import com.apec.android.domain.entities.goods.SkuData;
import com.apec.android.support.ImageHelp;
import com.apec.android.views.utils.InputNumDialog;
import com.apec.android.views.view.CartListClickListener;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.GoodsViewHolder> {

    private final CartListClickListener mListener;
    private final List<SkuData> mData;
    private Context mContext;

    public CartListAdapter(List<SkuData> data, Context context,
                           CartListClickListener listener) {
        mData = data;
        mContext = context;
        mListener = listener;
    }

    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(
                R.layout.item_shopping_cart, parent, false);
        return new GoodsViewHolder(rootView, mListener);
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder holder, int position) {
        holder.bindGood(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_select)
        CheckBox mCbSelect;
        @BindView(R.id.iv_goods)
        ImageView mIvGoods;
        @BindView(R.id.tv_goods_name)
        TextView mTvGoodsName;
        @BindView(R.id.tv_price)
        TextView mTvPrice;

        @BindView(R.id.iv_cut)
        ImageView mBtnCut;
        @BindView(R.id.iv_add)
        ImageView mBtnAdd;
        @BindView(R.id.tv_num)
        TextView mTvAddCount;

        @BindView(R.id.iv_lose)
        ImageView mIvLose;


        public GoodsViewHolder(View itemView, final CartListClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bindListener(itemView, listener);
        }

        public void bindGood(SkuData data) {
            mTvGoodsName.setText(data.getSkuName());
            mTvPrice.setText(String.format(mContext.getString(R.string.add_order_total),
                    data.getPrice()));
            mTvAddCount.setText(String.valueOf(data.getCount()));

            ImageHelp.display(mContext, data.getPic(), mIvGoods);

            if (data.getStatus() != 1) {
                mIvLose.setVisibility(View.VISIBLE);
                mCbSelect.setVisibility(View.GONE);
            } else {
                mIvLose.setVisibility(View.GONE);
                mCbSelect.setVisibility(View.VISIBLE);
            }

            mCbSelect.setChecked(data.isSelect());

            mCbSelect.setOnClickListener(view -> mListener.onCheckChange(data,
                    mCbSelect.isChecked(), getAdapterPosition()));


            mBtnAdd.setOnClickListener(view -> mListener.onUpdateCount(data, getAdapterPosition(), 1));

            mBtnCut.setOnClickListener(view -> mListener.onUpdateCount(data, getAdapterPosition(), -1));

            //输入数字
            mTvAddCount.setOnClickListener(view -> {
                //TODO 显示输入数字的弹窗
                new InputNumDialog((Activity) mContext, mTvAddCount.getText().toString(), count1 -> {
                    mListener.onUpdateCount(data, getAdapterPosition(), count1);
                }).showInputNumDialog();
            });

        }

        private void bindListener(View itemView,
                                  final CartListClickListener listener) {
            itemView.setOnClickListener(v ->
                    listener.onElementClick(getAdapterPosition()));
        }
    }
}
