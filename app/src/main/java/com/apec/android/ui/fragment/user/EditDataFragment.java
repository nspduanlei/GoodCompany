package com.apec.android.ui.fragment.user;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.config.Constants;
import com.apec.android.domain.transport.GoodsReceipt;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.user.EditDataPresenter;
import com.apec.android.util.StringUtils;
import com.apec.android.util.T;
import com.orhanobut.dialogplus.DialogPlus;

/**
 * Created by Administrator on 2016/2/26.
 */
public class EditDataFragment extends BaseFragment<EditDataPresenter.IView,
        EditDataPresenter> implements EditDataPresenter.IView, View.OnClickListener,
        SelectCityUtil.SelectArea {

    public static EditDataFragment newInstance(GoodsReceipt goodsReceipt) {
        EditDataFragment fragment = new EditDataFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ManageAddrFragment.EXTRA_EDIT_ADDRESS,
                goodsReceipt);
        fragment.setArguments(bundle);
        return fragment;
    }

    //供编辑的收货地址
    private GoodsReceipt mGoodsReceipt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoodsReceipt = getArguments()
                .getParcelable(ManageAddrFragment.EXTRA_EDIT_ADDRESS);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_edit_data;
    }

    @Override
    protected EditDataPresenter createPresenter() {
        return new EditDataPresenter(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private TextView selectArea;
    private DialogPlus dialog;
    private int mSelCityId, mSelAreaId, mSelRoadId;
    private String mSelCity, mSelArea;

    private EditText etPerson, etPhoneNumber, etAreaDetail;

    //true编辑地址， false添加地址
    private boolean isEdit;

    private void initView(View view) {

        String titleStr;
        if (mGoodsReceipt != null) {
            isEdit = true;
            titleStr = "编辑地址";
        } else {
            titleStr = "添加地址";
        }

        //标题
        TextView title = (TextView) view.findViewById(R.id.tv_top_title);
        title.setText(titleStr);
        view.findViewById(R.id.iv_back).setOnClickListener(this);

        //收货信息
        selectArea = (TextView) view.findViewById(R.id.tv_select_area);
        selectArea.setOnClickListener(this);

        etPerson = (EditText) view.findViewById(R.id.et_person);
        etPhoneNumber = (EditText) view.findViewById(R.id.et_phone_number);
        etAreaDetail = (EditText) view.findViewById(R.id.et_area_detail);

        view.findViewById(R.id.btn_save).setOnClickListener(this);

        if (isEdit) {
            etPerson.setText(mGoodsReceipt.getName());
            etPhoneNumber.setText(mGoodsReceipt.getPhone());
            etAreaDetail.setText(mGoodsReceipt.getAddrRes().getDetail());

            mSelCity = mGoodsReceipt.getAddrRes().getCity();
            mSelArea =  mGoodsReceipt.getAddrRes().getArea();

            selectArea.setText(String.format("%s%s", mSelCity, mSelArea));
            mSelCityId = Integer.valueOf(mGoodsReceipt.getAddrRes().getCityId());
            mSelAreaId = Integer.valueOf(mGoodsReceipt.getAddrRes().getAreaId());

            //城市选择弹窗
            dialog = new SelectCityUtil(getActivity(), this, mSelCity,
                    mSelArea, null, mSelCityId, mSelAreaId, 0).dialog;
        } else {
            //城市选择弹窗
            dialog = new SelectCityUtil(getActivity(), this).dialog;
        }

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmptyCase() {

    }

    @Override
    public void showNoConnection() {

    }

    @Override
    public void hideNoConnection() {

    }

    @Override
    public void needLogin() {

    }

    @Override
    public void saveAddressSuccess() {
        getActivity().setResult(Constants.RESULT_CODE_EDIT);
        getActivity().finish();
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

            case R.id.tv_select_area:
                dialog.show();
                break;

            case R.id.btn_save:
                //保存地址

                //手机号
                String takeGoodsPhone = etPhoneNumber.getText().toString();
                //收货人姓名
                String takeGoodsUser = etPerson.getText().toString();
                //市id
                int addreCity = mSelCityId;
                //区id
                int addreAreacounty = mSelAreaId;
                //详细地址
                String addreDetailAddress = etAreaDetail.getText().toString();

                String checkPhone = StringUtils.checkMobile(takeGoodsPhone);

                if (StringUtils.isNullOrEmpty(takeGoodsUser)) {

                } else if (!checkPhone.equals("")) {
                    T.showShort(getActivity(), checkPhone);
                    //Toast.makeText(getActivity(), checkPhone, Toast.LENGTH_SHORT).show();
                } else if (StringUtils.isNullOrEmpty(addreDetailAddress)) {

                } else if (addreCity == 0) {

                } else if (addreAreacounty == 0) {

                } else {
                    if (isEdit) {
                        //编辑地址
                        mPresenter.updateAddress(mGoodsReceipt.getAddressId(),
                                takeGoodsPhone, takeGoodsUser,
                                addreCity, addreAreacounty, addreDetailAddress);

                    } else {
                        //添加地址
                        mPresenter.saveAddress(takeGoodsPhone, takeGoodsUser,
                                addreCity, addreAreacounty, addreDetailAddress);
                    }
                }

                break;
        }
    }

    @Override
    public void finish(String areaStr, int selCityId, int selAreaId, int selRoadId) {
        selectArea.setText(areaStr);
        mSelCityId = selCityId;
        mSelAreaId = selAreaId;
        mSelRoadId = selRoadId;
    }
}
