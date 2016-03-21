package com.apec.android.ui.presenter.user;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.config.ErrorCode;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.goods.interator.GetGoodsInteract;
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

    public interface IView extends BaseViewInterface {
        void obtainCartSuccess(ShopCart shopCart);
        void needLogin();
        boolean isReady();
    }
}
