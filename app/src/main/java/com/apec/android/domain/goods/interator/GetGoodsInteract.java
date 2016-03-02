package com.apec.android.domain.goods.interator;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.apec.android.app.MyApplication;
import com.apec.android.config.UrlConstant;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.goods.Goods;
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
     * @param callback
     */
    public static void fetchCategorys(final GetDataCallback<test> callback) {
        GsonRequest<test> request = new GsonRequest<test>(
                UrlConstant.URL_CATEGORY, test.class,

                new Listener<test>() {
                    @Override
                    public void onResponse(test response) {
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
     * @param callback
     */
    public static void fetchGoods(final GetDataCallback<Goods> callback) {
        GsonRequest<Goods> request = new GsonRequest<>(
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
     * @param callback
     * @param id 商品id
     */
    public static void fetchGoodsDetail(final GetDataCallback<ModelTest> callback,
                                        final int id, final int originId) {
        GsonRequest<ModelTest> request = new GsonRequest<>(
                UrlConstant.URL_GOODS, ModelTest.class,

                new Listener<ModelTest>() {
                    @Override
                    public void onResponse(ModelTest response) {
                        callback.onRepose(response);
                    }

                    @Override
                    public Map getRequestParams() {
                        Map<String,String> params = new HashMap<>();
                        params.put("id", String.valueOf(id));
                        params.put("originId", String.valueOf(originId));
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

}
