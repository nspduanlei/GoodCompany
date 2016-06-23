package com.apec.android.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.domain.entities.transport.GoodsReceipt;
import com.apec.android.views.view.AddressListClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.GoodsViewHolder> {

    private final AddressListClickListener mAddressListClickListener;
    private final List<GoodsReceipt> mGoodsReceipts;
    private Context mContext;
    private boolean mIsSelect;

    public AddressListAdapter(List<GoodsReceipt> goodsReceipts, Context context,
                              AddressListClickListener recyclerClickListener,
                              boolean isSelect) {
        mGoodsReceipts = goodsReceipts;
        mContext = context;
        mAddressListClickListener = recyclerClickListener;
        mIsSelect = isSelect;
    }

    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(
                R.layout.item_address_list, parent, false);
        return new GoodsViewHolder(rootView, mAddressListClickListener);
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder holder, int position) {
        holder.bindGood(mGoodsReceipts.get(position));
    }

    @Override
    public int getItemCount() {
        return mGoodsReceipts.size();
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_user_name)
        TextView mTvUserName;
        @BindView(R.id.tv_phone_number)
        TextView mTvPhoneNumber;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.cb_default_address)
        CheckBox mCbDefaultAddress;
        @BindView(R.id.tv_delete)
        TextView mTvDelete;
        @BindView(R.id.tv_edit)
        TextView mTvEdit;

        public GoodsViewHolder(View itemView, final AddressListClickListener recyclerClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bindListener(itemView, recyclerClickListener);
        }

        public void bindGood(GoodsReceipt goodsReceipt) {
            mTvUserName.setText(goodsReceipt.getName());
            mTvPhoneNumber.setText(goodsReceipt.getPhone());
            mTvAddress.setText(goodsReceipt.getAddrRes().getDetail());

            //如果是默认地址不能删除, 如果是选择地址也不能删除
            if (mIsSelect || goodsReceipt.isDefalut()) {
                mTvDelete.setVisibility(View.GONE);
            } else {
                mTvDelete.setVisibility(View.VISIBLE);
                mTvDelete.setOnClickListener(view ->
                        mAddressListClickListener.onDeleteClick(goodsReceipt.getAddressId()));
            }

            //默认地址的checkBox失效
            if (goodsReceipt.isDefalut()) {
                mCbDefaultAddress.setEnabled(false);
            } else {
                mCbDefaultAddress.setEnabled(true);
            }

            mCbDefaultAddress.setChecked(goodsReceipt.isDefalut());

            mCbDefaultAddress.setOnClickListener(view -> {
                if (!goodsReceipt.isDefalut()) {
                    mAddressListClickListener.onCBDefaultClick(goodsReceipt.getAddressId());
                }
            });

            mTvEdit.setOnClickListener(view ->
                    mAddressListClickListener.onEditClick(goodsReceipt));
        }

        private void bindListener(View itemView,
                                  final AddressListClickListener recyclerClickListener) {
            itemView.setOnClickListener(v ->
                    recyclerClickListener.onElementClick(getAdapterPosition()));
        }
    }
}
