package com.apec.android.ui.fragment.user;

import android.content.Intent;
import android.opengl.ETC1;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.ui.activity.user.EditDataActivity;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.user.EditDataPresenter;
import com.apec.android.ui.presenter.user.RegisterPresenter;
import com.apec.android.util.StringUtils;
import com.orhanobut.dialogplus.DialogPlus;

/**
 *
 * Created by Administrator on 2016/2/26.
 */
public class EditDataFragment extends BaseFragment<EditDataPresenter.IView,
        EditDataPresenter> implements EditDataPresenter.IView, View.OnClickListener,
        SelectCityUtil.SelectArea {

    public static EditDataFragment newInstance() {
        EditDataFragment fragment = new EditDataFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private EditText etPerson, etPhoneNumber, etAreaDetail;

    private void initView(View view) {
        //收货信息
        selectArea = (TextView) view.findViewById(R.id.tv_select_area);
        selectArea.setOnClickListener(this);

        etPerson = (EditText) view.findViewById(R.id.et_person);
        etPhoneNumber = (EditText) view.findViewById(R.id.et_phone_number);
        etAreaDetail = (EditText) view.findViewById(R.id.et_area_detail);

        TextView title = (TextView) view.findViewById(R.id.tv_top_title);
        title.setText("添加地址");

        //城市选择弹窗
        dialog = new SelectCityUtil(getActivity(), this).dialog;

        view.findViewById(R.id.btn_save).setOnClickListener(this);

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

                if (StringUtils.isNullOrEmpty(takeGoodsPhone)) {

                } else if (StringUtils.isNullOrEmpty(takeGoodsUser)) {

                } else if (StringUtils.isNullOrEmpty(addreDetailAddress)) {

                } else if (addreCity == 0) {

                } else if (addreAreacounty == 0) {

                } else {
                    mPresenter.saveAddress(takeGoodsPhone,takeGoodsUser,
                            addreCity,addreAreacounty,addreDetailAddress);
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
