package com.apec.android.ui.presenter.user;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.config.ErrorCode;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.goods.interator.GoodsInteract;
import com.apec.android.domain.entities.order.interator.OrderInteract;
import com.apec.android.domain.entities.transport.ArrivalTime;
import com.apec.android.domain.entities.transport.GoodsReceipt;
import com.apec.android.domain.entities.transport.ReceiptDefault;
import com.apec.android.domain.entities.transport.interator.TransportInteract;
import com.apec.android.domain.entities.user.ShopCart;
import com.apec.android.domain.entities.user.ShopCartBack;
import com.apec.android.domain.entities.user.interator.UserInteract;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

/**
 * Created by Administrator on 2016/2/26.
 */
public class ShoppingCartPresenter extends BasePresenter<ShoppingCartPresenter.IView> {


    public ShoppingCartPresenter(Context context) {
        super(context);
    }

    /**
     * 获取购物车全部商品
     */
    public void obtainShopCart(String cityId) {
        if (isViewAttached()) {
            getView().showLoading();
        }
        UserInteract.obtainShoppingCart(
                mContext, new GetDataCallback<ShopCartBack>() {
                    @Override
                    public void onRepose(ShopCartBack response) {
                        if (!isViewAttached()) {
                            return;
                        }

                        getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            if (response.getB().getSkus() == null ||
                                    response.getB().getSkus().size() == 0) {
                                getView().showEmptyCase();
                            } else {
                                getView().obtainCartSuccess(response.getB());
                            }
                        } else if (code == ErrorCode.ERROR_NEED_LOGIN) {
                            //需要登录
                            getView().needLogin();
                        } else {
                            getView().showEmptyCase();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, cityId);
    }

    /**
     * 更新商品数量
     *
     * @param id skuId
     * @param i  商品数量
     */
    public void updateCartItem(int id, int i) {
//        if (isViewAttached()) {
//            getView().showLoading();
//        }
        GoodsInteract.addShoppingCart(
                mContext, new GetDataCallback<NoBody>() {
                    @Override
                    public void onRepose(NoBody response) {
                        if (!isViewAttached()) {
                            return;
                        }

                        //getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            //修改商品数量成功
                            getView().updateNumSuccess();
                        } else if (code == ErrorCode.ERROR_NEED_LOGIN) {
                            //需要登录
                            getView().needLogin();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, String.valueOf(id), String.valueOf(i));
    }

    /**
     * 获取默认收货地址
     */
    public void obtainDefaultAddress() {
        if (isViewAttached()) {
            getView().showLoading();
        }
        TransportInteract.obtainDefaultAddress(
                mContext, new GetDataCallback<ReceiptDefault>() {
                    @Override
                    public void onRepose(ReceiptDefault response) {
                        if (!isViewAttached()) {
                            return;
                        }

                        getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            //修改商品数量成功
                            getView().obtainDefaultSuccess(response.getB());
                        } else if (code == ErrorCode.ERROR_NEED_LOGIN) {
                            //需要登录
                            getView().needLogin();
                        } else if (code == ErrorCode.NOT_EXIST_DEFAULT_ADDRESS) {
                            //不存在默认收货地址
                            getView().obtainDefaultSuccess(null);
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    /**
     * 创建订单
     *
     * @param skus
     * @param addressId
     */
    public void createOrder(String skus, int addressId) {
        if (isViewAttached()) {
            getView().showLoading();
        }
        OrderInteract.createOrder(
                mContext, new GetDataCallback<NoBody>() {
                    @Override
                    public void onRepose(NoBody response) {
                        if (!isViewAttached()) {
                            return;
                        }

                        getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            //下单成功
                            getView().obtainOrderSuccess();
                        } else if (code == ErrorCode.ERROR_NEED_LOGIN ||
                                code == ErrorCode.ERROR_NOT_EXIST_USER) {
                            //需要登录
                            getView().needLoginPay();
                        } else if (code == ErrorCode.NOT_EXIST_DEFAULT_ADDRESS) {
                            //不存在默认收货地址
                            getView().obtainDefaultSuccess(null);
                        } else {
                            getView().orderError("创建订单失败：" + response.getH().getMsg());
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, skus, addressId);
    }

    /**
     * 获取到货时间
     */
    public void getArrivalTime() {
        if (isViewAttached()) {
            getView().showLoading();
        }
        GoodsInteract.getArrivalTime(
                mContext, new GetDataCallback<ArrivalTime>() {
                    @Override
                    public void onRepose(ArrivalTime response) {
                        if (!isViewAttached()) {
                            return;
                        }

                        getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            getView().getArrivalTimeSuccess(response.getB().getTime());
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    /**
     * 删除商品
     *
     * @param id
     */
    public void deleteGoods(int id) {
        GoodsInteract.deleteShoppingCart(
                mContext, new GetDataCallback<NoBody>() {
                    @Override
                    public void onRepose(NoBody response) {
                        if (!isViewAttached()) {
                            return;
                        }

                        //getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            //修改商品数量成功
                            getView().updateNumSuccess();
                        } else if (code == ErrorCode.ERROR_NEED_LOGIN) {
                            //需要登录
                            getView().needLogin();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, String.valueOf(id));
    }

    public interface IView extends BaseViewInterface {
        void getArrivalTimeSuccess(String time);

        void updateNumSuccess();

        void obtainOrderSuccess();

        void obtainDefaultSuccess(GoodsReceipt goodsReceipt);

        void obtainCartSuccess(ShopCart shopCart);

        void needLogin();

        void needLoginPay();

        boolean isReady();

        void orderError(String msg);
    }
}
