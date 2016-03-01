package com.apec.android.ui.presenter.goods;

import com.android.volley.VolleyError;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.goods.interator.GetGoodsInterator;
import com.apec.android.support.test;

/**
 * Created by Administrator on 2016/2/26.
 */
public class GoodsPresenter {

    private IView view;

    public GoodsPresenter(IView view) {
        this.view = view;
    }

    /**
     * 获取商品类型
     */
    public void fetchGoodTypes() {
        view.showLoading();

        GetGoodsInterator.fecthCategorys(
                new GetDataCallback<test>() {
                    @Override
                    public void onRepose(test response) {

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
    }
}
