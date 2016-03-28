package com.apec.android.ui.presenter.user;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.H;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.user.UserBack;
import com.apec.android.domain.user.interator.UserInteract;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

/**
 * Created by Administrator on 2016/2/26.
 */
public class RegisterFPresenter extends BasePresenter<RegisterFPresenter.IView> {

    public RegisterFPresenter(Context context) {
        super(context);
    }

    /**
     * 获取验证码
     *
     * @param mobile
     */
    public void getVerificationCode(String mobile) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        UserInteract.getCode(
                mContext,
                new GetDataCallback<NoBody>() {
                    @Override
                    public void onRepose(NoBody response) {
                        if (isViewAttached()) {
                            getView().hideLoading();
                        }
                        int code = response.getH().getCode();
                        switch (code) {
                            case 200:
                                if (isViewAttached()) {
                                    getView().sendCodeBack();
                                }
                                break;
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (isViewAttached()) {
                            getView().hideLoading();
                        }
                    }
                }, mobile, "3");
    }

    /**
     * 提交验证码
     *
     * @param phone
     * @param code
     */
    public void submitVerCode(String phone, String code) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        UserInteract.submitVerCode(
                mContext,
                new GetDataCallback<UserBack>() {
                    @Override
                    public void onRepose(UserBack response) {
                        if (isViewAttached()) {
                            getView().hideLoading();
                        }

                        if (response.getH().getCode() == 200) {
                            if (isViewAttached()) {
                                getView().submitCodeBack(response);
                            }
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, phone, code);
    }

    public interface IView extends BaseViewInterface {
        boolean isReady();
        void sendCodeBack();
        void submitCodeBack(UserBack head);
    }
}
