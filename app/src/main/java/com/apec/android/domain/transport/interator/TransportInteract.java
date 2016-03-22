package com.apec.android.domain.transport.interator;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.apec.android.app.MyApplication;
import com.apec.android.config.UrlConstant;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.user.ShopCartBack;
import com.apec.android.support.http.Listener;
import com.apec.android.support.http.request.GsonRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by duanlei on 2016/3/22.
 */
public class TransportInteract {

    public static void addShoppingCart(Context context,
                                       final GetDataCallback<NoBody> callback) {
        GsonRequest<NoBody> request = new GsonRequest<>(
                context, Request.Method.GET,
                UrlConstant.URL_OBTAIN_ADDR_ALL,
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
                context, Request.Method.GET,
                UrlConstant.URL_OBTAIN_ADDR_ALL,
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
}
