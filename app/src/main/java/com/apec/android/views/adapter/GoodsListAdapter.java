package com.apec.android.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apec.android.R;
import com.apec.android.domain.entities.goods.Good;
import com.apec.android.domain.entities.goods.Pic;
import com.apec.android.support.ImageHelp;
import com.apec.android.views.view.RecyclerClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.GoodsViewHolder> {

    private final RecyclerClickListener mRecyclerListener;
    private final List<Good> mGoods;

    private Context mContext;

    public GoodsListAdapter(List<Good> goods, Context context,
                            RecyclerClickListener recyclerClickListener) {
        mGoods = goods;
        mContext = context;
        mRecyclerListener = recyclerClickListener;
    }

    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(
            R.layout.item_goods_test, parent, false);

        return new GoodsViewHolder(rootView, mRecyclerListener);
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder holder, int position) {
        holder.bindGood(mGoods.get(position));
    }

    @Override
    public int getItemCount() {
        return mGoods.size();
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_goods)
        ImageView goodsImageView;

        public GoodsViewHolder(View itemView, final RecyclerClickListener recyclerClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bindListener(itemView, recyclerClickListener);
        }

        public void bindGood(Good good) {
            if (good.getPics().size() == 0)
                return;

            Pic pic = good.getPics().get(0);
            if (pic != null) {
                ImageHelp.displayRound(mContext, 15, 0, pic.getUrl(), goodsImageView);
            }
        }

        private void bindListener(View itemView, final RecyclerClickListener recyclerClickListener) {
            itemView.setOnClickListener(v ->
                recyclerClickListener.onElementClick(getAdapterPosition()));
        }
    }
}
