package com.apec.android.views.utils;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
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

    EditText etGoodsCount;

    public void showInputNumDialog() {
        View dialogView = mActivity.getLayoutInflater()
                .inflate(R.layout.dialog_edit_count, null);
        //填充数据
        etGoodsCount = (EditText) dialogView.findViewById(R.id.et_goods_count);

        etGoodsCount.addTextChangedListener(new EditChangedListener());

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


    class EditChangedListener implements TextWatcher {
        private CharSequence temp;//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 10;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */

            int number  = Integer.valueOf(s.toString());

            if (number < 0) {
                etGoodsCount.setText("0");
                T.showShort(mActivity, "商品数量不能小于0");
            } else if (number > Constants.MAX_GOODS_COUNT) {
                etGoodsCount.setText(String.valueOf(Constants.MAX_GOODS_COUNT));
                T.showShort(mActivity, "商品数量不能超过" + Constants.MAX_GOODS_COUNT);
            }

//            editStart = mEditTextMsg.getSelectionStart();
//            editEnd = mEditTextMsg.getSelectionEnd();
//            if (temp.length() > charMaxNum) {
//                Toast.makeText(getApplicationContext(), "你输入的字数已经超过了限制！", Toast.LENGTH_LONG).show();
//                s.delete(editStart - 1, editEnd);
//                int tempSelection = editStart;
//                mEditTextMsg.setText(s);
//                mEditTextMsg.setSelection(tempSelection);
//            }
        }
    };

}
