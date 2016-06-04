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
    CancelOrderUseCase mCancelOrderUseCase;

    OrderDetailView mView;
    Subscription mGetOrderSubscription;
    Subscription mCancelSubscription;

    public int mOrderId;

    @Inject
    public OrderDetailPresenter(GetOrderDetailUseCase getOrderDetailUseCase,
                                CancelOrderUseCase cancelOrderUseCase) {
        mGetOrderDetailUseCase = getOrderDetailUseCase;
        mCancelOrderUseCase = cancelOrderUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mGetOrderSubscription.unsubscribe();
        if (mCancelSubscription != null)
            mCancelSubscription.unsubscribe();
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

    public void cancelOrder(int orderId) {
        mView.showLoadingView();

        mCancelOrderUseCase.setData(orderId);
        mCancelSubscription = mCancelOrderUseCase.execute()
                .subscribe(this::onCancelReceived, this::manageCancelError);
    }

    private void manageCancelError(Throwable throwable) {
        mView.hideLoadingView();
    }

    private void onCancelReceived(NoBody noBody) {
        mView.hideLoadingView();

        if (noBody.getH().getCode() == 200) {
            mView.cancelSuccess();
        }
    }
}
