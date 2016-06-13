package com.apec.android.views.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.config.Constants;
import com.apec.android.domain.entities.user.User;

import com.apec.android.util.SPUtils;
import com.apec.android.util.StringUtils;
import com.apec.android.views.activities.LoginActivity;
import com.apec.android.views.activities.ManageAddressActivity;
import com.apec.android.views.activities.MyOrdersActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.litepal.crud.DataSupport;

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

    User mUser;

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
        mUser = DataSupport.findFirst(User.class);

        mBtnLoginOut.setVisibility(View.VISIBLE);
        mBtnLogin.setVisibility(View.GONE);
        mTvUserC.setVisibility(View.VISIBLE);
        mTvUserC.setText(mUser.getName());

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
//        Intent intent = new Intent(mActivity, ShoppingCartActivity.class);
//        mActivity.startActivity(intent);
    }

    @OnClick(R.id.tv_my_account)
    void onMyAccountClick(View view) {
//        Intent intent = new Intent(mActivity, MyAccountActivity.class);
//        mActivity.startActivity(intent);
    }

    @OnClick(R.id.tv_my_order)
    void onMyOrderClick(View view) {
        Intent intent = new Intent(mActivity, MyOrdersActivity.class);
        mActivity.startActivity(intent);
    }

    @OnClick(R.id.tv_manage_address)
    void onManageAddressClick(View view) {
        Intent intent = new Intent(mActivity, ManageAddressActivity.class);
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

    public static void gotoLogin(Activity activity) {
        if (StringUtils.isNullOrEmpty(
                (String) SPUtils.get(activity, SPUtils.SESSION_ID, ""))) {
            //没有登录
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivityForResult(intent, Constants.REQUEST_CODE_LOGIN);
        }
    }

    public static boolean isLogin(Activity activity) {
        if (StringUtils.isNullOrEmpty(
                (String) SPUtils.get(activity, SPUtils.SESSION_ID, ""))) {
            return false;
        }
        return true;
    }

    public static boolean gotoLoginNew(Activity activity) {
        if (StringUtils.isNullOrEmpty(
                (String) SPUtils.get(activity, SPUtils.SESSION_ID, ""))) {
            //没有登录
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivityForResult(intent, Constants.REQUEST_CODE_LOGIN);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 登录页跳转后的回调
     * @param requestCode
     * @param resultCode
     */
    public static void onActivityResult(int requestCode, int resultCode, Activity activity) {
        if (requestCode == Constants.REQUEST_CODE_LOGIN) {
            if (resultCode != Constants.RESULT_CODE_LOGIN_SUCCESS) {
                activity.finish();
            }
        }
    }

}
