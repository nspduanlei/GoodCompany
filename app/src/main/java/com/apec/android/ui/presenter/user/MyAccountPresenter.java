package com.apec.android.ui.presenter.user;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.config.ErrorCode;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.user.Area;
import com.apec.android.domain.user.Areas;
import com.apec.android.domain.user.User;
import com.apec.android.domain.user.UserBack;
import com.apec.android.domain.user.interator.UserInteract;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/26.
 */
public class MyAccountPresenter extends BasePresenter<MyAccountPresenter.IView> {


    public MyAccountPresenter(Context context) {
        super(context);
    }

    public void obtainUserInfo() {
        if (isViewAttached()) {
            getView().showLoading();
        }

        UserInteract.obtainUserInfo(mContext, new GetDataCallback<UserBack>() {
            @Override
            public void onRepose(UserBack response) {
                if (isViewAttached()) {
                    getView().hideLoading();
                }

                int code = response.getH().getCode();

                if (code == 200) {
                    if (isViewAttached()) {
                        getView().getUserInfoBack(response.getB());
                    }
                }  else if (code == ErrorCode.ERROR_NEED_LOGIN) {
                    //需要登录
                    if (isViewAttached()) {
                        getView().needLogin();
                    }
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    /**
     * 更新用户信息
     * @param user
     */
    public void updateUserInfo(User user) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        UserInteract.updateUserInfo(mContext, new GetDataCallback<NoBody>() {
            @Override
            public void onRepose(NoBody response) {
                if (response.getH().getCode() == 200) {
                    if (isViewAttached()) {
                        getView().updateSuccess();
                    }
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, user);
    }

    public interface IView extends BaseViewInterface {
        boolean isReady();
        void getUserInfoBack(User user);
        void updateSuccess();
        void needLogin();
    }
}
