package com.apec.android.views.view;

import com.apec.android.domain.entities.order.Order;

/**
 * Created by duanlei on 2016/5/10.
 */
public interface OrderListClickListener {
    void onElementClick(int position);
    void onGoodsItemClick(int goods);
    void onDetailClick(int orderId);

    //取消订单
    void onCancelOrder(Order order);
}
