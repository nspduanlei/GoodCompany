package com.apec.android.ui.presenter.goods;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.config.ErrorCode;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.goods.GetAllAttribute;
import com.apec.android.domain.goods.Good;
import com.apec.android.domain.goods.GoodsDetail;
import com.apec.android.domain.goods.SkuAttribute;
import com.apec.android.domain.goods.interator.GoodsInteract;
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

        GoodsInteract.fetchGoodsAttrs(
                mContext,
                new GetDataCallback<GetAllAttribute>() {
                    @Override
                    public void onRepose(GetAllAttribute response) {
                        getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            if (response.getB().size() != 0) {
                                if (isViewAttached()) {
                                    getView().getAllAttrSuccess(response.getB());
                                }
                            }
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, goodsId);
    }

    /**
     * 根据规格属性查询sku
     *
     * @return
     */
    public void querySku(int goodsId, String attrs) {
        if (isViewAttached()) {
            getView().showLoading();
        }
        GoodsInteract.querySku(
                mContext,
                new GetDataCallback<GetAllAttribute>() {
                    @Override
                    public void onRepose(GetAllAttribute response) {
                        getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            getView().getAllAttrSuccess(response.getB());
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, goodsId, attrs);
    }

    /**
     * 获取商品详情
     * @param goodsId
     */
    public void queryGoodsDetail(int goodsId) {
        if (isViewAttached()) {
            getView().showLoading();
        }
        GoodsInteract.fetchGoodsDetail(
                mContext, new GetDataCallback<GoodsDetail>() {
                    @Override
                    public void onRepose(GoodsDetail response) {
                        getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            getView().getGoodsDetail(response.getB());
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, goodsId);
    }

    public void addShoppingCart(int mSkuId, String num) {
        if (isViewAttached()) {
            getView().showLoading();
        }
        GoodsInteract.addShoppingCart(
                mContext, new GetDataCallback<NoBody>() {
                    @Override
                    public void onRepose(NoBody response) {
                        getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            //加入购物车成功
                            getView().addShoppingCartSuccess();

                        } else if (code == ErrorCode.ERROR_NEED_LOGIN) {
                            //需要登录
                            getView().needLogin();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, String.valueOf(mSkuId), num);
    }

    public interface IView extends BaseViewInterface {
        void getAllAttrSuccess(ArrayList<SkuAttribute> attrs);
        void getGoodsDetail(Good good);
        void needLogin();
        void addShoppingCartSuccess();
        boolean isReady();
    }

}
