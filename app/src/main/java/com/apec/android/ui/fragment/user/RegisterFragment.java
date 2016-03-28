package com.apec.android.ui.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apec.android.R;
import com.apec.android.domain.user.User;
import com.apec.android.ui.activity.goods.GoodsActivity;
import com.apec.android.ui.activity.user.RegisterFActivity;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.user.RegisterPresenter;
import com.apec.android.util.SPUtils;
import com.apec.android.util.StringUtils;
import com.orhanobut.dialogplus.DialogPlus;

/**
 * 注册流程，填写资料
 * Created by Administrator on 2016/2/26.
 */
public class RegisterFragment extends BaseFragment<RegisterPresenter.IView,
        RegisterPresenter> implements RegisterPresenter.IView, View.OnClickListener,
        SelectCityUtil.SelectArea {

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_register;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    /**
     * 表单数据
     */
    TextView tvArea;
    DialogPlus dialog;

    EditText etShop, etAreaDetail, etUserName;
    int userCityId, userAreaId, userRoadId;

    FrameLayout loading;

    private void initView(View view) {
        Button finish = (Button) view.findViewById(R.id.btn_finish);
        finish.setOnClickListener(this);

        ImageView back = (ImageView) view.findViewById(R.id.iv_back);
        back.setOnClickListener(this);

        TextView title = (TextView) view.findViewById(R.id.tv_top_title);
        title.setText("填写资料");

        LinearLayout selectArea = (LinearLayout) view.findViewById(R.id.ll_select_area);
        selectArea.setOnClickListener(this);

        //城市选择弹窗
        dialog = new SelectCityUtil(getActivity(), this).dialog;

        //表单数据
        tvArea = (TextView) view.findViewById(R.id.tv_area);
        etShop = (EditText) view.findViewById(R.id.et_shop);
        etAreaDetail = (EditText) view.findViewById(R.id.et_area_detail);
        etUserName = (EditText) view.findViewById(R.id.et_user_name);

        loading = (FrameLayout) view.findViewById(R.id.fl_loading);
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
    public boolean isReady() {
        return isAdded();
    }

    @Override
    public void submitSuccess() {
        Intent mIntent = new Intent(GoodsActivity.ACTION_USER_UPDATE);
        getActivity().sendBroadcast(mIntent);

        SPUtils.put(getActivity(), SPUtils.USER_NAME, etUserName.getText().toString());
        getActivity().setResult(ShoppingCartFragment.RESULT_CODE_LOGIN_SUCCESS);
        getActivity().finish();
    }

    @Override
    public void needLogin() {
        Intent intent = new Intent(getActivity(), RegisterFActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_finish:
                //验证
                String userShop = etShop.getText().toString();
                String userName = etUserName.getText().toString();
                int userCity = userCityId;
                int userArea = userAreaId;
                String userAddress = etAreaDetail.getText().toString();

                if (StringUtils.isNullOrEmpty(userShop) ||
                        StringUtils.isNullOrEmpty(userName) ||
                        userCity == 0 ||
                        userAreaId == 0 ||
                        StringUtils.isNullOrEmpty(userAddress)) {

                    Toast.makeText(getActivity(), "请填写完所用表单数据。。",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //提交数据
                    mPresenter.submitUserData(userShop, userName, userCity, userArea, userAddress);
                }

                break;
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.ll_select_area:
                dialog.show();
                break;
        }
    }

    @Override
    public void finish(String areaStr, int selCityId, int selAreaId, int selRoadId) {
        tvArea.setText(areaStr);
        userCityId = selCityId;
        userAreaId = selAreaId;
        userRoadId = selRoadId;
    }
}
