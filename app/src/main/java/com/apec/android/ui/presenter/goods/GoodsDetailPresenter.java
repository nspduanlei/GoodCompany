package com.apec.android.ui.presenter.goods;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.goods.Good;
import com.apec.android.domain.goods.interator.GetGoodsInteract;
import com.apec.android.domain.goods.ModelTest;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

/**
 * Created by duanlei on 2016/3/2.
 */
public class GoodsDetailPresenter extends BasePresenter<GoodsDetailPresenter.IView> {

    public GoodsDetailPresenter(Context context) {
        super(context);
    }

    /**
     * 获取商品详情
     *
     * @return
     */
    public void fetchGoodsDetail(Context context) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        GetGoodsInteract.fetchGoodsDetail(
                context,
                new GetDataCallback<ModelTest>() {
                    @Override
                    public void onRepose(ModelTest response) {

                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, 10, 10);
    }

    public interface IView extends BaseViewInterface {
        void showGoods(Good goods);

        boolean isReady();
    }

}
