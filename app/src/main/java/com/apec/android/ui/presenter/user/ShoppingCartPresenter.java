package com.apec.android.ui.presenter.user;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.config.ErrorCode;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.goods.interator.GoodsInteract;
import com.apec.android.domain.user.ShopCart;
import com.apec.android.domain.user.ShopCartBack;
import com.apec.android.domain.user.interator.UserInteract;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

/**
 * Created by Administrator on 2016/2/26.
 */
public class ShoppingCartPresenter extends BasePresenter<ShoppingCartPresenter.IView> {


    public ShoppingCartPresenter(Context context) {
        super(context);
    }

    public void obtainShopCart() {
        if (isViewAttached()) {
            getView().showLoading();
        }
        UserInteract.obtainShoppingCart(
                mContext, new GetDataCallback<ShopCartBack>() {
                    @Override
                    public void onRepose(ShopCartBack response) {
                        getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            //加入购物车成功
                            getView().obtainCartSuccess(response.getB());

                        } else if (code == ErrorCode.ERR_NEED_LOGIN) {
                            //需要登录
                            getView().needLogin();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    /**
     * 更新商品数量
     * @param id skuId
     * @param i 商品数量
     */
    public void updateCartItem(int id, int i) {
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
                            //修改商品数量成功


                        } else if (code == ErrorCode.ERR_NEED_LOGIN) {
                            //需要登录
                            getView().needLogin();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, String.valueOf(id), String.valueOf(i));
    }

    public interface IView extends BaseViewInterface {
        void obtainCartSuccess(ShopCart shopCart);
        void needLogin();
        boolean isReady();
    }
}
