package com.apec.android.ui.presenter.order;

import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

/**
 * Created by Administrator on 2016/2/26.
 */
public class MyOrdersPresenter extends BasePresenter<MyOrdersPresenter.IView> {

    public interface IView extends BaseViewInterface {
        boolean isReady();
    }
}
