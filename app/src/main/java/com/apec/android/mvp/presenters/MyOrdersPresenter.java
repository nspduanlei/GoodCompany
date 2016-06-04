package com.apec.android.mvp.presenters;

import com.apec.android.domain.entities.order.OrderListBack;
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
    MyOrdersView mView;
    Subscription mSubscription;

    @Inject
    public MyOrdersPresenter(GetAllOrderUseCase getAllOrderUseCase) {
        mGetAllOrderUseCase = getAllOrderUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mSubscription.unsubscribe();
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
        mView.showLoadingView();
        mSubscription = mGetAllOrderUseCase.execute()
                .subscribe(this::onGetAllReceived, this::managerGetAllError);
    }

    private void managerGetAllError(Throwable throwable) {
        mView.hideLoadingView();
    }

    private void onGetAllReceived(OrderListBack orderListBack) {
        mView.hideLoadingView();

        if (orderListBack.getH().getCode() == 200) {
            mView.onGetAllSuccess(orderListBack.getB());
        }
    }

    /**
     * 加载更多
     */
    public void onListEndReached() {

    }
}
