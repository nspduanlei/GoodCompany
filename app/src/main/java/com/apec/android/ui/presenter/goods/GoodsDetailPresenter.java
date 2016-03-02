package com.apec.android.ui.presenter.goods;

import com.apec.android.domain.goods.Good;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

/**
 * Created by duanlei on 2016/3/2.
 */
public class GoodsDetailPresenter extends BasePresenter<GoodsDetailPresenter.IView> {





    public interface IView extends BaseViewInterface {
        void showGoods(Good goods);
        boolean isReady();
    }

}
