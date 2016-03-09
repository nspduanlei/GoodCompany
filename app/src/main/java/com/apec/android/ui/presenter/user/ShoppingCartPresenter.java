package com.apec.android.ui.presenter.user;

import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

/**
 * Created by Administrator on 2016/2/26.
 */
public class ShoppingCartPresenter extends BasePresenter<ShoppingCartPresenter.IView> {



    public interface IView extends BaseViewInterface {
        boolean isReady();
    }
}
