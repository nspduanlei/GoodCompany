package com.apec.android.ui.activity.user;

import android.support.v4.app.Fragment;

import com.apec.android.ui.activity.SingleFragmentActivity;
import com.apec.android.ui.fragment.user.RegisterFragment;
import com.apec.android.ui.fragment.user.ShoppingCartFragment;

/**
 * 购物车
 * Created by Administrator on 2016/2/26.
 */
public class ShoppingCartActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return ShoppingCartFragment.newInstance();
    }

}
