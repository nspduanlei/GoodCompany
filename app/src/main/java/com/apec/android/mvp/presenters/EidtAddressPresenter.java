package com.apec.android.mvp.presenters;

import com.apec.android.domain.usercase.AddDeliveryUseCase;
import com.apec.android.domain.usercase.UpdateDeliveryUseCase;
import com.apec.android.mvp.views.View;

import javax.inject.Inject;

/**
 * Created by duanlei on 2016/6/1.
 */
public class EidtAddressPresenter implements Presenter {

    UpdateDeliveryUseCase mUpdateDeliveryUseCase;

    @Inject
    public EidtAddressPresenter(UpdateDeliveryUseCase updateDeliveryUseCase) {
        mUpdateDeliveryUseCase = updateDeliveryUseCase;
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

    }

    @Override
    public void onCreate() {

    }
}
