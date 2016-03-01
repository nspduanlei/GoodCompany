package com.apec.android.ui.presenter.goods;

import com.android.volley.VolleyError;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.goods.Goods;
import com.apec.android.domain.goods.interator.GetGoodsInterator;
import com.apec.android.support.test;

/**
 * Created by Administrator on 2016/2/26.
 */
public class GoodsFPresenter {

    private IView view;

    public GoodsFPresenter(IView view) {
        this.view = view;
    }

    /**
     * 获取商品类型
     */
    public void fetchGoods() {
        view.showLoading();

        GetGoodsInterator.fecthGoods(
                new GetDataCallback<Goods>() {
                    @Override
                    public void onRepose(Goods response) {

                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    public interface IView {
        void hideLoading();

        void showLoading();

        void showEmptyCase();

        void showGoodTypes();

        void isReady();
    }
}
