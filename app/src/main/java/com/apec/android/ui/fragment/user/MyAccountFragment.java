package com.apec.android.ui.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.apec.android.R;
import com.apec.android.config.Constants;
import com.apec.android.domain.goods.Goods;
import com.apec.android.domain.user.Area;
import com.apec.android.domain.user.User;
import com.apec.android.ui.activity.goods.GoodsActivity;
import com.apec.android.ui.activity.user.RegisterFActivity;
import com.apec.android.ui.activity.user.ShoppingCartActivity;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.presenter.user.MyAccountPresenter;
import com.apec.android.ui.presenter.user.SelectCityPresenter;
import com.apec.android.util.SPUtils;
import com.apec.android.util.StringUtils;

import java.util.ArrayList;

/**
 * 我的帐号
 * Created by Administrator on 2016/2/26.
 */
public class MyAccountFragment extends BaseFragment<MyAccountPresenter.IView,
        MyAccountPresenter> implements MyAccountPresenter.IView, View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static MyAccountFragment newInstance() {
        MyAccountFragment fragment = new MyAccountFragment();
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my_account;
    }

    @Override
    protected MyAccountPresenter createPresenter() {
        return new MyAccountPresenter(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        mPresenter.obtainUserInfo();
    }

    private Button btnSave;
    private TextView edit;
    private EditText etShop, etUser, etPhone;
    private FrameLayout loading;

    private void initView(View view) {
        TextView title = (TextView) view.findViewById(R.id.tv_top_title);
        title.setText("我的帐号");
        view.findViewById(R.id.iv_back).setOnClickListener(this);
        view.findViewById(R.id.iv_shopping_cart).setOnClickListener(this);
        loading = (FrameLayout) view.findViewById(R.id.fl_loading);

        btnSave = (Button) view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);

        edit = (TextView) view.findViewById(R.id.tv_edit);
        edit.setOnClickListener(this);

        etShop = (EditText) view.findViewById(R.id.et_shop);
        etUser = (EditText) view.findViewById(R.id.et_user);
        etPhone = (EditText) view.findViewById(R.id.et_phone);
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
    public void getUserInfoBack(User user) {
        etPhone.setText(user.getPhone());
        etShop.setText(user.getShopName());
        etUser.setText(user.getName());
    }

    @Override
    public void updateSuccess() {
        //修改用户信息成功
        SPUtils.put(getActivity(), SPUtils.USER_NAME, etUser.getText().toString());
        Toast.makeText(getActivity(), "修改成功！", Toast.LENGTH_SHORT).show();

        Intent mIntent = new Intent(GoodsActivity.ACTION_USER_UPDATE);
        getActivity().sendBroadcast(mIntent);

        getActivity().finish();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private boolean isEdit;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.iv_shopping_cart:
                Intent intent = new Intent(getActivity(), ShoppingCartActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_edit:
                //进入编辑状态

                edit.setVisibility(View.INVISIBLE);

                isEdit = true;
                etPhone.setEnabled(true);
                etShop.setEnabled(true);
                etUser.setEnabled(true);
                edit.setEnabled(true);
                btnSave.setEnabled(true);
                break;

            case R.id.btn_save:
                //保存资料
                String userPhone = etPhone.getText().toString();
                String userShop = etShop.getText().toString();
                String userName = etUser.getText().toString();

                String checkPhone = StringUtils.checkMobile(userPhone);

                if (StringUtils.isNullOrEmpty(userShop)) {
                    Toast.makeText(getActivity(), "商品名不能为空!", Toast.LENGTH_SHORT).show();
                } else if (StringUtils.isNullOrEmpty(userName)) {
                    Toast.makeText(getActivity(), "用户名不能为空!", Toast.LENGTH_SHORT).show();
                } else if (!checkPhone.equals("")) {
                    Toast.makeText(getActivity(), checkPhone, Toast.LENGTH_SHORT).show();
                }

                User user = new User();
                user.setPhone(userPhone);
                user.setName(userName);
                user.setShopName(userShop);

                mPresenter.updateUserInfo(user);
                break;
        }
    }

    @Override
    public void needLogin() {
        Toast.makeText(getActivity(), R.string.please_login, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), RegisterFActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_LOGIN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_CODE_LOGIN:
                if (resultCode == Constants.RESULT_CODE_LOGIN_SUCCESS) {
                    mPresenter.obtainUserInfo();
                } else {
                    getActivity().finish();
                }
                break;
        }
    }

}
