package com.apec.android.domain.goods.interator;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.apec.android.app.MyApplication;
import com.apec.android.config.UrlConstant;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.goods.GateGorys;
import com.apec.android.domain.goods.GetAllAttribute;
import com.apec.android.domain.goods.Goods;
import com.apec.android.domain.goods.GoodsDetail;
import com.apec.android.domain.goods.ModelTest;
import com.apec.android.support.http.Listener;
import com.apec.android.support.http.request.GsonRequest;
import com.apec.android.support.test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


/**
 * Created by Administrator on 2016/2/26.
 */
public class GetGoodsInteract {
    /**
     * 获取所用的类型
     *
     * @param callback
     */
    public static void fetchCategorys(Context context, final GetDataCallback<GateGorys> callback) {
        GsonRequest<GateGorys> request = new GsonRequest<>(
                context, Request.Method.POST,
                UrlConstant.URL_CATEGORY, GateGorys.class,
                new Listener<GateGorys>() {
                    @Override
                    public void onResponse(GateGorys response) {
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
                });

        MyApplication.getRequestQueue().add(request);
    }

    /**
     * 获取商品列表
     *
     * @param callback
     */
    public static void fetchGoods(Context context, final GetDataCallback<Goods> callback) {
        GsonRequest<Goods> request = new GsonRequest<>(
                context, Request.Method.POST,
                UrlConstant.URL_GOODS, Goods.class,
                new Listener<Goods>() {
                    @Override
                    public void onResponse(Goods response) {
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
                });

        MyApplication.getRequestQueue().add(request);
    }

    /**
     * 获得商品详情
     *
     * @param callback
     * @param id       商品id
     */
    public static void fetchGoodsDetail(Context context,
                                        final GetDataCallback<GoodsDetail> callback,
                                        final int id) {
        GsonRequest<GoodsDetail> request = new GsonRequest<>(
                context, Request.Method.GET,
                UrlConstant.URL_GOODS_DETAIL + "?id=" + id,
                GoodsDetail.class,
                new Listener<GoodsDetail>() {
                    @Override
                    public void onResponse(GoodsDetail response) {
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
                });

        MyApplication.getRequestQueue().add(request);
    }


    /**
     * 获得商品全部规格属性
     *
     * @param callback
     * @param id       商品id
     */
    public static void fetchGoodsAttrs(Context context,
                                       final GetDataCallback<GetAllAttribute> callback,
                                       final int id) {
        GsonRequest<GetAllAttribute> request = new GsonRequest<>(
                context, Request.Method.GET,
                UrlConstant.URL_ALL_ARRT + "?id=" + id,
                GetAllAttribute.class,
                new Listener<GetAllAttribute>() {
                    @Override
                    public void onResponse(GetAllAttribute response) {
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
                });

        MyApplication.getRequestQueue().add(request);
    }

    public static void querySku(Context context,
                                       final GetDataCallback<GetAllAttribute> callback,
                                       final int id, final String attrs) {
        GsonRequest<GetAllAttribute> request = new GsonRequest<>(
                context, Request.Method.GET,
                UrlConstant.URL_SKU_ATTR + "?goodsId=" + id + "&attributeValueIds=" + attrs,
                GetAllAttribute.class,
                new Listener<GetAllAttribute>() {
                    @Override
                    public void onResponse(GetAllAttribute response) {
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
                });

        MyApplication.getRequestQueue().add(request);
    }
}
