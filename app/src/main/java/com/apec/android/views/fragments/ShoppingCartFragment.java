package com.apec.android.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.domain.entities.goods.SkuData;
import com.apec.android.domain.entities.user.ShopCart;
import com.apec.android.domain.entities.user.ShopCartData;
import com.apec.android.injector.components.DaggerShopCartComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.ShoppingCartPresenter;
import com.apec.android.mvp.views.ShoppingCartView;
import com.apec.android.util.T;
import com.apec.android.views.activities.MainActivity;
import com.apec.android.views.activities.TrueOrderActivity;
import com.apec.android.views.adapter.CartListAdapter;
import com.apec.android.views.adapter.CartListEditAdapter;
import com.apec.android.views.fragments.core.BaseFragment;
import com.apec.android.views.utils.AlertDialog;
import com.apec.android.views.utils.ShopCartUtil;
import com.apec.android.views.view.CartListClickListener;
import com.apec.android.views.view.CartListEditClickListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/6/7.
 */
public class ShoppingCartFragment extends BaseFragment implements ShoppingCartView,
        CartListClickListener {

    @Inject
    ShoppingCartPresenter mPresenter;

    @BindView(R.id.cb_select_all)
    CheckBox mCbSelectAll;
    @BindView(R.id.btn_goto_pay)
    Button mBtnGotoPay;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.rv_cart)
    RecyclerView mRvCart;

    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;

    CartListAdapter mAdapter;

    ArrayList<SkuData> mData = new ArrayList<>();

    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;

    //编辑购物车
    @BindView(R.id.tv_edit)
    TextView mTvEdit;
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.rv_cart_edit)
    RecyclerView mRvCartEdit;
    @BindView(R.id.cb_select_all_edit)
    CheckBox mCbSelectAllEdit;
    @BindView(R.id.btn_delete_edit)
    Button mBtnDeleteEdit;
    @BindView(R.id.ll_edit)
    LinearLayout mLlEdit;

    boolean isEdit = false;
    CartListEditAdapter mAdapterEdit;

    //总价
    private Double mTotalPrice = 0.0;
    //总数量
    private int mCount;
    private int mCountEdit = 0;

    boolean isSelectAll = true;
    boolean isSelectAllEdit = false;

    @Override
    protected void initUI(View view) {
        mRvCart.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CartListAdapter(mData, getActivity(), this);
        mRvCart.setAdapter(mAdapter);

        mRvCartEdit.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapterEdit = new CartListEditAdapter(mData, getActivity(), new CartListEditClickListener() {
            @Override
            public void onElementClick(int position) {

            }

            @Override
            public void onCheckChange(SkuData skuData, boolean isCheck, int position) {
                skuData.setSelect(isCheck);
                mData.set(position, skuData);
                if (isCheck) {
                    mCountEdit++;
                } else {
                    mCountEdit--;
                }
                //TODO
                notifyDataEdit();
            }
        });
        mRvCartEdit.setAdapter(mAdapterEdit);
    }

    private void notifyDataEdit() {
        mBtnDeleteEdit.setText(String.format(getString(R.string.delete_btn), mCountEdit));

        //是否全选按钮选中
        for (SkuData skuData:mData) {
            if (skuData.isSelect()) {
                isSelectAllEdit = true;
            } else {
                isSelectAllEdit = false;
                break;
            }
        }
        mCbSelectAllEdit.setChecked(isSelectAllEdit);
        mAdapterEdit.notifyDataSetChanged();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {
        DaggerShopCartComponent.builder()
                .activityModule(new ActivityModule(getActivity()))
                .appComponent(myApplication.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }

    @Override
    public void showLoadingView() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public void onDataEmpty() {
        mLlEmpty.setVisibility(View.VISIBLE);
        mTvEdit.setVisibility(View.GONE);
    }

    @Override
    public void hideEmpty() {
        mLlEmpty.setVisibility(View.GONE);
        mTvEdit.setVisibility(View.VISIBLE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE_LOGIN) {
            if (resultCode == Constants.RESULT_CODE_LOGIN_SUCCESS) {
                getData();
            }
        }
        if (requestCode == Constants.REQUEST_CODE_TRUE_ORDER) {
            if (resultCode == Constants.RESULT_CODE_ORDER_SUCCESS) {
                getData();
            }
        }
    }

    @Override
    public void onElementClick(int position) {

    }

    @Override
    public void onUpdateCount(SkuData data, int adapterPosition, int num) {

        int newCount = data.getCount() + num;
        if (newCount > Constants.MAX_GOODS_COUNT) {
            data.setCount(Constants.MAX_GOODS_COUNT);
            mData.set(adapterPosition, data);
            mAdapter.notifyDataSetChanged();
            T.showShort(getActivity(), "商品数量不能超过" + Constants.MAX_GOODS_COUNT);
            return;
        }

        if (newCount == 0) {
            new AlertDialog(getActivity(), "商品减为0将被删除，确定删除吗？", () -> {
                ShopCartUtil.deleteSku(data);
                ((MainActivity) getActivity()).updateGoods();

                mData.remove(adapterPosition);

                if (mData.size() == 0) {
                    onDataEmpty();
                } else {
                    updateAllPrice();
                    mAdapter.notifyDataSetChanged();
                    mAdapterEdit.notifyDataSetChanged();
                }
            }).showAlertDialog();

            return;
        }

        ShopCartUtil.updateCount(data.getSkuId(), num);
        ((MainActivity) getActivity()).updateGoods();

        data.setCount(data.getCount() + num);
        mData.set(adapterPosition, data);

        updateAllPrice();

        mAdapter.notifyDataSetChanged();
    }


    /**
     *
     * 更新价格和数量
     */
    public void updateAllPrice() {
        mTotalPrice = 0.0;
        mCount = 0;
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).isSelect()) {
                mTotalPrice = mTotalPrice +
                        Double.valueOf(mData.get(i).getPrice()) * mData.get(i).getCount();
                mCount ++;
                isSelectAll = true;

            } else {
                isSelectAll = false;
            }
        }

        mCbSelectAll.setChecked(isSelectAll);

        mTvTotalPrice.setText(String.format(getString(R.string.total_price_cart),
                String.valueOf(mTotalPrice)));
        mBtnGotoPay.setText(String.format(getString(R.string.goto_pay_btn), mCount));
    }

    @Override
    public void onCheckChange(SkuData skuData, boolean isCheck, int position) {
        if (isCheck) {
            mTotalPrice = mTotalPrice + Double.valueOf(skuData.getPrice()) * skuData.getCount();
            mCount = mCount + 1;
        } else {
            mCount = mCount - 1;
            mTotalPrice = mTotalPrice - Double.valueOf(skuData.getPrice()) * skuData.getCount();
        }

        skuData.setSelect(isCheck);
        mData.set(position, skuData);

        ShopCartUtil.updateCheck(skuData, isCheck);

        notifyData();
    }

    private void notifyData() {
        mTvTotalPrice.setText(String.format(getString(R.string.total_price_cart),
                String.valueOf(mTotalPrice)));
        mBtnGotoPay.setText(String.format(getString(R.string.goto_pay_btn), mCount));

        //是否全选按钮选中
        for (SkuData skuData:mData) {
            if (skuData.isSelect()) {
                isSelectAll = true;
            } else {
                isSelectAll = false;
                break;
            }
        }
        mCbSelectAll.setChecked(isSelectAll);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void bindCart(ShopCartData shopCart) {
        mData.clear();
        mData.addAll(shopCart.getSkus());

        updateAllCountAndPrice();
    }

    public void getData() {
        mTotalPrice = 0.0;
        mCount = 0;
        mPresenter.getData();

        exitEdit();
    }

    //全选
    @OnClick(R.id.cb_select_all)
    void onSelectAllClicked(View view) {
        if (mCbSelectAll.isChecked()) {
            for (SkuData skuData : mData) {
                if (!skuData.isSelect()) {
                    skuData.setSelect(true);
                }
            }
            ShopCartUtil.selectAll();
        } else {
            for (SkuData skuData : mData) {
                if (skuData.isSelect()) {
                    skuData.setSelect(false);
                }
            }
            ShopCartUtil.cancelSelectAll();
        }
        updateAllCountAndPrice();
    }

    //编辑状态全选
    @OnCheckedChanged(R.id.cb_select_all_edit)
    void onSelectAllEditCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            for (SkuData skuData : mData) {
                if (!skuData.isSelect()) {
                    skuData.setSelect(true);
                }
            }
            mCountEdit = mData.size();
        } else {
            for (SkuData skuData : mData) {
                if (skuData.isSelect()) {
                    skuData.setSelect(false);
                }
            }
            mCountEdit = 0;
        }
        updateEdit();
    }


    @Override
    public void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @OnClick(R.id.btn_goto_pay)
    void onGotoPayClicked(View view) {
        List<SkuData> list = ShopCartUtil.getSelectList();

        if (list.size() == 0) {
            return;
        }

        ArrayList<Integer> skuIds = new ArrayList<>();
        for (SkuData item : list) {
            skuIds.add(Integer.valueOf(item.getSkuId()));
        }

        //确认订单
        Intent intent = new Intent(getActivity(), TrueOrderActivity.class);
        intent.putIntegerArrayListExtra("sku_ids", skuIds);
        intent.putExtra("count", list.size());
        startActivityForResult(intent, Constants.REQUEST_CODE_TRUE_ORDER);
    }

    public void updateAllCountAndPrice() {
        mTotalPrice = ShopCartUtil.getSelectPrice();
        mCount = ShopCartUtil.getSelectCount();
        notifyData();
    }

    private void updateEdit() {
        mBtnDeleteEdit.setText(String.format(getString(R.string.delete_btn), mCountEdit));
        mAdapterEdit.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_edit)
    void onEditClicked(View view) {
        if (isEdit) {
            //如果是编辑状态退出编辑


            getData();
            //exitEdit();
        } else {
            //如果不是编辑状态，进入编辑状态
            isEdit = true;

            mTvEdit.setText("完成");

            for (SkuData skuData:mData) {
                if (skuData.isSelect()) {
                    skuData.setSelect(false);
                }
            }
            mCountEdit = 0;
            updateEdit();

            mLlContent.setVisibility(View.GONE);
            mLlEdit.setVisibility(View.VISIBLE);
            mCbSelectAllEdit.setChecked(false);
        }
    }

    //退出编辑状态
    public void exitEdit() {
        isEdit = false;

        mTvEdit.setText("编辑");

        mLlContent.setVisibility(View.VISIBLE);
        mLlEdit.setVisibility(View.GONE);
    }


    @OnClick(R.id.btn_delete_edit)
    void onDeleteClicked(View view) {

        ArrayList<SkuData> skuDatas = new ArrayList<>();

        Iterator iterator = mData.iterator();
        while (iterator.hasNext()) {
            SkuData skuData = (SkuData) iterator.next();

            if (skuData.isSelect()) {
                skuDatas.add(skuData);
                iterator.remove();
            }
        }

        if (skuDatas.size() > 0) {
            ShopCartUtil.deleteSkuList(skuDatas);
        } else {
            T.showShort(getActivity(), "请选择要删除的商品");
            return;
        }

        mCountEdit = 0;
        getData();
        updateEdit();

        ((MainActivity) getActivity()).updateGoods();
    }

}
