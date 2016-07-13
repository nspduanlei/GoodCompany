package com.apec.android.views.activities;

import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.domain.entities.goods.SkuData;
import com.apec.android.domain.entities.transport.GoodsReceipt;
import com.apec.android.domain.entities.transport.ReceiptDefault;
import com.apec.android.injector.components.DaggerOrderComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.TrueOrderPresenter;
import com.apec.android.mvp.views.TrueOrderView;
import com.apec.android.util.T;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.adapter.listView.CommonAdapter;
import com.apec.android.views.adapter.listView.MyViewHolder;
import com.apec.android.views.fragments.GoodsCFragment;
import com.apec.android.views.utils.LoginUtil;
import com.apec.android.views.utils.ShopCartUtil;
import com.apec.android.views.widget.NoScrollListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/6/12.
 */
public class TrueOrderActivity extends BaseActivity implements TrueOrderView {

    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_user_phone)
    TextView mTvUserPhone;
    @BindView(R.id.tv_user_address)
    TextView mTvUserAddress;

    @BindView(R.id.tv_title_price)
    TextView mTvTitlePrice;
    @BindView(R.id.tv_title_send_coin)
    TextView mTvTitleSendCoin;
    @BindView(R.id.tv_title_total)
    TextView mTvTitleTotal;
    @BindView(R.id.tv_title_function)
    TextView mTvTitleFunction;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
//    @BindView(R.id.tv_arrived_time)
//    TextView mTvArrivedTime;

    @BindView(R.id.lv_goods)
    NoScrollListView mLvGoods;

    //private int mCount;
    private ArrayList<Integer> mSkuIds;
    private ArrayList<SkuData> mData;

    @Inject
    TrueOrderPresenter mPresenter;

    //boolean isCart;

    GoodsReceipt mGoodsReceipt;


    @Override
    protected void setUpContentView() {
        //如果没有登录去登录
        LoginUtil.gotoLogin(this);
        setContentView(R.layout.activity_true_order, R.string.true_order_title);
    }

    @Override
    protected void initUi() {
        //获取skuId和count
        mSkuIds = getIntent().getIntegerArrayListExtra("sku_ids");

        mData = new ArrayList<>();

//        if (mSkuIds.size() > 0) {
//            isCart = true;
//        }
        //订单金额
        Double amount = 0.0;

        for (Integer skuId : mSkuIds) {
            SkuData skuData = ShopCartUtil.querySkuById(String.valueOf(skuId));
            mData.add(skuData);
            amount = amount + Double.valueOf(skuData.getPrice()) * skuData.getCount();
        }

        mLvGoods.setAdapter(new CommonAdapter<SkuData>(this, mData, R.layout.order_goods_item_new) {
            @Override
            public void convert(MyViewHolder holder, SkuData skuData) {

                holder.setText(R.id.tv_goods_name, skuData.getSkuName())
                        .setText(R.id.tv_price,
                                String.format(getString(R.string.true_order_2),
                                        skuData.getPrice()))
                        .setText(R.id.tv_good_num,
                                String.format(getString(R.string.true_order_6),
                                        skuData.getCount()));

                if (skuData.getAttrValue() != null) {
                    holder.setText(R.id.tv_weight,
                            String.format(getString(R.string.true_order_3), skuData.getAttrName()));
                }

            }
        });

        mGoodsReceipt = GoodsCFragment.mGoodsReceipt;
        if (mGoodsReceipt != null) {
            bindGoodReceipt();
        }

        mTvTitlePrice.setText(String.format(getString(R.string.true_order_4), String.valueOf(amount)));
        mTvTitleTotal.setText(String.format(getString(R.string.true_order_5), String.valueOf(amount)));
    }

    private void bindGoodReceipt() {
        mTvUserName.setText(mGoodsReceipt.getName());
        mTvUserPhone.setText(mGoodsReceipt.getPhone());
        mTvUserAddress.setText(mGoodsReceipt.getAddrRes().getCity() +
                mGoodsReceipt.getAddrRes().getArea() +
                mGoodsReceipt.getAddrRes().getDetail());
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        DaggerOrderComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(application.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }

    @Override
    public void bindArriveTime(String time) {
        //mTvArrivedTime.setText(String.format(getString(R.string.true_order_1), time));
    }

    @Override
    public void onOrderSuccess() {
        //下单成功
        T.showShort(this, "下单成功");
        //删除购物车数据
        ShopCartUtil.deleteSkuList(mData);

        //发送广播刷新数据
        Intent mIntent = new Intent(MainActivity.ACTION_GOOD_UPDATE);
        sendBroadcast(mIntent);

//        setResult(Constants.RESULT_CODE_ORDER_SUCCESS);
//        finish();

        //跳转到我的订单
        Intent intentOrder = new Intent(this, OrdersActivity.class);
        startActivity(intentOrder);

        this.finish();
    }

    @Override
    public void onGetDefaultAddress(ReceiptDefault receiptDefault) {
        mGoodsReceipt = receiptDefault.getB();
        //默认地址设置成功
        bindGoodReceipt();
    }

    @Override
    public void addressNotMatch() {
        //您选择的收货地址和该商品所在地址不匹配
        T.showShort(this, "商品与您配送地址不匹配");
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
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @OnClick(R.id.btn_order)
    void onOrderClicked(View view) {
        JSONArray jsonArray = new JSONArray();
        try {
            for (int i = 0; i < mData.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("skuId", mData.get(i).getSkuId());
                jsonObject.put("num", mData.get(i).getCount());

                if (mGoodsReceipt != null) {
                    jsonObject.put("addressId", mGoodsReceipt.getAddressId());
                } else {
                    T.showShort(this, "地址为空");
                }
                jsonArray.put(i, jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //购物车下单
        mPresenter.cartOrder(jsonArray.toString());
    }

    @OnClick(R.id.rl_address)
    void onAddressClicked(View view) {
        Intent intent = new Intent(this, ManageAddressActivity.class);
        intent.putExtra(ManageAddressActivity.HAS_DEFAULT, true);
        intent.putExtra(ManageAddressActivity.IS_SELECT, true);
        startActivityForResult(intent, Constants.REQUEST_CODE_ADDR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LoginUtil.onActivityResult(requestCode, resultCode, this);
        if (requestCode == Constants.REQUEST_CODE_ADDR) {
            //选择地址
            if (resultCode == Constants.RESULT_CODE_SELECT_ADDRESS) {
                GoodsReceipt goodsReceipt = data.getParcelableExtra("address");
                mGoodsReceipt = goodsReceipt;
                bindGoodReceipt();
            }
            //设置了默认地址
            if (resultCode == Constants.RESULT_CODE_SET_DEFAULT_ADDR) {
                //TODO 获取默认地址
                mPresenter.getDefaultAddress();
            }
        }

        if (requestCode == Constants.REQUEST_CODE_LOGIN) {
            if (resultCode == Constants.RESULT_CODE_LOGIN_SUCCESS) {
                //TODO 登录成功 获取默认地址
                mPresenter.getDefaultAddress();
            }
        }
    }
}
