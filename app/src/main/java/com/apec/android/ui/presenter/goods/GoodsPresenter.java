package com.apec.android.ui.presenter.goods;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.goods.GateGory;
import com.apec.android.domain.goods.interator.GoodsInteract;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

/**
 * Created by Administrator on 2016/2/26.
 */
public class GoodsPresenter extends BasePresenter<GoodsPresenter.IView> {

    public GoodsPresenter(Context context) {
        super(context);
    }

    /**
     * 获取商品类型
     */
    public void fetchGoodTypes() {
        if (isViewAttached()) {
            getView().showLoading();
        }
        GoodsInteract.fetchCategory(
                mContext,
                new GetDataCallback<GateGory>() {
                    @Override
                    public void onRepose(GateGory response) {
                        if (isViewAttached()) {
                            getView().hideLoading();
                        }
                        int code = response.getH().getCode();
                        if (code == 200) {

                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    public interface IView extends BaseViewInterface {
        void showGoodTypes();
    }
}
