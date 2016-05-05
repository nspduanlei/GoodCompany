package com.apec.android.ui.presenter.order;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.config.ErrorCode;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.order.Order;
import com.apec.android.domain.order.OrderList;
import com.apec.android.domain.order.OrderListBack;
import com.apec.android.domain.order.interator.OrderInteract;
import com.apec.android.domain.user.ShopCartBack;
import com.apec.android.domain.user.interator.UserInteract;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/26.
 */
public class MyOrdersPresenter extends BasePresenter<MyOrdersPresenter.IView> {

    public MyOrdersPresenter(Context context) {
        super(context);
    }

    /**
     * 获取我的所有订单
     */
    public void getMyOrders() {
        if (isViewAttached()) {
            getView().showLoading();
        }
        OrderInteract.getMyOrders(
                mContext, new GetDataCallback<OrderListBack>() {
                    @Override
                    public void onRepose(OrderListBack response) {
                        if (!isViewAttached()) {
                            return;
                        }

                        getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            //获取订单成功
                            if (response.getB().getDataTotal() == 0) {
                                getView().showEmptyCase();
                            } else {
                                getView().obtainOrdersSuccess(response.getB().getData());
                            }

                        } else if (code == ErrorCode.ERROR_NEED_LOGIN) {
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
        boolean isReady();

        void needLogin();

        void obtainOrdersSuccess(ArrayList<Order> orders);
    }
}
