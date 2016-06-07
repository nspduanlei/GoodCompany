package com.apec.android.mvp.presenters;

import com.apec.android.domain.entities.user.ShopCartBack;
import com.apec.android.domain.usercase.DeleteCartUseCase;
import com.apec.android.domain.usercase.DoAddCartUseCase;
import com.apec.android.domain.usercase.GetAllCartUseCase;
import com.apec.android.mvp.views.ShoppingCartView;
import com.apec.android.mvp.views.View;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/6/7.
 */
public class ShoppingCartPresenter implements Presenter {

    ShoppingCartView mView;

    GetAllCartUseCase mGetAllCartUseCase;
    DeleteCartUseCase mDeleteCartUseCase;
    DoAddCartUseCase mDoAddCartUseCase;

    Subscription mGetAllSubscription;

    @Inject
    public ShoppingCartPresenter(GetAllCartUseCase getAllCartUseCase,
                                 DeleteCartUseCase deleteCartUseCase,
                                 DoAddCartUseCase doAddCartUseCase) {
        mGetAllCartUseCase = getAllCartUseCase;
        mDeleteCartUseCase = deleteCartUseCase;
        mDoAddCartUseCase = doAddCartUseCase;

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
        mView = (ShoppingCartView) v;
    }

    @Override
    public void onCreate() {
        getData();
    }

    public void getData() {
        mView.showLoadingView();
        mGetAllSubscription = mGetAllCartUseCase.execute()
                .subscribe(this::onGetAllReceived, this::manageGetAllError);
    }

    private void manageGetAllError(Throwable throwable) {
        mView.hideLoadingView();
    }

    private void onGetAllReceived(ShopCartBack shopCartBack) {
        mView.hideLoadingView();
        if (shopCartBack.getH().getCode() == 200) {
            mView.bindCart(shopCartBack.getB());
        }
    }
}
