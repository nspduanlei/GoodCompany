package com.apec.android.injector.components;

import com.apec.android.domain.usercase.CancelOrderUseCase;
import com.apec.android.domain.usercase.CreateOrderUseCase;
import com.apec.android.domain.usercase.GetAllOrderUseCase;
import com.apec.android.domain.usercase.GetOrderDetailUseCase;
import com.apec.android.injector.Activity;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.injector.modules.OrderModule;
import com.apec.android.injector.modules.UserModule;
import com.apec.android.views.activities.ManageAddressActivity;
import com.apec.android.views.activities.MyOrdersActivity;
import com.apec.android.views.activities.OrderDetailActivity;

import dagger.Component;

/**
 * Created by duanlei on 2016/5/10.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {OrderModule.class, ActivityModule.class})
public interface OrderComponent extends ActivityComponent {

    void inject(MyOrdersActivity myOrdersActivity);
    void inject(OrderDetailActivity orderDetailActivity);

    CreateOrderUseCase createOrderUseCase();
    GetAllOrderUseCase getAllOrderUseCase();
    GetOrderDetailUseCase getOrderDetailUseCase();
    CancelOrderUseCase cancelOrderUseCase();
}
