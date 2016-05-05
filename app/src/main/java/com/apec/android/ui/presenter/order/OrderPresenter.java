package com.apec.android.ui.presenter.order;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.config.ErrorCode;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.order.Order;
import com.apec.android.domain.order.OrderBack;
import com.apec.android.domain.order.interator.OrderInteract;
import com.apec.android.domain.user.ShopCartBack;
import com.apec.android.domain.user.interator.UserInteract;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

/**
 * Created by Administrator on 2016/2/26.
 */
public class OrderPresenter extends BasePresenter<OrderPresenter.IView> {


    public OrderPresenter(Context context) {
        super(context);
    }

    /**
     * 获取某个订单的订单详情
     *
     * @param orderId
     */
    public void getOrder(int orderId) {
        if (isViewAttached()) {
            getView().showLoading();
        }
        OrderInteract.getOrder(
                mContext, new GetDataCallback<OrderBack>() {
                    @Override
                    public void onRepose(OrderBack response) {
                        if (!isViewAttached()) {
                            return;
                        }

                        getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            getView().getOrderSuccess(response.getB());
                        } else if (code == ErrorCode.ERROR_NEED_LOGIN) {
                            //需要登录
                            getView().needLogin();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, orderId);
    }

    /**
     * 取消订单
     *
     * @param orderId
     */
    public void cancelOrder(int orderId) {
        if (isViewAttached()) {
            getView().showLoading();
        }
        OrderInteract.cancelOrder(
                mContext, new GetDataCallback<NoBody>() {
                    @Override
                    public void onRepose(NoBody response) {
                        if (!isViewAttached()) {
                            return;
                        }

                        getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            //取消订单成功
                            getView().cancelOrderSuccess();
                        } else if (code == ErrorCode.ERROR_NEED_LOGIN) {
                            //需要登录
                            getView().needLogin();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, orderId);
    }

    public interface IView extends BaseViewInterface {
        void cancelOrderSuccess();

        void getOrderSuccess(Order order);

        void needLogin();

        boolean isReady();
    }
}
