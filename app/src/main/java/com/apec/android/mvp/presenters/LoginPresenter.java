package com.apec.android.mvp.presenters;

import com.apec.android.config.Constants;
import com.apec.android.config.ErrorCode;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.user.ShopCart;
import com.apec.android.domain.entities.user.ShopCartBack;
import com.apec.android.domain.entities.user.ShopCartData;
import com.apec.android.domain.entities.user.User;
import com.apec.android.domain.entities.user.UserBack;
import com.apec.android.domain.usercase.CompleteUserUseCase;
import com.apec.android.domain.usercase.GetAllCartUseCase;
import com.apec.android.domain.usercase.GetVerCodeUseCase;
import com.apec.android.domain.usercase.SubmitVerCodeUseCase;
import com.apec.android.mvp.views.LoginView;
import com.apec.android.mvp.views.View;
import com.apec.android.util.L;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by duanlei on 2016/5/11.
 */
public class LoginPresenter implements Presenter {

    LoginView mLoginView;
    GetVerCodeUseCase mGetVerCodeUseCase;
    SubmitVerCodeUseCase mSubmitVerCodeUseCase;
    CompleteUserUseCase mCompleteUserUseCase;
    GetAllCartUseCase mGetAllCartUseCase;

    Subscription mSubscriptionGetVer;
    Subscription mSubscriptionSubmit;
    Subscription mSubscriptionComplete;
    Subscription mSubscriptionAllCart;

    @Inject
    public LoginPresenter(GetVerCodeUseCase getVerCodeUseCase,
                          SubmitVerCodeUseCase submitVerCodeUseCase,
                          CompleteUserUseCase completeUserUseCase,
                          GetAllCartUseCase getAllCartUseCase) {
        mGetVerCodeUseCase = getVerCodeUseCase;
        mSubmitVerCodeUseCase = submitVerCodeUseCase;
        mCompleteUserUseCase = completeUserUseCase;
        mGetAllCartUseCase = getAllCartUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mSubscriptionGetVer != null) {
            mSubscriptionGetVer.unsubscribe();
        }
        if (mSubscriptionSubmit != null) {
            mSubscriptionSubmit.unsubscribe();
        }
        if (mSubscriptionComplete != null) {
            mSubscriptionComplete.unsubscribe();
        }
        if (mSubscriptionAllCart != null) {
            mSubscriptionAllCart.unsubscribe();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mLoginView = (LoginView) v;
    }

    @Override
    public void onCreate() {

    }

    public void getVerificationCode(String phoneNumberStr) {
        mLoginView.showLoadingView();

        mGetVerCodeUseCase.setMobile(phoneNumberStr);
        mSubscriptionGetVer = mGetVerCodeUseCase.execute()
                .subscribe(this::onVerCodeReceived, this::manageGetVerCodeError);
    }

    private void onVerCodeReceived(NoBody noBody) {
        mLoginView.hideLoadingView();
        if (noBody.getH().getCode() == Constants.SUCCESS_CODE) {
            mLoginView.getVerCodeReceived();
        }
    }

    //提交验证码
    public void submitVerCode(String phoneNumber, String vCode) {
        mLoginView.showLoadingView();
        mSubmitVerCodeUseCase.setData(phoneNumber, vCode);

        mSubscriptionSubmit = mSubmitVerCodeUseCase.execute()
                .observeOn(Schedulers.io())
                .doOnNext(userBack -> {
                    //将用户信息存储在数据库中
                    if (userBack.getH().getCode() == Constants.SUCCESS_CODE) {
                        processUser(userBack.getB());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSubmitCodeReceived, this::manageGetVerCodeError);
    }

    private void processUser(User user) {
        //数据库操作
        user.saveThrows();
    }

    private void onSubmitCodeReceived(UserBack userBack) {
//        if (userBack.getH().getCode() == 200) {
//            //登录成功获取购物车数据填充
//            mSubscriptionAllCart = mGetAllCartUseCase.execute()
//                    .observeOn(Schedulers.io())
//                    .doOnNext(shopCartBack -> {
//                        //将用户信息存储在数据库中
//                        if (shopCartBack.getH().getCode() == Constants.SUCCESS_CODE) {
//                            if (shopCartBack.getB().getSkus().size() > 0) {
//                                ShopCartData.addCarts(shopCartBack.getB().getSkus());
//                            }
//                        }
//                    })
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(this::onAllCartReceived, this::ManagerError);
//        }

        mLoginView.hideLoadingView();
        switch (userBack.getH().getCode()) {
            case Constants.SUCCESS_CODE:
                mLoginView.bindUser(userBack.getB());
                break;
            case ErrorCode.COMPLETE_DATA:
                mLoginView.completeData();
                break;
            case ErrorCode.VER_CODE_ERROR:
                mLoginView.verCodeError();
                break;
        }
    }

    private void ManagerError(Throwable throwable) {

    }

    private void onAllCartReceived(ShopCartBack shopCartBack) {

    }

    private void manageGetVerCodeError(Throwable error) {
        mLoginView.hideLoadingView();
    }

    public void completeUser(User user) {
        mLoginView.showLoadingView();
        mCompleteUserUseCase.setData(user);
        mCompleteUserUseCase.execute().subscribe(this::onCompleteReceived, this::manageError);
    }

    private void manageError(Throwable throwable) {
        mLoginView.hideLoadingView();
    }

    private void onCompleteReceived(NoBody noBody) {
        mLoginView.hideLoadingView();
        if (noBody.getH().getCode() == 200) {
            mLoginView.completeSuccess();
        }
    }
}
