package com.apec.android.mvp.presenters;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.transport.TransportInfo;
import com.apec.android.domain.usercase.GetTransportUseCase;
import com.apec.android.mvp.views.TransportView;
import com.apec.android.mvp.views.View;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/6/28.
 */
public class TransportPresenter implements Presenter {

    TransportView mView;

    GetTransportUseCase mGetTransportUseCase;
    Subscription mSubscription;

    @Inject
    public TransportPresenter(GetTransportUseCase getTransportUseCase) {
        mGetTransportUseCase = getTransportUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mView = (TransportView) v;
    }

    @Override
    public void onCreate() {
    }

    public void getTransports(int orderId) {
        mView.showLoadingView();
        mGetTransportUseCase.setData(orderId);
        mSubscription = mGetTransportUseCase.execute()
                .subscribe(this::onGetReceived, this::managerError);
    }

    private void managerError(Throwable throwable) {
        mView.hideLoadingView();
    }

    private void onGetReceived(TransportInfo transportInfo) {
        mView.hideLoadingView();
        if (transportInfo.getH().getCode() == 200) {
            mView.bindTransports(transportInfo.getB());
        }
    }
}
