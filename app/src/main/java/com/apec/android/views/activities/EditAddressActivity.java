package com.apec.android.views.activities;

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
import com.apec.android.injector.components.DaggerAddressComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.EditAddressPresenter;
import com.apec.android.mvp.views.EditAddressView;
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
    private int mSelCityId, mSelAreaId, mSelRoadId, mAddressId;

    @Override
    protected void setUpContentView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_address);
        ButterKnife.bind(this);
        setUpToolbar(R.string.edit_address_title, -1, MODE_BACK);
    }

    @Override
    protected void initUi() {
        GoodsReceipt goodsReceipt =
                getIntent().getParcelableExtra(ManageAddressActivity.EXTRA_EDIT_ADDRESS);
        mBinding.setGoodsReceipt(goodsReceipt);

        mSelCityId = Integer.valueOf(goodsReceipt.getAddrRes().getCityId());
        mSelAreaId = Integer.valueOf(goodsReceipt.getAddrRes().getAreaId());
        mAddressId = goodsReceipt.getAddressId();

        mSelectCityUtil.setData(this,
                goodsReceipt.getAddrRes().getCity(),
                goodsReceipt.getAddrRes().getArea(),
                null, mSelCityId, mSelAreaId, 0);
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
            ReceiptInfo receiptInfo = new ReceiptInfo(mAddressId, takeGoodsPhone, takeGoodsUser,
                    addreCity, addreAreacounty, addreDetailAddress);

            //添加地址
            mPresenter.updateAddress(receiptInfo);
        }

    }

    @OnClick(R.id.tv_select_area)
    void onSelectAreaClicked(View view) {
        dialog.show();
    }

    @Override
    public void selectCityFinish(String areaStr, int selCityId, int selAreaId, int selRoadId) {
        mTvSelectArea.setText(areaStr);
        mSelCityId = selCityId;
        mSelAreaId = selAreaId;
        mSelRoadId = selRoadId;
    }

    @Override
    public void onUpdateSuccess() {
        setResult(Constants.RESULT_CODE_UPDATE_ADDRESS_SUCCESS);
        this.finish();
    }
}
