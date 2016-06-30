package com.apec.android.mvp.views;

import com.apec.android.domain.entities.order.OrderList;

/**
 * Created by duanlei on 2016/6/2.
 */
public interface MyOrdersView extends View {
    void bindOrders(OrderList orderList);

    void cancelSuccess();

    void showEmpty();
    void hideEmpty();
}
