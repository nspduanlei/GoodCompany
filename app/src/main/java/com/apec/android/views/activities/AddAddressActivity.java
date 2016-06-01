package com.apec.android.views.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.domain.entities.transport.ReceiptInfo;
import com.apec.android.injector.components.DaggerAddressComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.AddAddressPresenter;
import com.apec.android.mvp.views.AddAddressView;
import com.apec.android.util.StringUtils;
import com.apec.android.util.T;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.utils.SelectCityUtil;
import com.orhanobut.dialogplus.DialogPlus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/6/1.
 * 添加地址
 */
public class AddAddressActivity extends BaseActivity implements
        AddAddressView, SelectCityUtil.SelectArea {


    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.et_person)
    EditText mEtPerson;
    @BindView(R.id.et_phone_number)
    EditText mEtPhoneNumber;
    @BindView(R.id.tv_select_area)
    TextView mTvSelectArea;
    @BindView(R.id.et_area_detail)
    EditText mEtAreaDetail;

    @Inject
    AddAddressPresenter mPresenter;


    private DialogPlus dialog;
    private int mSelCityId, mSelAreaId, mSelRoadId;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_add_address, R.string.add_address_title);
    }

    @Override
    protected void initUi() {
        dialog = new SelectCityUtil(this, this).dialog;
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
    public void selectCityFinish(String areaStr, int selCityId, int selAreaId, int selRoadId) {
        mTvSelectArea.setText(areaStr);
        mSelCityId = selCityId;
        mSelAreaId = selAreaId;
        mSelRoadId = selRoadId;
    }

    @OnClick(R.id.tv_select_area)
    void onSelectAreaClicked(View view) {
        dialog.show();
    }

    @OnClick(R.id.btn_save)
    void onSubmitClicked(View view) {
        //手机号
        String takeGoodsPhone = mEtPhoneNumber.getText().toString();
        //收货人姓名
        String takeGoodsUser = mEtPerson.getText().toString();
        //市id
        int addreCity = mSelCityId;
        //区id
        int addreAreacounty = mSelAreaId;
        //详细地址
        String addreDetailAddress = mEtAreaDetail.getText().toString();

        String checkPhone = StringUtils.checkMobile(takeGoodsPhone);

        if (StringUtils.isNullOrEmpty(takeGoodsUser)) {

        } else if (!checkPhone.equals("")) {
            T.showShort(this, checkPhone);
        } else if (StringUtils.isNullOrEmpty(addreDetailAddress)) {

        } else if (addreCity == 0) {

        } else if (addreAreacounty == 0) {

        } else {
            ReceiptInfo receiptInfo = new ReceiptInfo(takeGoodsPhone, takeGoodsUser,
                    addreCity, addreAreacounty, addreDetailAddress);

            //添加地址
            mPresenter.saveAddress(receiptInfo);
        }
    }

    @Override
    public void showLoadingView() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mPbLoading.setVisibility(View.GONE);
    }
}
