package com.apec.android.domain.transport.interator;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.apec.android.app.MyApplication;
import com.apec.android.config.UrlConstant;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.transport.ReceiptDefalut;
import com.apec.android.domain.transport.ReceiptList;
import com.apec.android.support.http.Listener;
import com.apec.android.support.http.request.GsonRequest;
import com.apec.android.util.L;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by duanlei on 2016/3/22.
 */
public class TransportInteract {

    /**
     * 获取全部收货地址
     * @param context
     * @param callback
     */
    public static void getAllAddress(Context context,
                                       final GetDataCallback<ReceiptList> callback) {
        GsonRequest<ReceiptList> request = new GsonRequest<>(
                context, Request.Method.GET,
                UrlConstant.URL_OBTAIN_ADDRESS_ALL,
                ReceiptList.class,
                new Listener<ReceiptList>() {
                    @Override
                    public void onResponse(ReceiptList response) {
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
     * 添加收货信息
     * @param context
     * @param callback
     * @param takeGoodsPhone
     * @param takeGoodsUser
     * @param addreCity
     * @param addreAreacounty
     * @param addreDetailAddress
     */
    public static void addDeliveryManInfo(Context context,
                                          final GetDataCallback<NoBody> callback,
                                          final String takeGoodsPhone,
                                          final String takeGoodsUser,
                                          final int addreCity,
                                          final int addreAreacounty,
                                          final String addreDetailAddress) {

        GsonRequest<NoBody> request = new GsonRequest<>(
                context, Request.Method.POST,
                UrlConstant.URL_ADD_ADDRESS,
                NoBody.class,
                new Listener<NoBody>() {
                    @Override
                    public void onResponse(NoBody response) {
                        callback.onRepose(response);
                    }

                    @Override
                    public Map getRequestParams() {
                        Map<String, String> params = new HashMap<>();

                        params.put("takeGoodsPhone", takeGoodsPhone);
                        params.put("takeGoodsUser", takeGoodsUser);
                        params.put("addreCity", String.valueOf(addreCity));
                        params.put("addreAreacounty", String.valueOf(addreAreacounty));
                        params.put("addreDetailAddress", addreDetailAddress);

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
     * 设置默认地址
     * @param context
     * @param callback
     * @param addressId
     */
    public static void setDefaultAddress(Context context,
                                         final GetDataCallback<NoBody> callback,
                                         final int addressId) {
        GsonRequest<NoBody> request = new GsonRequest<>(
                context, Request.Method.POST,
                UrlConstant.URL_DEFAULT_ADDRESS,
                NoBody.class,
                new Listener<NoBody>() {
                    @Override
                    public void onResponse(NoBody response) {
                        callback.onRepose(response);
                    }

                    @Override
                    public Map getRequestParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", String.valueOf(addressId));
                        L.e("test002", "---->" + addressId);
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
     * 删除地址
     * @param context
     * @param callback
     * @param addressId
     */
    public static void deleteAddress(Context context,
                                     final GetDataCallback<NoBody> callback,
                                     final int addressId) {
        GsonRequest<NoBody> request = new GsonRequest<>(
                context, Request.Method.POST,
                UrlConstant.URL_DELETE_ADDRESS,
                NoBody.class,
                new Listener<NoBody>() {
                    @Override
                    public void onResponse(NoBody response) {
                        callback.onRepose(response);
                    }

                    @Override
                    public Map getRequestParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", String.valueOf(addressId));
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
     * 编辑收货地址
     * @param context
     * @param callback
     * @param addressId
     * @param takeGoodsPhone
     * @param takeGoodsUser
     * @param addreCity
     * @param addreAreacounty
     * @param addreDetailAddress
     */
    public static void updateDeliveryManInfo(Context context,
                                             final GetDataCallback<NoBody> callback,
                                             final int addressId,
                                             final String takeGoodsPhone,
                                             final String takeGoodsUser,
                                             final int addreCity,
                                             final int addreAreacounty,
                                             final String addreDetailAddress) {

        GsonRequest<NoBody> request = new GsonRequest<>(
                context, Request.Method.POST,
                UrlConstant.URL_UPDATE_ADDRESS,
                NoBody.class,
                new Listener<NoBody>() {
                    @Override
                    public void onResponse(NoBody response) {
                        callback.onRepose(response);
                    }

                    @Override
                    public Map getRequestParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("addressId", String.valueOf(addressId));
                        params.put("takeGoodsPhone", takeGoodsPhone);
                        params.put("takeGoodsUser", takeGoodsUser);
                        params.put("addreCity", String.valueOf(addreCity));
                        params.put("addreAreacounty", String.valueOf(addreAreacounty));
                        params.put("addreDetailAddress", addreDetailAddress);
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
     * 获取默认收货地址
     * @param context
     * @param callback
     */
    public static void obtainDefaultAddress(Context context,
                                            final GetDataCallback<ReceiptDefalut> callback) {
        GsonRequest<ReceiptDefalut> request = new GsonRequest<>(
                context, Request.Method.GET,
                UrlConstant.URL_GET_DEFAULT_ADDRESS,
                ReceiptDefalut.class,
                new Listener<ReceiptDefalut>() {
                    @Override
                    public void onResponse(ReceiptDefalut response) {
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
