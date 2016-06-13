package com.apec.android.views.utils;

import android.os.Handler;
import android.os.Message;

import com.apec.android.R;
import com.apec.android.util.ColorPhrase;
import com.apec.android.views.activities.LoginActivity;

import java.lang.ref.WeakReference;

/**
 * Created by duanlei on 2016/5/11.
 */
public class LoginHandler extends Handler {

    public final static int HANDLER_TIMER = 1;

    WeakReference<LoginActivity> mActivity;

    public LoginHandler(LoginActivity activity) {
        mActivity = new WeakReference<>(activity);
    }
    @Override
    public void handleMessage(Message msg) {
        LoginActivity theActivity = mActivity.get();
        theActivity.hideLoadingView();

        switch (msg.what) {
            case HANDLER_TIMER: //倒计时
                if (theActivity.mDown > 0) {
//                    String downStr_other = String.format(
//                            theActivity.getString(R.string.hint_register_2), theActivity.mDown--);
//
//                    CharSequence chars = ColorPhrase.from(downStr_other).withSeparator("{}")
//                            .innerColor(0xFFE6454A).outerColor(0xFF666666).format();
//
//                    theActivity.mTvHintDown.setText(chars);


                    String downStr = String.format(
                            theActivity.getString(R.string.hint_login), theActivity.mDown--);
                    theActivity.mBtnGetCode.setText(downStr);

                } else {
                    theActivity.mBtnGetCode.setEnabled(true);
                    theActivity.mBtnGetCode.setText("验证");

                    //theActivity.mTvHintDown.setText("如果您还没收到短信，请尝试重新获取");
                }
                break;
        }
    }
}
