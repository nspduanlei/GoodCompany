package com.apec.android.ui.activity.order;

import android.support.v4.app.Fragment;

import com.apec.android.ui.activity.SingleFragmentActivity;
import com.apec.android.ui.fragment.order.OrderFragment;

/**
 * 点击购买商品下订单
 * Created by Administrator on 2016/2/26.
 */
public class OrderActivity extends SingleFragmentActivity {

    public final static String EXTRA_ORDER_ID = "order_id";

    @Override
    protected Fragment createFragment() {
        int order_id = getIntent().getIntExtra(EXTRA_ORDER_ID, 0);
        return OrderFragment.newInstance(order_id);
    }
}
