package com.apec.android.ui.presenter.user;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.H;
import com.apec.android.domain.NoBody;
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
     * 或区验证码
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
                        getView().hideLoading();

                        int code = response.getH().getCode();
                        switch (code) {
                            case 200:
                                getView().sendCodeBack();
                                break;
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getView().hideLoading();
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
                new GetDataCallback<NoBody>() {
                    @Override
                    public void onRepose(NoBody response) {
                        getView().hideLoading();
                        getView().submitCodeBack(response.getH());
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, phone, code);
    }

    public interface IView extends BaseViewInterface {
        boolean isReady();
        void sendCodeBack();
        void submitCodeBack(H head);
    }
}
