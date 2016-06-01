package com.apec.android.mvp.presenters;

import com.apec.android.domain.entities.transport.ReceiptInfo;
import com.apec.android.domain.usercase.AddDeliveryUseCase;
import com.apec.android.mvp.views.AddAddressView;
import com.apec.android.mvp.views.View;
import com.apec.android.views.activities.AddAddressActivity;

import javax.inject.Inject;

/**
 * Created by duanlei on 2016/6/1.
 */
public class AddAddressPresenter implements Presenter {

    AddDeliveryUseCase mAddDeliveryUseCase;
    AddAddressView mView;

    @Inject
    public AddAddressPresenter(AddDeliveryUseCase addDeliveryUseCase) {
        mAddDeliveryUseCase = addDeliveryUseCase;
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
        mView = (AddAddressView) v;
    }

    @Override
    public void onCreate() {

    }


    public void saveAddress(ReceiptInfo receiptInfo) {

    }
}
