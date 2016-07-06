package com.apec.android.mvp.presenters;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.order.OrderListBack;
import com.apec.android.domain.usercase.CancelOrderUseCase;
import com.apec.android.domain.usercase.GetAllOrderUseCase;
import com.apec.android.mvp.views.MyOrdersView;
import com.apec.android.mvp.views.View;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/6/2.
 */
public class MyOrdersPresenter implements Presenter {

    GetAllOrderUseCase mGetAllOrderUseCase;
    CancelOrderUseCase mCancelOrderUseCase;

    MyOrdersView mView;
    Subscription mSubscription;
    Subscription mCancelSubscription;

    @Inject
    public MyOrdersPresenter(GetAllOrderUseCase getAllOrderUseCase,
                             CancelOrderUseCase cancelOrderUseCase) {
        mGetAllOrderUseCase = getAllOrderUseCase;
        mCancelOrderUseCase = cancelOrderUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        if (mCancelSubscription != null) {
            mCancelSubscription.unsubscribe();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mView = (MyOrdersView) v;
    }

    @Override
    public void onCreate() {

    }

    public void getOrderList(int state) {
        mView.showLoadingView();
        mGetAllOrderUseCase.setState(state);
        mSubscription = mGetAllOrderUseCase.execute()
                .subscribe(this::onGetAllReceived, this::managerGetAllError);
    }

    private void managerGetAllError(Throwable throwable) {
        mView.hideLoadingView();
    }

    private void onGetAllReceived(OrderListBack orderListBack) {
        mView.hideLoadingView();

        if (orderListBack.getH().getCode() == 200) {
            if (orderListBack.getB().getData().size() > 0) {
                mView.hideEmpty();
                mView.bindOrders(orderListBack.getB());
            } else {
                mView.showEmpty();
            }
        }
    }

    /**
     * 加载更多
     */
    public void onListEndReached() {

    }

    public void cancelOrder(String orderId) {
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
