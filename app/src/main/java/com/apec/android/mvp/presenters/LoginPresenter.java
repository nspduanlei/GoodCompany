package com.apec.android.mvp.presenters;

import com.apec.android.config.Constants;
import com.apec.android.config.ErrorCode;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.user.User;
import com.apec.android.domain.entities.user.UserBack;
import com.apec.android.domain.usercase.GetVerCodeUseCase;
import com.apec.android.domain.usercase.SubmitVerCodeUseCase;
import com.apec.android.mvp.views.LoginView;
import com.apec.android.mvp.views.View;
import com.apec.android.util.L;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by duanlei on 2016/5/11.
 */
public class LoginPresenter implements Presenter {

    LoginView mLoginView;
    GetVerCodeUseCase mGetVerCodeUseCase;
    SubmitVerCodeUseCase mSubmitVerCodeUseCase;

    @Inject
    public LoginPresenter(GetVerCodeUseCase getVerCodeUseCase,
                          SubmitVerCodeUseCase submitVerCodeUseCase) {
        mGetVerCodeUseCase = getVerCodeUseCase;
        mSubmitVerCodeUseCase = submitVerCodeUseCase;
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
        mLoginView = (LoginView) v;
    }

    @Override
    public void onCreate() {

    }

    public void getVerificationCode(String phoneNumberStr) {
        mLoginView.showLoadingView();

        mGetVerCodeUseCase.setMobile(phoneNumberStr);
        mGetVerCodeUseCase.execute()
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
        mSubmitVerCodeUseCase.execute()
                .observeOn(Schedulers.io())
                .doOnNext(userBack -> {
                    //将用户信息存储在数据库中
                    L.e("test00", "doOnNext工作的线程： " + Thread.currentThread().getName());

                    if (userBack.getH().getCode() == Constants.SUCCESS_CODE) {
                        processUser(userBack.getB());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSubmitCodeReceived, this::manageGetVerCodeError);
    }

    private void processUser(User b) {
        //数据库操作
    }

    private void onSubmitCodeReceived(UserBack userBack) {
        mLoginView.hideLoadingView();
        switch (userBack.getH().getCode()) {
            case Constants.SUCCESS_CODE:
                mLoginView.bindUser();
                break;
            case ErrorCode.COMPLETE_DATA:
                mLoginView.completeData();
                break;
            case ErrorCode.VER_CODE_ERROR:
                mLoginView.verCodeError();
                break;
        }
    }

    private void manageGetVerCodeError(Throwable error) {
        mLoginView.hideLoadingView();
    }
}
