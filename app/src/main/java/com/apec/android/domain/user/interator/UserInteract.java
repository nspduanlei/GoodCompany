package com.apec.android.domain.user.interator;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.apec.android.app.MyApplication;
import com.apec.android.config.UrlConstant;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.goods.Goods;
import com.apec.android.domain.user.Areas;
import com.apec.android.domain.user.ShopCartBack;
import com.apec.android.support.http.Listener;
import com.apec.android.support.http.request.GsonRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/25.
 */
public class UserInteract {

    /**
     * 获取验证码
     *
     * @param callback
     */
    public static void getCode(Context context, final GetDataCallback<NoBody> callback,
                               final String mobile, final String type) {
        GsonRequest<NoBody> request = new GsonRequest<>(
                context, Request.Method.POST,
                UrlConstant.URL_SMS_GETCODE, NoBody.class,
                new Listener<NoBody>() {
                    @Override
                    public void onResponse(NoBody response) {
                        callback.onRepose(response);
                    }

                    @Override
                    public Map getRequestParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("mobile", mobile);
                        params.put("type", type);
                        return params;
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onErrorResponse(error);
                    }
                });

        MyApplication.getRequestQueue().add(request);
    }

    /**
     * 提交验证码
     *
     * @param callback
     */
    public static void submitVerCode(Context context, final GetDataCallback<NoBody> callback,
                                     final String phone, final String code) {
        GsonRequest<NoBody> request = new GsonRequest<>(
                context, Request.Method.POST,
                UrlConstant.URL_SMS_LOGIN, NoBody.class,
                new Listener<NoBody>() {
                    @Override
                    public void onResponse(NoBody response) {
                        callback.onRepose(response);
                    }

                    @Override
                    public Map getRequestParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("phone", phone);
                        params.put("code", code);
                        return params;
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onErrorResponse(error);
                    }
                });

        MyApplication.getRequestQueue().add(request);
    }

    /**
     * 获取供选择的城市或地区
     *
     * @param context
     * @param callback
     * @param id
     */
    public static void obtainArea(Context context, final GetDataCallback<Areas> callback,
                                  final String id) {
        GsonRequest<Areas> request = new GsonRequest<>(
                context, Request.Method.GET,
                UrlConstant.URL_AREA + "?id=" + id,
                Areas.class,
                new Listener<Areas>() {
                    @Override
                    public void onResponse(Areas response) {
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

    public static void submitUserData(Context context, final GetDataCallback<NoBody> callback,
                                      final String userShop,
                                      final String userName,
                                      final String userCity,
                                      final String userAreacounty,
                                      final String userAddress) {
        GsonRequest<NoBody> request = new GsonRequest<>(
                context, Request.Method.POST,
                UrlConstant.URL_COMPLETE,
                NoBody.class,
                new Listener<NoBody>() {
                    @Override
                    public void onResponse(NoBody response) {
                        callback.onRepose(response);
                    }

                    @Override
                    public Map getRequestParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("userShop", userShop);
                        params.put("userName", userName);
                        params.put("userCity", userCity);
                        params.put("userAreacounty", userAreacounty);
                        params.put("userAddress", userAddress);
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

    public static void obtainShoppingCart(Context context,
                                          final GetDataCallback<ShopCartBack> callback) {
        GsonRequest<ShopCartBack> request = new GsonRequest<>(
                context, Request.Method.GET,
                UrlConstant.URL_OBTAIN_SHOPPING_CART,
                ShopCartBack.class,
                new Listener<ShopCartBack>() {
                    @Override
                    public void onResponse(ShopCartBack response) {
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