package com.apec.android.ui.activity.order;

import android.support.v4.app.Fragment;

import com.apec.android.ui.activity.SingleFragmentActivity;
import com.apec.android.ui.fragment.order.MyOrdersFragment;
import com.apec.android.ui.fragment.user.RegisterFragment;

/**
 * 注册页面，用户信息填写
 * Created by Administrator on 2016/2/26.
 */
public class MyOrdersActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return MyOrdersFragment.newInstance();
    }

}
