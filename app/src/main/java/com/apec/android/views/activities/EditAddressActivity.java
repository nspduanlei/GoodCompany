package com.apec.android.views.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.databinding.ActivityEditAddressBinding;
import com.apec.android.domain.entities.transport.GoodsReceipt;
import com.apec.android.domain.entities.transport.ReceiptInfo;
import com.apec.android.domain.entities.user.Address;
import com.apec.android.injector.components.DaggerAddressComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.EditAddressPresenter;
import com.apec.android.mvp.views.EditAddressView;
import com.apec.android.util.KeyBoardUtils;
import com.apec.android.util.StringUtils;
import com.apec.android.util.T;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.utils.SelectCityUtil;
import com.orhanobut.dialogplus.DialogPlus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/6/1.
 * 编辑地址
 */
public class EditAddressActivity extends BaseActivity implements EditAddressView,
        SelectCityUtil.SelectArea {

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
    EditAddressPresenter mPresenter;

    @Inject
    SelectCityUtil mSelectCityUtil;

    ActivityEditAddressBinding mBinding;

    private DialogPlus dialog;
    private int mSelCityId, mSelAreaId, mAddressId;
    private String mCityName, mAreaName;

    GoodsReceipt mGoodsReceipt;

    @Override
    protected void setUpContentView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_address);
        ButterKnife.bind(this);
        setUpToolbar(R.string.edit_address_title, -1, MODE_BACK);
    }

    @Override
    protected void initUi() {
        mGoodsReceipt =
                getIntent().getParcelableExtra(ManageAddressActivity.EXTRA_EDIT_ADDRESS);

        mBinding.setGoodsReceipt(mGoodsReceipt);

        mSelCityId = Integer.valueOf(mGoodsReceipt.getAddrRes().getCityId());
        mSelAreaId = Integer.valueOf(mGoodsReceipt.getAddrRes().getAreaId());
        mAddressId = mGoodsReceipt.getAddressId();

        mSelectCityUtil.setData(this,
                mGoodsReceipt.getAddrRes().getCity(),
                mGoodsReceipt.getAddrRes().getArea(),
                mSelCityId, mSelAreaId);

        dialog = mSelectCityUtil.dialog;
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
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
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
            T.showShort(this, "请填写收货人");
        } else if (!checkPhone.equals("")) {
            T.showShort(this, checkPhone);
        } else if (StringUtils.isNullOrEmpty(addreDetailAddress)) {
            T.showShort(this, "请填写详细地址");
        } else if (addreCity == 0) {
            T.showShort(this, "请选择地区");
        } else if (addreAreacounty == 0) {
            T.showShort(this, "请选择地区");
        } else {

            if (takeGoodsPhone.equals(mGoodsReceipt.getPhone())
                    && takeGoodsUser.equals(mGoodsReceipt.getName())
                    && addreCity == Integer.valueOf(mGoodsReceipt.getAddrRes().getCityId())
                    && addreAreacounty == Integer.valueOf(mGoodsReceipt.getAddrRes().getAreaId())
                    && addreDetailAddress.equals(mGoodsReceipt.getAddrRes().getDetail())) {
                T.showShort(this, "地址没有修改");
            } else {

                mGoodsReceipt.setName(takeGoodsUser);
                mGoodsReceipt.setPhone(takeGoodsPhone);

                Address address = new Address();
                address.setAreaId(String.valueOf(addreAreacounty));
                address.setCityId(String.valueOf(addreCity));
                address.setArea(mAreaName);
                address.setCity(mCityName);
                address.setDetail(addreDetailAddress);

                mGoodsReceipt.setAddrRes(address);

                ReceiptInfo receiptInfo = new ReceiptInfo(mAddressId, takeGoodsPhone, takeGoodsUser,
                        addreCity, addreAreacounty, addreDetailAddress);

                //修改地址
                mPresenter.updateAddress(receiptInfo);
            }
        }

    }

    @OnClick(R.id.tv_select_area)
    void onSelectAreaClicked(View view) {
        dialog.show();
        KeyBoardUtils.closeKeybord(mEtPerson, this);
        KeyBoardUtils.closeKeybord(mEtPhoneNumber, this);
        KeyBoardUtils.closeKeybord(mEtAreaDetail, this);
    }

    @Override
    public void selectCityFinish(String cityName, String areaName, int selCityId, int selAreaId) {
        mTvSelectArea.setText(cityName + areaName);
        mSelCityId = selCityId;
        mSelAreaId = selAreaId;
        mCityName = cityName;
        mAreaName = areaName;
    }

    @Override
    public void onUpdateSuccess() {

        Intent mIntent = new Intent(MainActivity.ADDRESS_EDIT_ACTION);
        mIntent.putExtra("address", mGoodsReceipt);
        sendBroadcast(mIntent);
        Intent mIntentTrue = new Intent(TrueOrderActivity.ACTION_ADDRESS_UPDATE);
        mIntentTrue.putExtra("address", mGoodsReceipt);
        sendBroadcast(mIntentTrue);

        setResult(Constants.RESULT_CODE_UPDATE_ADDRESS_SUCCESS);
        this.finish();
    }
}
