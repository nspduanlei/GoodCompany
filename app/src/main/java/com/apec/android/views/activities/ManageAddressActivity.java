package com.apec.android.views.activities;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.domain.entities.transport.GoodsReceipt;
import com.apec.android.injector.components.DaggerAddressComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.ManageAddressPresenter;
import com.apec.android.mvp.views.ManageAddressView;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.adapter.AddressListAdapter;
import com.apec.android.views.utils.LoginUtil;
import com.apec.android.views.view.AddressListClickListener;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/6/1.
 */
public class ManageAddressActivity extends BaseActivity implements ManageAddressView,
        AddressListClickListener {

    @Inject
    ManageAddressPresenter mPresenter;

    @BindView(R.id.rv_address)
    RecyclerView mRvAddress;

    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;

    AddressListAdapter mAdapter;
    ArrayList<GoodsReceipt> mGoodsReceipts= new ArrayList<>();

    public final static String EXTRA_EDIT_ADDRESS = "goodsReceipt";

    @Override
    protected void setUpContentView() {
        //是否登录
        LoginUtil.isLogin(this);
        setContentView(R.layout.activity_manage_address, R.string.manage_address_title);
    }

    @Override
    protected void initUi() {
        mRvAddress.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AddressListAdapter(mGoodsReceipts, this, this);
        mRvAddress.setAdapter(mAdapter);
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        DaggerAddressComponent.builder()
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
    public void showLoadingView() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public void bindAddress(ArrayList<GoodsReceipt> addressList) {
        mGoodsReceipts.clear();
        mGoodsReceipts.addAll(addressList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCBDefaultClick(int addressId) {
        mPresenter.setDefaultAddress(addressId);
    }

    @Override
    public void onDeleteClick(int addressId) {
        mPresenter.deleteAddress(addressId);
    }

    @Override
    public void onEditClick(GoodsReceipt goodsReceipt) {
        Intent intent = new Intent(this, EditAddressActivity.class);
        intent.putExtra(EXTRA_EDIT_ADDRESS, goodsReceipt);
        startActivityForResult(intent, Constants.REQUEST_CODE_UPDATE_ADDRESS);
    }

    @Override
    public void onElementClick(int position) {

    }

    @OnClick(R.id.btn_add_address)
    void onAddAddressClicked(View v) {
        //TODO 添加地址
        Intent intent = new Intent(this, AddAddressActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_UPDATE_ADDRESS);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LoginUtil.onActivityResult(requestCode, resultCode, this);
        if (requestCode == Constants.REQUEST_CODE_EDIT) {
            if (resultCode == Constants.RESULT_CODE_EDIT) {
                mPresenter.getAllAddress();
            }
        } else if (requestCode == Constants.REQUEST_CODE_UPDATE_ADDRESS) {
            if (resultCode == Constants.RESULT_CODE_UPDATE_ADDRESS_SUCCESS) {
                mPresenter.getAllAddress();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
