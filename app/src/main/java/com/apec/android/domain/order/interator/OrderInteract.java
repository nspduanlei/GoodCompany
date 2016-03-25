package com.apec.android.domain.order.interator;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.apec.android.app.MyApplication;
import com.apec.android.config.UrlConstant;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.order.OrderBack;
import com.apec.android.domain.order.OrderList;
import com.apec.android.domain.order.OrderListBack;
import com.apec.android.support.http.Listener;
import com.apec.android.support.http.request.GsonRequest;
import com.apec.android.util.L;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by duanlei on 2016/3/23.
 */
public class OrderInteract {
    /**
     * 获取默认收货地址
     * @param context
     * @param callback
     */
    public static void createOrder(Context context,
                                            final GetDataCallback<NoBody> callback,
                                   final String skus, final int addressId) {
        GsonRequest<NoBody> request = new GsonRequest<>(
                context, Request.Method.POST,
                UrlConstant.URL_CART_CREATE_ORDER,
                NoBody.class,
                new Listener<NoBody>() {
                    @Override
                    public void onResponse(NoBody response) {
                        callback.onRepose(response);
                    }

                    @Override
                    public Map getRequestParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("skuIds", skus);
                        params.put("addressId", String.valueOf(addressId));

                        L.e("test001", "skuIds-->" + skus + "|addressId-->" + addressId);
                        return params;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onErrorResponse(error);
                    }
                }
        );
        MyApplication.getRequestQueue().add(request);
    }

    /**
     * 获取我的订单列表
     * @param context
     * @param callback
     */
    public static void getMyOrders(Context context,
                                   final GetDataCallback<OrderListBack> callback) {
        GsonRequest<OrderListBack> request = new GsonRequest<>(
                context, Request.Method.POST,
                UrlConstant.URL_MY_ORDERS,
                OrderListBack.class,
                new Listener<OrderListBack>() {
                    @Override
                    public void onResponse(OrderListBack response) {
                        callback.onRepose(response);
                    }

                    @Override
                    public Map getRequestParams() {
                        return null;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onErrorResponse(error);
                    }
                }
        );
        MyApplication.getRequestQueue().add(request);
    }

    /**
     * 获取订单详情
     * @param context
     * @param callback
     * @param orderId
     */
    public static void getOrder(Context context,
                                final GetDataCallback<OrderBack> callback,
                                final int orderId) {
        GsonRequest<OrderBack> request = new GsonRequest<>(
                context, Request.Method.GET,
                UrlConstant.URL_ORDER + "?id=" +orderId,
                OrderBack.class,
                new Listener<OrderBack>() {
                    @Override
                    public void onResponse(OrderBack response) {
                        callback.onRepose(response);
                    }

                    @Override
                    public Map getRequestParams() {
                        return null;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onErrorResponse(error);
                    }
                }
        );
        MyApplication.getRequestQueue().add(request);
    }

    /**
     * 取消订单
     * @param context
     * @param callback
     * @param orderId
     */
    public static void cancelOrder(Context context,
                                   final GetDataCallback<NoBody> callback,
                                   final int orderId) {
        L.e("test001", "orderId--->" + orderId);

        GsonRequest<NoBody> request = new GsonRequest<>(
                context, Request.Method.GET,
                UrlConstant.URL_CANCEL_ORDER + "?id=" +orderId,
                NoBody.class,
                new Listener<NoBody>() {
                    @Override
                    public void onResponse(NoBody response) {
                        callback.onRepose(response);
                    }

                    @Override
                    public Map getRequestParams() {
                        return null;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onErrorResponse(error);
                    }
                }
        );
        MyApplication.getRequestQueue().add(request);
    }
}
