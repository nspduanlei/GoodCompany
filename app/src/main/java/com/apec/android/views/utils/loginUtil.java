package com.apec.android.views.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.config.Constants;
import com.apec.android.util.SPUtils;
import com.apec.android.util.StringUtils;
import com.apec.android.views.activities.LoginActivity;
import com.apec.android.views.activities.MainActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

/**
 * Created by duanlei on 2016/5/11.
 */
public class LoginUtil {

    private static void loginOut(Activity activity, View btnView) {
        btnView.setVisibility(View.GONE);

        //退出登录
        SPUtils.put(activity, SPUtils.SESSION_ID, "");
        SPUtils.put(activity, SPUtils.IS_FIRST_LAUNCH, 1);

        UserUtil.clear();
        //ShopCartUtil.clear();

        Intent mIntent = new Intent(MainActivity.ACTION_USER_UPDATE);
        activity.sendBroadcast(mIntent);
        activity.setResult(Constants.RESULT_CODE_LOGIN_SUCCESS);
    }

    /**
     * 显示退出登录弹窗
     */
    public static void showLoginOutDialog(Activity activity, View btnView) {
        View dialogView = activity.getLayoutInflater()
                .inflate(R.layout.dialog_cancel_order, null);
        TextView title = (TextView) dialogView.findViewById(R.id.tv_title);
        title.setText(String.format("    %s", "确定退出登录吗？"));
        ViewHolder viewHolder = new ViewHolder(dialogView);
        DialogPlus dialog = new DialogPlus.Builder(activity)
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
                            loginOut(activity, btnView);
                            dialog1.dismiss();
                            break;
                        default:
                            break;
                    }
                })
                .create();
        dialog.show();
    }

    public static void gotoLogin(Activity activity) {
        if (StringUtils.isNullOrEmpty(
                (String) SPUtils.get(activity, SPUtils.SESSION_ID, ""))) {
            //没有登录
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivityForResult(intent, Constants.REQUEST_CODE_LOGIN);
        }
    }

    /**
     * 是否登录
     * @param activity
     * @return
     */
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

}
