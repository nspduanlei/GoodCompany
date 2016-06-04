package com.apec.android.mvp.views;

import com.apec.android.domain.entities.order.Order;

/**
 * Created by duanlei on 2016/6/2.
 */
public interface OrderDetailView extends View {
    void bindOrder(Order order);
    void cancelSuccess();
}
