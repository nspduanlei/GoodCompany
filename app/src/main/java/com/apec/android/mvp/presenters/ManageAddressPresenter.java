package com.apec.android.mvp.presenters;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.transport.GoodsReceipt;
import com.apec.android.domain.entities.transport.ReceiptList;
import com.apec.android.domain.usercase.DelAddressUseCase;
import com.apec.android.domain.usercase.GetAllAddressUseCase;
import com.apec.android.domain.usercase.SetDefaultAddressUseCase;
import com.apec.android.mvp.views.ManageAddressView;
import com.apec.android.mvp.views.View;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by duanlei on 2016/6/1.
 */
public class ManageAddressPresenter implements Presenter {

    ManageAddressView mView;
    GetAllAddressUseCase mGetAllUseCase;
    SetDefaultAddressUseCase mSetDefaultUseCase;
    DelAddressUseCase mDelAddressUseCase;

    private Subscription mGetAllSubscription;
    private Subscription mSetDefaultSubscription;
    private Subscription mDelAddressSubscription;


    @Inject
    public ManageAddressPresenter(GetAllAddressUseCase getAllAddressUseCase,
                                  SetDefaultAddressUseCase setDefaultUseCase,
                                  DelAddressUseCase delAddressUseCase) {
        mGetAllUseCase = getAllAddressUseCase;
        mSetDefaultUseCase = setDefaultUseCase;
        mDelAddressUseCase = delAddressUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mGetAllSubscription.unsubscribe();
        if (mSetDefaultSubscription != null)
            mSetDefaultSubscription.unsubscribe();
        if (mDelAddressSubscription != null)
            mDelAddressSubscription.unsubscribe();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mView = (ManageAddressView) v;
    }

    @Override
    public void onCreate() {
        getAllAddress();
    }
    /********************************请求数据********************************/

    public void getAllAddress() {
        mView.showLoadingView();
        mGetAllSubscription =
                mGetAllUseCase.execute()
                        .subscribe(this::onGetAddressReceived, this::manageGetAddressError);
    }

    public void setDefaultAddress(int addressId) {
        mView.showLoadingView();

        mSetDefaultUseCase.setData(addressId);
        mSetDefaultSubscription = mSetDefaultUseCase.execute()
                .flatMap(noBody -> {
                    if (noBody.getH().getCode() != 200) {
                        //TODO 设置不成功
                    }
                    return mGetAllUseCase.execute();
                })
                .subscribe(this::onGetAddressReceived, this::manageGetAddressError);
    }

    public void deleteAddress(int addressId) {
        mView.showLoadingView();

        mDelAddressUseCase.setData(addressId);
        mDelAddressSubscription = mDelAddressUseCase.execute()
                .flatMap(noBody -> {
                    if (noBody.getH().getCode() != 200) {
                        //TODO 删除不成功
                    }
                    return mGetAllUseCase.execute();
                })
                .subscribe(this::onGetAddressReceived, this::manageGetAddressError);
    }

    private void onGetAddressReceived(ReceiptList receiptList) {
        mView.hideLoadingView();
        if (receiptList.getH().getCode() == 200) {
            mView.bindAddress(receiptList.getB());
        }
    }

    private void manageGetAddressError(Throwable throwable) {

    }
}


