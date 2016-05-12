package com.apec.android.ui.presenter.goods;

import android.content.Context;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.goods.Good;
import com.apec.android.domain.goods.Goods;
import com.apec.android.domain.goods.interator.GoodsInteract;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/26.
 */
public class GoodsFPresenter extends BasePresenter<GoodsFPresenter.IView> {

    public GoodsFPresenter(Context context) {
        super(context);
    }

    /**
     * 获取商品列表
     */
    public void fetchGoods(int cid, int cityId) {
        if (isViewAttached()) {
            getView().showLoading();
        }
        GoodsInteract.fetchGoods(
                mContext,
                new GetDataCallback<Goods>() {
                    @Override
                    public void onRepose(Goods response) {
                        if (!isViewAttached()) {
                            return;
                        }
                        getView().hideNoConnection();
                        getView().hideLoading();
                        if (response.getH().getCode() == 200) {
                            if (response.getB().getData().size() > 0) {
                                getView().showGoods(response.getB().getData());
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (!isViewAttached()) {
                            return;
                        }
                        getView().hideLoading();
                        if (error instanceof NoConnectionError) {
                            getView().showNoConnection();
                        }
                    }
                }, cid, cityId);
    }

    public interface IView extends BaseViewInterface {
        void showGoods(ArrayList<Good> goods);

        boolean isReady();
    }
}
