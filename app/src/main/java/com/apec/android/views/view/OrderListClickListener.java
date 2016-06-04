package com.apec.android.views.view;

/**
 * Created by duanlei on 2016/5/10.
 */
public interface OrderListClickListener {
    void onElementClick(int position);
    void onGoodsItemClick(int goods);
    void onDetailClick(int orderId);
}
