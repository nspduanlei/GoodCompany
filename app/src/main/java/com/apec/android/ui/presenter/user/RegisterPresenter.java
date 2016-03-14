package com.apec.android.ui.presenter.user;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.goods.interator.GetGoodsInteract;
import com.apec.android.support.test;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

/**
 * Created by Administrator on 2016/2/26.
 */
public class RegisterPresenter extends BasePresenter<RegisterPresenter.IView> {


    public RegisterPresenter(Context context) {
        super(context);
    }

    public interface IView extends BaseViewInterface {
        boolean isReady();
    }
}
