package com.apec.android.ui.presenter.goods;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.goods.GetAllAttribute;
import com.apec.android.domain.goods.Good;
import com.apec.android.domain.goods.SkuAttribute;
import com.apec.android.domain.goods.interator.GetGoodsInteract;
import com.apec.android.domain.goods.ModelTest;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

import java.util.ArrayList;

/**
 * Created by duanlei on 2016/3/2.
 */
public class GoodsDetailPresenter extends BasePresenter<GoodsDetailPresenter.IView> {

    public GoodsDetailPresenter(Context context) {
        super(context);
    }

    /**
     * 获取商品全部规格属性
     *
     * @return
     */
    public void fetchGoodsAttrs(int goodsId) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        GetGoodsInteract.fetchGoodsAttrs(
                mContext,
                new GetDataCallback<GetAllAttribute>() {
                    @Override
                    public void onRepose(GetAllAttribute response) {
                        int code = response.getH().getCode();
                        if (code == 200) {
                            getView().getAllAttrSuccess(response.getB());
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, goodsId);
    }

    /**
     * 获取商品全部规格属性
     *
     * @return
     */
    public void querySku(int goodsId, String arrrs) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        GetGoodsInteract.fetchGoodsAttrs(
                mContext,
                new GetDataCallback<GetAllAttribute>() {
                    @Override
                    public void onRepose(GetAllAttribute response) {
                        int code = response.getH().getCode();
                        if (code == 200) {
                            getView().getAllAttrSuccess(response.getB());
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, goodsId);
    }



    public interface IView extends BaseViewInterface {
        void getAllAttrSuccess(ArrayList<SkuAttribute> attrs);
        boolean isReady();
    }

}
