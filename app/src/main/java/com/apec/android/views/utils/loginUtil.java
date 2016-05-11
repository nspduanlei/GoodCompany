package com.apec.android.views.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.ui.activity.order.MyOrdersActivity;
import com.apec.android.ui.activity.user.ManageAddrActivity;
import com.apec.android.ui.activity.user.MyAccountActivity;
import com.apec.android.ui.activity.user.ShoppingCartActivity;
import com.apec.android.util.SPUtils;
import com.apec.android.util.StringUtils;
import com.apec.android.views.activities.LoginActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/5/11.
 */
public class LoginUtil {

    Activity mActivity;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_user_c)
    TextView mTvUserC;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_my_account)
    TextView mTvMyAccount;
    @BindView(R.id.tv_my_order)
    TextView mTvMyOrder;
    @BindView(R.id.tv_manage_address)
    TextView mTvManageAddress;
    @BindView(R.id.tv_my_shopping_cart)
    TextView mTvMyShoppingCart;
    @BindView(R.id.btn_login_out)
    Button mBtnLoginOut;

    public LoginUtil(Context context, View view) {
        mActivity = (Activity) context;
        ButterKnife.bind(this, view);
    }

    public void updateUser() {
        //验证是否登录
        if (StringUtils.isNullOrEmpty(
                (String) SPUtils.get(mActivity, SPUtils.SESSION_ID, ""))) {
            loginOut();
        } else {
            login();
        }
    }

    public void login() {
        mBtnLoginOut.setVisibility(View.VISIBLE);
        mBtnLogin.setVisibility(View.GONE);
        mTvUserC.setVisibility(View.VISIBLE);
        mTvUserName.setText((String) SPUtils.get(mActivity, SPUtils.USER_NAME, ""));
    }

    public void loginOut() {
        //没有登录
        mBtnLoginOut.setVisibility(View.GONE);
        mBtnLogin.setVisibility(View.VISIBLE);
        mTvUserC.setVisibility(View.GONE);
        mTvUserName.setText("请登录");
    }

    /**
     * 显示退出登录弹窗
     */
    private void showLoginOutDialog() {
        View dialogView = mActivity.getLayoutInflater()
                .inflate(R.layout.dialog_cancel_order, null);
        TextView title = (TextView) dialogView.findViewById(R.id.tv_title);
        title.setText(String.format("    %s", "确定退出登录吗？"));
        ViewHolder viewHolder = new ViewHolder(dialogView);
        DialogPlus dialog = new DialogPlus.Builder(mActivity)
                .setContentHolder(viewHolder)
                .setCancelable(true)
                .setGravity(DialogPlus.Gravity.CENTER)
                .setBackgroundColorResourceId(R.color.transparency)
                .setOnClickListener((dialog1, view) -> {
                    switch (view.getId()) {
                        case R.id.tv_cancel:
                            dialog1.dismiss();
                            break;
                        case R.id.tv_sure:
                            dialog1.dismiss();
                            //退出登录
                            SPUtils.clear(mActivity);
                            SPUtils.put(mActivity, SPUtils.IS_FIRST_LAUNCH, 1);
                            loginOut();
                            break;
                        default:
                            break;
                    }
                })
                .create();
        dialog.show();
    }

    @OnClick(R.id.tv_my_shopping_cart)
    void onMyShoppingCartClick(View view) {
        Intent intent = new Intent(mActivity, ShoppingCartActivity.class);
        mActivity.startActivity(intent);
    }

    @OnClick(R.id.tv_my_account)
    void onMyAccountClick(View view) {
        Intent intent = new Intent(mActivity, MyAccountActivity.class);
        mActivity.startActivity(intent);
    }

    @OnClick(R.id.tv_my_order)
    void onMyOrderClick(View view) {
        Intent intent = new Intent(mActivity, MyOrdersActivity.class);
        mActivity.startActivity(intent);
    }

    @OnClick(R.id.tv_manage_address)
    void onManageAddressClick(View view) {
        Intent intent = new Intent(mActivity, ManageAddrActivity.class);
        mActivity.startActivity(intent);
    }

    @OnClick(R.id.btn_login_out)
    void onLoginOutClick(View view) {
        showLoginOutDialog();
    }

    @OnClick(R.id.btn_login)
    void onLoginClick(View view) {
        Intent intent = new Intent(mActivity, LoginActivity.class);
        mActivity.startActivity(intent);
    }
}
