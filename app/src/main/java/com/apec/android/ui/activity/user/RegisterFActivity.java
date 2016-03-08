package com.apec.android.ui.activity.user;

import android.support.v4.app.Fragment;

import com.apec.android.ui.activity.SingleFragmentActivity;
import com.apec.android.ui.fragment.user.RegisterFFragment;
import com.apec.android.ui.fragment.user.RegisterFragment;

/**
 * 注册页面，用户信息填写
 * Created by Administrator on 2016/2/26.
 */
public class RegisterFActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return RegisterFFragment.newInstance();
    }

}
