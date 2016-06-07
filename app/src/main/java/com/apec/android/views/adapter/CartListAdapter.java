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
import com.apec.android.domain.entities.user.Skus;
import com.apec.android.views.view.AddressListClickListener;
import com.apec.android.views.view.CartListClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.GoodsViewHolder> {

    private final CartListClickListener mListener;
    private final List<Skus> mData;
    private Context mContext;

    public CartListAdapter(List<Skus> data, Context context,
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


        public GoodsViewHolder(View itemView, final CartListClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bindListener(itemView, listener);
        }

        public void bindGood(Skus data) {


        }

        private void bindListener(View itemView,
                                  final CartListClickListener listener) {
            itemView.setOnClickListener(v ->
                    listener.onElementClick(getAdapterPosition()));
        }
    }
}
