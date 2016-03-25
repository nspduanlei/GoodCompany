package com.apec.android.ui.activity.user;

import android.support.v4.app.Fragment;

import com.apec.android.ui.activity.SingleFragmentActivity;
import com.apec.android.ui.fragment.user.MyAccountFragment;
import com.apec.android.ui.fragment.user.SelectCityFragment;

/**
 * 地址选择
 * Created by Administrator on 2016/2/26.
 */
public class MyAccountActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return MyAccountFragment.newInstance();
    }

}
