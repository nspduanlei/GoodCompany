package com.apec.android.views.utils;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.apec.android.R;
import com.apec.android.config.Constants;
import com.apec.android.util.KeyBoardUtils;
import com.apec.android.util.T;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

/**
 * Created by duanlei on 2016/6/22.
 */
public class InputNumDialog {

    public interface InputCallback {
        void inputSure(int count);
    }

    InputCallback mInputCallback;
    Activity mActivity;
    String mCount;

    public InputNumDialog(Activity activity, String count, InputCallback inputCallback) {
        mActivity = activity;
        mCount = count;
        mInputCallback = inputCallback;
    }

    public void showInputNumDialog() {
        View dialogView = mActivity.getLayoutInflater()
                .inflate(R.layout.dialog_edit_count, null);
        //填充数据
        final EditText etGoodsCount =
                (EditText) dialogView.findViewById(R.id.et_goods_count);
        etGoodsCount.setText(mCount);

        ViewHolder viewHolder = new ViewHolder(dialogView);
        DialogPlus dialogPlus = new DialogPlus.Builder(mActivity)
                .setContentHolder(viewHolder)
                .setCancelable(true)
                .setGravity(DialogPlus.Gravity.CENTER)
                .setBackgroundColorResourceId(R.color.transparency)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()) {
                            case R.id.btn_add:
                                int addCount =
                                        Integer.valueOf(etGoodsCount.getText().toString());
                                etGoodsCount.setText(String.valueOf(addCount + 1));
                                break;
                            case R.id.btn_cut:
                                int cutCount =
                                        Integer.valueOf(etGoodsCount.getText().toString());
                                etGoodsCount.setText(String.valueOf(cutCount - 1));
                                break;

                            case R.id.tv_cancel:
                                KeyBoardUtils.closeKeybord(etGoodsCount, mActivity);
                                dialog.dismiss();
                                break;
                            case R.id.btn_sure:
                                KeyBoardUtils.closeKeybord(etGoodsCount, mActivity);
                                dialog.dismiss();

                                int sureCount =
                                        Integer.valueOf(etGoodsCount.getText().toString());

                                if (sureCount > Constants.MAX_GOODS_COUNT) {
                                    sureCount = Constants.MAX_GOODS_COUNT;
                                    etGoodsCount.setText(String.valueOf(Constants.MAX_GOODS_COUNT));
                                    T.showShort(mActivity, "商品数量不能超过" + Constants.MAX_GOODS_COUNT);
                                }

                                //TODO  提交数量
                                mInputCallback.inputSure(sureCount - Integer.valueOf(mCount));
                                break;
                            default:
                                break;
                        }
                    }
                })
                .create();
        dialogPlus.show();
    }
}
