package com.apec.android.views.adapter;

import android.app.Activity;
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
import com.apec.android.domain.entities.goods.Pic;
import com.apec.android.domain.entities.goods.Sku;
import com.apec.android.domain.entities.goods.SkuAttribute;
import com.apec.android.support.ImageHelp;
import com.apec.android.util.DensityUtils;
import com.apec.android.views.utils.InputNumDialog;
import com.apec.android.views.view.RecyclerClickListener;
import com.bigkoo.convenientbanner.ConvenientBanner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.GoodsViewHolder> {

    private final RecyclerClickListener mRecyclerListener;
    private final List<Sku> mGoods;

    private static final int IS_HEADER = 2;
    private static final int IS_NORMAL = 1;

    private Context mContext;

    public GoodsListAdapter(List<Sku> goods, Context context,
                            RecyclerClickListener recyclerClickListener) {
        mGoods = goods;
        mContext = context;
        mRecyclerListener = recyclerClickListener;
    }

    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = null;

        if (viewType == IS_HEADER) {
            rootView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_goods_header, parent, false);
        } else if (viewType == IS_NORMAL) {
            rootView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_goods_test, parent, false);
        }

        return new GoodsViewHolder(rootView, mRecyclerListener, viewType);
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder holder, int position) {
        if (position != 0 && holder.getItemViewType() == IS_NORMAL) {
            holder.bindGood(mGoods.get(position - 1));
        }

        if (position == 0 && holder.getItemViewType() == IS_HEADER) {
            holder.bindHeader();
        }
    }

    @Override
    public int getItemCount() {
        return mGoods.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return IS_HEADER;
        } else {
            return IS_NORMAL;
        }
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {

        class NormalHolder {
            @BindView(R.id.tv_goods_name)
            TextView mTvGoodsName;
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

            @BindView(R.id.ll_attr)
            LinearLayout mLlAttr;

            public NormalHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        //头部
        class HeaderHolder {
            @BindView(R.id.cb_ad)
            ConvenientBanner mCbAd;

            public HeaderHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        HeaderHolder mHeaderHolder;
        NormalHolder mNormalHolder;

        public GoodsViewHolder(View itemView, final RecyclerClickListener recyclerClickListener,
                               int viewType) {
            super(itemView);

            if (viewType == IS_HEADER) {
                mHeaderHolder = new HeaderHolder(itemView);
            } else {
                mNormalHolder = new NormalHolder(itemView);
            }

            bindListener(itemView, recyclerClickListener);
        }

        public void bindGood(Sku sku) {
            mNormalHolder.mTvGoodsName.setText(sku.getSkuName());
            mNormalHolder.mTvPrice.setText(sku.getPrice());

            //TODO 绑定属性名
            List<SkuAttribute> attrs = new ArrayList<>();
            attrs.addAll(sku.getNonkeyAttr());
            attrs.addAll(sku.getAttributeNames());
            mNormalHolder.mLlAttr.removeAllViews();
            for (SkuAttribute itemAttr : attrs) {
                TextView textView = new TextView(mContext);
                textView.setText(itemAttr.getAttributeValues().get(0).getName());
                textView.setTextColor(mContext.getResources().getColor(R.color.text_2));
                textView.setTextSize(12);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 10, 0);
                mNormalHolder.mLlAttr.addView(textView, params);
            }

            //获取数据库里面的商品数量
            int count = sku.getCount();

            if (count == 0) {
                mNormalHolder.mBtnAddCart.setVisibility(View.VISIBLE);
                mNormalHolder.mLlUpdateNum.setVisibility(View.GONE);
                mNormalHolder.mBtnAddCart.setOnClickListener(view -> addCart(sku, 1));
            } else {
                mNormalHolder.mBtnAddCart.setVisibility(View.GONE);
                mNormalHolder.mLlUpdateNum.setVisibility(View.VISIBLE);
                mNormalHolder.mTvNum.setText(String.valueOf(count));
            }

            if (sku.getPics().size() != 0) {
                Pic pic = sku.getPics().get(0);
                if (pic != null && pic.getUrl() != null) {
                    ImageHelp.display(mContext, pic.getUrl(), mNormalHolder.mIvGoods);
                } else {
                    ImageHelp.displayLocal(mContext, R.drawable.test0010, mNormalHolder.mIvGoods);
                }
            }


            //快速下单
            mNormalHolder.mBtnOrder.setOnClickListener(view -> {
                final int position = getAdapterPosition() - 1;
                if (count == 0) {
                    new InputNumDialog((Activity) mContext, "1",
                            count1 -> {

                                addCart(sku, count1 + 1);
//                                mRecyclerListener.onUpdateCount(sku,
//                                        position, count1 - 1);
                                mRecyclerListener.onOrderClick(sku.getId(), count1 + 1);
                            }).showInputNumDialog();

                } else {
                    mRecyclerListener.onOrderClick(sku.getId(), count);
                }
            });

            mNormalHolder.mIvAdd.setOnClickListener(view -> {
                //购物车数量加1
                mRecyclerListener.onUpdateCount(sku, getAdapterPosition() - 1, 1);
            });

            mNormalHolder.mIvCut.setOnClickListener(view -> {
                //购物车数量减1
                mRecyclerListener.onUpdateCount(sku, getAdapterPosition() - 1, -1);
            });

            //输入数字
            mNormalHolder.mTvNum.setOnClickListener(view -> {
                //TODO 显示输入数字的弹窗
                new InputNumDialog((Activity) mContext, mNormalHolder.mTvNum.getText().toString(),
                        count1 -> {
                            mRecyclerListener.onUpdateCount(sku, getAdapterPosition() - 1, count1);
                        }).showInputNumDialog();
            });
        }

        void addCart(Sku sku, int count) {
            mRecyclerListener.onSaveCartClick(sku, getAdapterPosition() - 1, count);
            mNormalHolder.mBtnAddCart.setVisibility(View.GONE);
            mNormalHolder.mLlUpdateNum.setVisibility(View.VISIBLE);
        }

        private void bindListener(View itemView, final RecyclerClickListener recyclerClickListener) {
            itemView.setOnClickListener(v ->
                    recyclerClickListener.onElementClick(getAdapterPosition() - 1));
        }

        //填充头部
        public void bindHeader() {
            List<Integer> localImages = new ArrayList<>();
            localImages.add(R.drawable.ad_2);
            localImages.add(R.drawable.ad_1);
            localImages.add(R.drawable.ad_3);

            mHeaderHolder.mCbAd.setPages(
                    () -> new LocalImageHolderView(), localImages)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.drawable.shape_page_indicator,
                            R.drawable.shape_page_indicator_focused})
                    //.startTurning(10000)
                    //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .setScrollDuration(1000);
        }
    }
}
