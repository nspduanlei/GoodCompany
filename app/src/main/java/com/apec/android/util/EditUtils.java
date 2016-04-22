package com.apec.android.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by duanlei on 2016/4/22.
 */
public class EditUtils implements TextWatcher {

    private EditText mEditText;
    private Context mContext;

    public EditUtils(EditText editText, Context context) {
        mEditText = editText;
        mContext = context;
    }

    private CharSequence temp;//监听前的文本
    private int editStart;//光标开始位置
    private int editEnd;//光标结束位置
    private final int charMaxNum = 6;

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
        editStart = mEditText.getSelectionStart();
        editEnd = mEditText.getSelectionEnd();
        if (temp.length() > charMaxNum) {
            Toast.makeText(mContext, "你输入的字数已经超过了限制！", Toast.LENGTH_LONG).show();
            s.delete(editStart - 1, editEnd);
            int tempSelection = editStart;
            mEditText.setText(s);
            mEditText.setSelection(tempSelection);
        }
    }

}
