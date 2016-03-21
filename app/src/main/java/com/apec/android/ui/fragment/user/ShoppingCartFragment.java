package com.apec.android.ui.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apec.android.R;
import com.apec.android.domain.user.ShopCart;
import com.apec.android.domain.user.Skus;
import com.apec.android.ui.activity.user.RegisterFActivity;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.ViewHolder;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.presenter.user.ShoppingCartPresenter;
import com.apec.android.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/26.
 */
public class ShoppingCartFragment extends BaseListFragment<ShoppingCartPresenter.IView,
        ShoppingCartPresenter> implements ShoppingCartPresenter.IView, View.OnClickListener {

    public static ShoppingCartFragment newInstance() {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    protected ShoppingCartPresenter createPresenter() {
        return new ShoppingCartPresenter(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        //获取购物车数据
        mPresenter.obtainShopCart();
    }

    private List<Skus> mData;
    private BaseAdapter mAdapter;
    private FrameLayout loading;
    //全选复选框
    private CheckBox allSelect;
    //合计金额
    private TextView totalPrices;
    //去结算
    private Button gotoPay;

    //购物车中的商品数量
    private int mCount;
    private Double mPrice;

    private void initView(View view) {
        TextView title = (TextView) view.findViewById(R.id.tv_top_title);
        title.setText("购物车");

        view.findViewById(R.id.iv_back).setOnClickListener(this);
        loading = (FrameLayout) view.findViewById(R.id.fl_loading);

        allSelect = (CheckBox) view.findViewById(R.id.cb_all_select);
        allSelect.setChecked(true);

        totalPrices = (TextView) view.findViewById(R.id.tv_total_carts);
        gotoPay = (Button) view.findViewById(R.id.btn_goto_pay);
        gotoPay.setOnClickListener(this);

        allSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allSelect.isChecked()) {
                    //全选
                    L.e("test001", "全选");
                    for (Skus item : mData) {
                        if (!item.isSelectCart()) {
                            item.setIsSelectCart(true);
                        }
                    }
                } else {
                    //取消全选
                    L.e("test001", "取消全选");
                    for (Skus item : mData) {
                        if (item.isSelectCart()) {
                            item.setIsSelectCart(false);
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });

//        allSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    //全选
//                    L.e("test001", "全选");
//                    for (Skus item : mData) {
//                        if (!item.isSelectCart()) {
//                            item.setIsSelectCart(true);
//                        }
//                    }
//                } else {
//                    //取消全选
//                    L.e("test001", "取消全选");
//                    for (Skus item : mData) {
//                        if (item.isSelectCart()) {
//                            item.setIsSelectCart(false);
//                        }
//                    }
//                }
//                mAdapter.notifyDataSetChanged();
//            }
//        });

        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<Skus>(getActivity(), mData,
                R.layout.shopping_cart_item) {
            @Override
            public void convert(final ViewHolder holder, final Skus skus) {
                holder.setText(R.id.tv_goods_name, skus.getSku().getSkuName())
                        .setTextTow(R.id.tv_add_count, skus.getSku().getPrice(),
                                skus.getCount())
                        .setText(R.id.tv_total,
                                String.valueOf(
                                        Double.valueOf(skus.getSku().getPrice()) *
                                                skus.getCount()
                                ));

                if (skus.isSelectCart()) {
                    holder.setChecked(R.id.cb_select, true);
                } else {
                    holder.setChecked(R.id.cb_select, false);
                }

                holder.setOnCheckChangeLister(R.id.cb_select, new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            mCount++;

                            mPrice = mPrice + Double.valueOf(
                                    mData.get(holder.getMPosition()).getSku().getPrice()) *
                                    skus.getCount();

                            //如果商品被全部选择了，则将全选复选框设为选择状态，反正则异然
                            if (mCount == mData.size()) {
                                allSelect.setChecked(true);
                            }
                        } else {
                            mCount--;

                            mPrice = mPrice - Double.valueOf(
                                    mData.get(holder.getMPosition()).getSku().getPrice()) *
                                    skus.getCount();

                            allSelect.setChecked(false);
                        }
                        //填充购物车数量
                        gotoPay.setText(String.format(getString(R.string.goto_pay_btn), mCount));
                        //填充总价
                        totalPrices.setText(String.format(getString(R.string.total_price_cart),
                                mPrice));

                    }
                });
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
    }

    @Override
    public void obtainCartSuccess(ShopCart shopCart) {
        mData.clear();
        mData.addAll(shopCart.getSkus());
        mAdapter.notifyDataSetChanged();

        mCount = shopCart.getTotal();
        mPrice = Double.valueOf(shopCart.getTotalPrice());
        totalPrices.setText(String.format(getString(R.string.total_price_cart),
                shopCart.getTotalPrice()));
        gotoPay.setText(String.format(getString(R.string.goto_pay_btn), mCount));
    }

    @Override
    public void needLogin() {
        Toast.makeText(getActivity(), R.string.please_login, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), RegisterFActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean isReady() {
        return isAdded();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
        }
    }
}
