package com.apec.android.ui.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apec.android.R;
import com.apec.android.config.Constants;
import com.apec.android.domain.transport.GoodsReceipt;
import com.apec.android.domain.user.ShopCart;
import com.apec.android.domain.user.Skus;
import com.apec.android.ui.activity.goods.GoodsActivity;
import com.apec.android.ui.activity.user.ManageAddrActivity;
import com.apec.android.ui.activity.user.RegisterFActivity;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.MyViewHolder;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.presenter.user.ShoppingCartPresenter;
import com.apec.android.util.DialogUtils;
import com.apec.android.util.L;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import org.litepal.util.Const;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * 购物车
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
        mPresenter.obtainShopCart();
    }

    private ArrayList<Skus> mData;
    private BaseAdapter mAdapter;
    private FrameLayout loading;
    private LinearLayout empty;

    //全选复选框
    private CheckBox allSelect;
    //合计金额
    private TextView totalPrices;
    //去结算
    private Button gotoPay;

    //购物车中的商品数量
    private int mCount;
    private Double mPrice;

    //private TextView editBtn;

    //是否是编辑状态
    //private boolean isEdit;

    //收货地址显示
    private RelativeLayout addressShow;

    //购物车编辑用
    //private ArrayList<Skus> mDataForEdit;

    private void initView(View view) {
        TextView title = (TextView) view.findViewById(R.id.tv_top_title);
        title.setText("购物车");

        view.findViewById(R.id.iv_back).setOnClickListener(this);
        loading = (FrameLayout) view.findViewById(R.id.fl_loading);
        empty = (LinearLayout) view.findViewById(R.id.ll_empty);
        TextView hint = (TextView) empty.findViewById(R.id.tv_hint);
        hint.setText("您的购物车是空的，快去商城看看吧");
        Button emptyGoto = (Button) empty.findViewById(R.id.btn_goto);
        emptyGoto.setText("去看看");
        emptyGoto.setOnClickListener(this);

        allSelect = (CheckBox) view.findViewById(R.id.cb_all_select);
        allSelect.setChecked(true);

        //编辑购物车
//        editBtn = (TextView) view.findViewById(R.id.tv_edit);
//        editBtn.setOnClickListener(this);

        addressShow = (RelativeLayout) view.findViewById(R.id.rl_address_show);

        //收货信息
        view.findViewById(R.id.btn_update).setOnClickListener(this);

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

        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<Skus>(getActivity(), mData,
                R.layout.shopping_cart_item) {
            @Override
            public void convert(final MyViewHolder holder, final Skus skus) {
                holder.setText(R.id.tv_count, String.valueOf(skus.getCount()))
                        .setText(R.id.tv_goods_name, skus.getSku().getSkuName())
                        .setText(R.id.tv_price, String.format("￥%s", skus.getSku().getPrice()));

                if (skus.isSelectCart() && skus.getSku().getStatus() == 1) {
                    holder.setChecked(R.id.cb_select, true);
                } else {
                    holder.setChecked(R.id.cb_select, false);
                }

                if (skus.getSku().getStatus() == 2) {
                    //该商品已下架
                    holder.setVisibility(R.id.fl_sold_out, View.VISIBLE);
                } else {
                    holder.setVisibility(R.id.fl_sold_out, View.GONE);
                }

                //商品数量编辑
                holder.setOnClickLister(R.id.tv_count, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        View dialogView = getActivity().getLayoutInflater()
                                .inflate(R.layout.dialog_edit_count, null);
                        //填充数据
                        final EditText etGoodsCount =
                                (EditText) dialogView.findViewById(R.id.et_goods_count);
                        etGoodsCount.setText(String.valueOf(mData.get(holder.getMPosition()).getCount()));
                        ViewHolder viewHolder = new ViewHolder(dialogView);
                        DialogPlus dialogPlus = new DialogPlus.Builder(getActivity())
                                .setContentHolder(viewHolder)
                                .setCancelable(true)
                                .setGravity(DialogPlus.Gravity.CENTER)
                                .setBackgroundColorResourceId(R.color.transparency)
                                .setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(DialogPlus dialog, View view) {
                                        switch (view.getId()) {

                                            case R.id.btn_add:
                                                int count_add = Integer.valueOf(
                                                        etGoodsCount.getText().toString());
                                                if (count_add == 100) {
                                                    Toast.makeText(getActivity(), "最多只能购买100包",
                                                            Toast.LENGTH_SHORT).show();
                                                } else {
                                                    etGoodsCount.setText(String.valueOf(count_add + 1));
                                                }
                                                break;
                                            case R.id.btn_cut:
                                                int count_cut = Integer.valueOf(
                                                        etGoodsCount.getText().toString());
                                                if (count_cut > 0) {
                                                    etGoodsCount.setText(String.valueOf(count_cut - 1));
                                                }
                                                break;

                                            case R.id.tv_cancel:
                                                dialog.dismiss();
                                                break;
                                            case R.id.btn_sure:
                                                dialog.dismiss();

                                                int count = 0;
                                                if (!etGoodsCount.getText().toString().equals("")) {
                                                    count = Integer.valueOf(
                                                            etGoodsCount.getText().toString());
                                                }

                                                if (count > 100) {
                                                    count = 100;
                                                    Toast.makeText(getActivity(), "最多只能购买100包",
                                                            Toast.LENGTH_SHORT).show();
                                                }

                                                int addCount =
                                                        count - mData.get(holder.getMPosition())
                                                                .getCount();

                                                mPresenter.updateCartItem(mData
                                                                .get(holder.getMPosition())
                                                                .getSku().getId(),
                                                        addCount);

                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                })
                                .create();
                        dialogPlus.show();
                    }
                });

                //商品数量加1
                holder.setOnClickLister(R.id.btn_add, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getMPosition();
                        L.e("test001", "------>" + position);
                        int count = mData.get(position).getCount() + 1;
                        mPresenter.updateCartItem(mData.get(position).getSku().getId(),
                                1);

                        mData.get(position).setCount(count);
                        mAdapter.notifyDataSetChanged();
                    }
                });

                //商品数量减1
                holder.setOnClickLister(R.id.btn_cut, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getMPosition();
                        L.e("test001", "------>" + position);

                        int count = mData.get(position).getCount() - 1;
                        mPresenter.updateCartItem(mData.get(position).getSku().getId(),
                                -1);

                        mData.get(position).setCount(count);
                        mAdapter.notifyDataSetChanged();


                    }
                });

                holder.setOnCheckChangeLister(R.id.cb_select,
                        new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            mCount++;
                            mPrice = mPrice + Double.valueOf(
                                    mData.get(holder.getMPosition()).getSku().getPrice()) *
                                    mData.get(holder.getMPosition()).getCount();

                            //如果商品被全部选择了，则将全选复选框设为选择状态，反正则异然
                            if (mCount == mData.size()) {
                                allSelect.setChecked(true);
                            }
                        } else {
                            mCount--;
                            mPrice = mPrice - Double.valueOf(
                                    mData.get(holder.getMPosition()).getSku().getPrice()) *
                                    mData.get(holder.getMPosition()).getCount();

                            allSelect.setChecked(false);
                        }
                        //填充购物车数量
                        if (addressId != 0) {
                            gotoPay.setText(String.format(getString(R.string.goto_pay_btn), mCount));
                        }

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
        empty.setVisibility(View.VISIBLE);
    }


    private int addressId;

    @Override
    public void updateNumSuccess() {
        mPresenter.obtainShopCart();
    }

    @Override
    public void obtainOrderSuccess() {
        //下单成功
        Toast.makeText(getActivity(), "创建订单成功！", Toast.LENGTH_SHORT).show();
        mPresenter.obtainShopCart();
    }

    private boolean isGetAddressed;

    @Override
    public void obtainDefaultSuccess(GoodsReceipt goodsReceipt) {
        isGetAddressed = true;
        if (goodsReceipt == null) {
            //不存在默认收货地址
            addressId = 0;
            gotoPay.setText("设置地址");
            addressShow.setVisibility(View.GONE);
        } else {
            addressId = goodsReceipt.getAddressId();
            addressShow.setVisibility(View.VISIBLE);

            TextView address = (TextView) addressShow.findViewById(R.id.tv_address);
            address.setText(goodsReceipt.getAddrRes().getDetail());
            TextView userInfo = (TextView) addressShow.findViewById(R.id.tv_user_info);
            userInfo.setText(String.format("%s   %s", goodsReceipt.getName(),
                    goodsReceipt.getPhone()));

            gotoPay.setText(String.format(getString(R.string.goto_pay_btn), mCount));
        }
    }

    @Override
    public void obtainCartSuccess(ShopCart shopCart) {
        if (!isGetAddressed) {
            mPresenter.obtainDefaultAddress();
        }

        mData.clear();
        mData.addAll(shopCart.getSkus());
        mAdapter.notifyDataSetChanged();

        mCount = shopCart.getTotal();
        mPrice = Double.valueOf(shopCart.getTotalPrice());
        totalPrices.setText(String.format(getString(R.string.total_price_cart),
                shopCart.getTotalPrice()));

        if (addressId != 0) {
            gotoPay.setText(String.format(getString(R.string.goto_pay_btn), mCount));
        }
    }

    @Override
    public void needLogin() {
        Toast.makeText(getActivity(), R.string.please_login, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), RegisterFActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_LOGIN);
    }

    @Override
    public void needLoginPay() {
        Toast.makeText(getActivity(), R.string.please_login, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), RegisterFActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_LOGIN_PAY);
    }

    @Override
    public boolean isReady() {
        return isAdded();
    }

    //购买商品skuId拼接
    StringBuffer sbSkus = new StringBuffer();
    DialogPlus mDialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.btn_update:
                Intent intent = new Intent(getActivity(), ManageAddrActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_ADDR);
                break;
//            case R.id.tv_edit:
//                if (isEdit) {
//                    //退出编辑状态
//                    isEdit = false;
//
//                    editBtn.setText("编辑");
//                    totalPrices.setVisibility(View.VISIBLE);
//                    gotoPay.setText(String.format(getString(R.string.goto_pay_btn), mCount));
//
//                } else {
//                    //进入编辑状态
//                    isEdit = true;
//                    editBtn.setText("完成");
//                    totalPrices.setVisibility(View.GONE);
//
//                    gotoPay.setText("删除");
//                }
//                break;

            case R.id.btn_goto_pay:
                if (addressId != 0) {
                    mPayData = new ArrayList<>();
                    for (Skus item : mData) {
                        if (item.isSelectCart()) {
                            mPayData.add(item);
                            sbSkus.append(item.getSku().getId());
                            sbSkus.append(",");
                        }
                    }

                    sbSkus.deleteCharAt(sbSkus.lastIndexOf(","));

                    View dialogView = getActivity().getLayoutInflater()
                            .inflate(R.layout.dialog_pay, null);

                    initViewDialog(dialogView);

                    ViewHolder viewHolder = new ViewHolder(dialogView);

                    mDialog = new DialogPlus.Builder(getActivity())
                            .setContentHolder(viewHolder)
                            .setCancelable(true)
                            .setGravity(DialogPlus.Gravity.CENTER)
                            .setBackgroundColorResourceId(R.color.transparency)
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(DialogPlus dialog, View view) {
                                    switch (view.getId()) {
                                        case R.id.iv_cancel:
                                            dialog.dismiss();
                                            break;
                                        case R.id.btn_pay:
                                            mDialog.dismiss();
                                            //TODO 货到付款
                                            //下单
                                            mPresenter.createOrder(sbSkus.toString(), addressId);
                                            sbSkus.delete(0, sbSkus.toString().length());
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            })
                            .create();
                    mDialog.show();
                } else {
                    //没有选择收货地址
                    Intent intent1 = new Intent(getActivity(), ManageAddrActivity.class);
                    startActivityForResult(intent1, Constants.REQUEST_CODE_ADDR);
                }
                break;

            case R.id.btn_goto:
                //购物车是空的
                Intent intent1 = new Intent(getActivity(), GoodsActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private ArrayList<Skus> mPayData;

    private void initViewDialog(View view) {
        TextView money = (TextView) view.findViewById(R.id.tv_money);
        money.setText("￥" + mPrice);

        ListView payList = (ListView) view.findViewById(R.id.lv_goods);
        payList.setAdapter(new CommonAdapter<Skus>(getActivity(), mPayData,
                R.layout.pay_list_item) {
            @Override
            public void convert(MyViewHolder holder, Skus skus) {
                holder.setText(R.id.tv_goods_name, String.format("   %s",
                        skus.getSku().getSkuName()))
                        .setText(R.id.tv_goods_price, String.format("%d x ￥%s",
                                skus.getCount(), skus.getSku().getPrice()));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_CODE_LOGIN:
                if (resultCode == Constants.RESULT_CODE_LOGIN_SUCCESS) {
                    mPresenter.obtainShopCart();
                } else {
                    getActivity().finish();
                }

                break;
            case Constants.REQUEST_CODE_ADDR: //更改收货地址
                if (resultCode == Constants.RESULT_CODE_ADDRESS_SUCCESS) {
                    mPresenter.obtainDefaultAddress();
                }
                break;

            case Constants.REQUEST_CODE_LOGIN_PAY:
                if (resultCode == Constants.RESULT_CODE_LOGIN_SUCCESS) {
                    mPresenter.createOrder(sbSkus.toString(), addressId);
                }
                break;
        }
    }
}
