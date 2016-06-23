package com.apec.android.views.utils;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.util.KeyBoardUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

/**
 * Created by duanlei on 2016/6/22.
 * 警告弹窗
 */
public class AlertDialog {

    public interface InputCallback {
        void inputSure();
    }

    InputCallback mInputCallback;
    Activity mActivity;
    String mContent;

    public AlertDialog(Activity activity, String content, InputCallback inputCallback) {
        mActivity = activity;
        mContent = content;
        mInputCallback = inputCallback;
    }

    public void showAlertDialog() {
        View dialogView = mActivity.getLayoutInflater()
                .inflate(R.layout.dialog_alert, null);
        //填充数据
        TextView tvTitle =
                (TextView) dialogView.findViewById(R.id.tv_title);
        tvTitle.setText(mContent);

        ViewHolder viewHolder = new ViewHolder(dialogView);
        DialogPlus dialogPlus = new DialogPlus.Builder(mActivity)
                .setContentHolder(viewHolder)
                .setCancelable(true)
                .setGravity(DialogPlus.Gravity.CENTER)
                .setBackgroundColorResourceId(R.color.transparency)
                .setOnClickListener((dialog, view) -> {
                    switch (view.getId()) {
                        case R.id.tv_cancel:
                            dialog.dismiss();
                            break;
                        case R.id.btn_sure:
                            dialog.dismiss();
                            mInputCallback.inputSure();
                            break;
                        default:
                            break;
                    }
                })
                .create();
        dialogPlus.show();
    }
}
