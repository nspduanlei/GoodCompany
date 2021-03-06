package com.apec.android.ui.presenter.goods;

import com.android.volley.VolleyError;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.goods.Good;
import com.apec.android.domain.goods.Goods;
import com.apec.android.domain.goods.interator.GetGoodsInteract;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/26.
 */
public class GoodsFPresenter extends BasePresenter<GoodsFPresenter.IView> {

    /**
     * 获取商品列表
     */
    public void fetchGoods() {
        if(isViewAttached()) {
            getView().showLoading();
        }

        GetGoodsInteract.fetchGoods(
                new GetDataCallback<Goods>() {
                    @Override
                    public void onRepose(Goods response) {
                        getView().hideLoading();
                        if (response.getH().getCode() == 200) {
                            getView().showGoods(response.getB().getData());
                        } else {

                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    public interface IView extends BaseViewInterface {
        void showGoods(ArrayList<Good> goods);
        boolean isReady();
    }
}
