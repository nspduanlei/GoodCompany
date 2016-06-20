package com.apec.android.mvp.presenters;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.transport.ReceiptInfo;
import com.apec.android.domain.usercase.AddDeliveryUseCase;
import com.apec.android.mvp.views.AddAddressView;
import com.apec.android.mvp.views.View;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/6/1.
 */
public class AddAddressPresenter implements Presenter {

    AddDeliveryUseCase mAddDeliveryUseCase;
    AddAddressView mView;

    Subscription mAddSubscription;

    @Inject
    public AddAddressPresenter(AddDeliveryUseCase addDeliveryUseCase) {
        mAddDeliveryUseCase = addDeliveryUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mAddSubscription != null) {
            mAddSubscription.unsubscribe();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mView = (AddAddressView) v;
    }

    @Override
    public void onCreate() {

    }

    public void saveAddress(ReceiptInfo receiptInfo) {
        mView.showLoadingView();

        mAddDeliveryUseCase.setData(receiptInfo);
        mAddSubscription = mAddDeliveryUseCase.execute()
                .subscribe(this::onAddReceived, this::manageGoodsError);

    }

    private void manageGoodsError(Throwable throwable) {
        mView.hideLoadingView();
    }

    private void onAddReceived(NoBody noBody) {
        mView.hideLoadingView();

        if (noBody.getH().getCode() == 200) {
            mView.onAddSuccess();
        }
    }
}
