package com.apec.android.mvp.presenters;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.transport.ArrivalTime;
import com.apec.android.domain.usercase.CreateOneOrderUseCase;
import com.apec.android.domain.usercase.CreateOrderUseCase;
import com.apec.android.domain.usercase.GetArriveTimeUseCase;
import com.apec.android.mvp.views.TrueOrderView;
import com.apec.android.mvp.views.View;
import com.apec.android.views.activities.TrueOrderActivity;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/6/13.
 */
public class TrueOrderPresenter implements Presenter {

    TrueOrderView mView;

    GetArriveTimeUseCase mGetArriveTimeUseCase;
    //批量下单
    CreateOrderUseCase mCreateOrderUseCase;
    CreateOneOrderUseCase mCreateOneOrderUseCase;

    Subscription mSubscriptionArriveTime;
    Subscription mSubscriptionCreateOrder;
    Subscription mSubscriptionCreateOrderOne;

    @Inject
    public TrueOrderPresenter(GetArriveTimeUseCase getArriveTimeUseCase,
                              CreateOrderUseCase createOrderUseCase,
                              CreateOneOrderUseCase createOneOrderUseCase) {
        mGetArriveTimeUseCase = getArriveTimeUseCase;
        mCreateOrderUseCase = createOrderUseCase;
        mCreateOneOrderUseCase = createOneOrderUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mSubscriptionArriveTime != null) {
            mSubscriptionArriveTime.unsubscribe();
        }
        if (mSubscriptionCreateOrderOne != null) {
            mSubscriptionCreateOrderOne.unsubscribe();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mView = (TrueOrderView) v;
    }

    @Override
    public void onCreate() {
        getArriveTime();
    }

    public void getArriveTime() {
        mView.showLoadingView();
        mSubscriptionArriveTime = mGetArriveTimeUseCase.execute()
                .subscribe(this::onArriveTimeReceived, this::managerError);
    }

    private void managerError(Throwable throwable) {
        mView.hideLoadingView();
    }

    private void onArriveTimeReceived(ArrivalTime arrivalTime) {
        mView.hideLoadingView();
        if (arrivalTime.getH().getCode() == 200) {
            mView.bindArriveTime(arrivalTime.getB().getTime());
        }
    }

    //快速下单
    public void fastOrder(int skuId, int addressId, int num) {
        mView.showLoadingView();
        mCreateOneOrderUseCase.setData(skuId, addressId, num);
        mSubscriptionCreateOrderOne = mCreateOneOrderUseCase.execute()
                .subscribe(this::onOrderReceived, this::managerError);
    }

    private void onOrderReceived(NoBody noBody) {
        mView.hideLoadingView();
        if (noBody.getH().getCode() == 200) {
            mView.onOrderSuccess();
        }
    }

    //购物车下单
    public void cartOrder(String json) {
        mView.showLoadingView();
        mCreateOrderUseCase.setData(json);
        mCreateOrderUseCase.execute()
                .subscribe(this::onOrderReceived, this::managerError);
    }
}
