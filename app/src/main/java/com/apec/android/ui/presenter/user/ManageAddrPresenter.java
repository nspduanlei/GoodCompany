package com.apec.android.ui.presenter.user;

import android.content.Context;

import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

/**
 * Created by Administrator on 2016/2/26.
 */
public class ManageAddrPresenter extends BasePresenter<ManageAddrPresenter.IView> {


    public ManageAddrPresenter(Context context) {
        super(context);
    }

    public interface IView extends BaseViewInterface {
        boolean isReady();
    }
}