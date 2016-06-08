package com.apec.android.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.domain.entities.goods.Good;
import com.apec.android.domain.entities.goods.Pic;
import com.apec.android.domain.entities.goods.Sku;
import com.apec.android.domain.entities.goods.SkuData;
import com.apec.android.domain.entities.user.ShopCart;
import com.apec.android.support.ImageHelp;
import com.apec.android.views.utils.ShopCartUtil;
import com.apec.android.views.view.RecyclerClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.GoodsViewHolder> {

    private final RecyclerClickListener mRecyclerListener;
    private final List<Sku> mGoods;

    private Context mContext;

    public GoodsListAdapter(List<Sku> goods, Context context,
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


        @BindView(R.id.tv_goods_name)
        TextView mTvGoodsName;
        @BindView(R.id.tv_size)
        TextView mTvSize;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
        @BindView(R.id.iv_goods)
        ImageView mIvGoods;
        @BindView(R.id.iv_cut)
        ImageView mIvCut;
        @BindView(R.id.tv_num)
        TextView mTvNum;
        @BindView(R.id.iv_add)
        ImageView mIvAdd;
        @BindView(R.id.ll_update_num)
        LinearLayout mLlUpdateNum;
        @BindView(R.id.btn_add_cart)
        Button mBtnAddCart;
        @BindView(R.id.btn_order)
        Button mBtnOrder;


        public GoodsViewHolder(View itemView, final RecyclerClickListener recyclerClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bindListener(itemView, recyclerClickListener);
        }

        public void bindGood(Sku sku) {
            mTvGoodsName.setText(sku.getSkuName());
            mTvPrice.setText(sku.getPrice());

            //获取数据库里面的商品数量
            int count = sku.getCount();

            if (count == 0) {
                mBtnAddCart.setVisibility(View.VISIBLE);
                mLlUpdateNum.setVisibility(View.GONE);
                mBtnAddCart.setOnClickListener(view -> addCart(sku, count));
            } else {
                mBtnAddCart.setVisibility(View.GONE);
                mLlUpdateNum.setVisibility(View.VISIBLE);
                mTvNum.setText(String.valueOf(count));
            }

            if (sku.getPics().size() != 0) {
                Pic pic = sku.getPics().get(0);
                if (pic != null && pic.getUrl() != null) {
                    ImageHelp.displayRound(mContext, 15, 0, pic.getUrl(), mIvGoods);
                } else {
                    ImageHelp.displayRoundLocal(mContext, 15, 0, R.drawable.test0010, mIvGoods);
                }
            }

            mBtnOrder.setOnClickListener(view -> {
                if (count == 0) {
                    addCart(sku, count);
                }
                mRecyclerListener.onOrderClick(sku.getId(), count);
            });

            mIvAdd.setOnClickListener(view -> {
                //购物车数量加1
                sku.setCount(sku.getCount()+1);
                mRecyclerListener.onAddCount(sku);

            });

            mIvCut.setOnClickListener(view -> {
                //购物车数量减1

                mRecyclerListener.onCutCount(sku);
            });
        }

        void addCart(Sku sku, int count) {
            count = count + 1;
            sku.setCount(count);

            mRecyclerListener.onAddCartClick(sku, getAdapterPosition());
            mBtnAddCart.setVisibility(View.GONE);
            mLlUpdateNum.setVisibility(View.VISIBLE);

        }

        private void bindListener(View itemView, final RecyclerClickListener recyclerClickListener) {
            itemView.setOnClickListener(v ->
                    recyclerClickListener.onElementClick(getAdapterPosition()));
        }
    }
}
