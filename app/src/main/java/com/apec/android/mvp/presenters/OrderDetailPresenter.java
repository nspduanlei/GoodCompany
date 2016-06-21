package com.apec.android.mvp.presenters;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.order.OrderBack;
import com.apec.android.domain.usercase.CancelOrderUseCase;
import com.apec.android.domain.usercase.GetOrderDetailUseCase;
import com.apec.android.mvp.views.OrderDetailView;
import com.apec.android.mvp.views.View;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/6/2.
 */
public class OrderDetailPresenter implements Presenter {

    GetOrderDetailUseCase mGetOrderDetailUseCase;


    OrderDetailView mView;
    Subscription mGetOrderSubscription;

    public int mOrderId;

    @Inject
    public OrderDetailPresenter(GetOrderDetailUseCase getOrderDetailUseCase) {
        mGetOrderDetailUseCase = getOrderDetailUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mGetOrderSubscription != null) {
            mGetOrderSubscription.unsubscribe();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mView = (OrderDetailView) v;
    }

    @Override
    public void onCreate() {
        mView.showLoadingView();
        mGetOrderDetailUseCase.setData(mOrderId);
        mGetOrderSubscription = mGetOrderDetailUseCase.execute()
                .subscribe(this::onGetOrderReceived, this::manageGetOrderError);
    }

    private void onGetOrderReceived(OrderBack orderBack) {
        mView.hideLoadingView();
        if (orderBack.getH().getCode() == 200) {
            mView.bindOrder(orderBack.getB());
        }
    }

    private void manageGetOrderError(Throwable throwable) {
        mView.hideLoadingView();
    }




}
