package com.apec.android.domain.goods.interator;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.apec.android.app.MyApplication;
import com.apec.android.config.UrlConstant;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.goods.Goods;
import com.apec.android.support.http.request.GsonRequest;
import com.apec.android.support.test;


/**
 * Created by Administrator on 2016/2/26.
 */
public class GetGoodsInterator {
    /**
     * 获取所以的类型
     * @param callback
     */
    public static void fecthCategorys(final GetDataCallback<test> callback) {
        GsonRequest<test> request = new GsonRequest<test>(
                UrlConstant.URL_CATEGORY, test.class,

                new Response.Listener<test>() {
                    @Override
                    public void onResponse(test response) {
                        callback.onRepose(response);
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
    public static void fecthGoods(final GetDataCallback<Goods> callback) {
        GsonRequest<Goods> request = new GsonRequest<Goods>(
                UrlConstant.URL_GOODS, Goods.class,

                new Response.Listener<Goods>() {
                    @Override
                    public void onResponse(Goods response) {
                        callback.onRepose(response);
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
