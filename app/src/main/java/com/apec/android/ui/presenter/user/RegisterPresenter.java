package com.apec.android.ui.presenter.user;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.goods.interator.GetGoodsInteract;
import com.apec.android.domain.user.Area;
import com.apec.android.domain.user.Areas;
import com.apec.android.domain.user.interator.LoginInteractor;
import com.apec.android.support.test;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/26.
 */
public class RegisterPresenter extends BasePresenter<RegisterPresenter.IView> {

    public RegisterPresenter(Context context) {
        super(context);
    }


    public void submitUserData(String userShop,
                               String userName,
                               int userCity,
                               int userAreacounty,
                               String userAddress) {
        LoginInteractor.submitUserData(mContext, new GetDataCallback<NoBody>() {
                    @Override
                    public void onRepose(NoBody response) {
                        int code = response.getH().getCode();
                        if (code == 200) {
                            getView().submitSuccess();
                        } else if (code == 10) { //需要登录
                            getView().needLogin();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, userShop, userName, String.valueOf(userCity),
                String.valueOf(userAreacounty), userAddress);
    }

    public interface IView extends BaseViewInterface {
        boolean isReady();
        void submitSuccess();
        void needLogin();
    }
}
