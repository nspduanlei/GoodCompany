package com.apec.android.mvp.presenters;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.transport.ReceiptInfo;
import com.apec.android.domain.usercase.UpdateDeliveryUseCase;
import com.apec.android.mvp.views.EditAddressView;
import com.apec.android.mvp.views.View;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/6/1.
 */
public class EditAddressPresenter implements Presenter {

    UpdateDeliveryUseCase mUpdateDeliveryUseCase;
    EditAddressView mView;
    Subscription mSubscription;

    @Inject
    public EditAddressPresenter(UpdateDeliveryUseCase updateDeliveryUseCase) {
        mUpdateDeliveryUseCase = updateDeliveryUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mSubscription != null)
            mSubscription.unsubscribe();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mView = (EditAddressView) v;
    }

    @Override
    public void onCreate() {

    }

    public void updateAddress(ReceiptInfo receiptInfo) {
        mView.showLoadingView();

        mUpdateDeliveryUseCase.setData(receiptInfo);
        mSubscription = mUpdateDeliveryUseCase.execute()
                .subscribe(this::onUpdateReceived, this::manageAddressError);
    }

    private void manageAddressError(Throwable throwable) {
        mView.hideLoadingView();
    }

    private void onUpdateReceived(NoBody noBody) {
        mView.hideLoadingView();
        if (noBody.getH().getCode() == 200) {
            mView.onUpdateSuccess();
        }
    }
}
