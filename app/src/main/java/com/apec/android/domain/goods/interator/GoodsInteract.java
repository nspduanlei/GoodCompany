package com.apec.android.domain.goods.interator;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.config.UrlConstant;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.goods.GateGory;
import com.apec.android.domain.goods.GetAllAttribute;
import com.apec.android.domain.goods.Goods;
import com.apec.android.domain.goods.GoodsDetail;
import com.apec.android.domain.transport.ArrivalTime;
import com.apec.android.domain.user.Areas;
import com.apec.android.support.http.Listener;
import com.apec.android.support.http.request.GsonRequest;
import com.apec.android.util.L;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2016/2/26.
 */
public class GoodsInteract {
    /**
     * 获取所用的类型
     *
     * @param callback
     */
    public static void fetchCategory(Context context, final GetDataCallback<GateGory> callback) {
        GsonRequest<GateGory> request = new GsonRequest<>(
                context, Request.Method.POST,
                UrlConstant.URL_CATEGORY, GateGory.class,
                new Listener<GateGory>() {
                    @Override
                    public void onResponse(GateGory response) {
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
    public static void fetchGoods(Context context,
                                  final GetDataCallback<Goods> callback,
                                  final int cid,
                                  final int cityId) {
        L.e("test0001", "cid===" + cid + ",  cityId===" + cityId);

        GsonRequest<Goods> request = new GsonRequest<>(
                context, Request.Method.GET,
                UrlConstant.URL_GOODS + "?categoryId=" + cid + "&cityId" + cityId,
                Goods.class,
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


    /**
     * 购物车添加商品或修改商品数量
     *
     * @param context
     * @param callback
     * @param s
     * @param num
     */
    public static void addShoppingCart(Context context,
                                       final GetDataCallback<NoBody> callback,
                                       final String s, final String num) {
        GsonRequest<NoBody> request = new GsonRequest<>(
                context, Request.Method.POST,
                UrlConstant.URL_ADD_SHOPPING_CART,
                NoBody.class,
                new Listener<NoBody>() {
                    @Override
                    public void onResponse(NoBody response) {
                        callback.onRepose(response);
                    }

                    @Override
                    public Map getRequestParams() {
                        L.e("test002", "skuId-->" + s + "|num--->" + num);

                        Map<String, String> params = new HashMap<>();
                        params.put("skuId", s);
                        params.put("num", num);
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
     * 获取到货时间
     *
     * @param context
     * @param callback
     */
    public static void getArrivalTime(Context context, final GetDataCallback<ArrivalTime> callback) {
        GsonRequest<ArrivalTime> request = new GsonRequest<>(
                context, Request.Method.GET,
                UrlConstant.URL_ARRIVALTIME,
                ArrivalTime.class,
                new Listener<ArrivalTime>() {
                    @Override
                    public void onResponse(ArrivalTime response) {
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
     * 删除购物车商品
     *
     * @param context
     * @param callback
     * @param skuId
     */
    public static void deleteShoppingCart(Context context, final GetDataCallback<NoBody> callback,
                                          final String skuId) {
        GsonRequest<NoBody> request = new GsonRequest<>(
                context, Request.Method.POST,
                UrlConstant.URL_DEL_SHOPPING_CART,
                NoBody.class,
                new Listener<NoBody>() {
                    @Override
                    public void onResponse(NoBody response) {
                        callback.onRepose(response);
                    }

                    @Override
                    public Map getRequestParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("skuId", skuId);
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
     * 获取开发城市列表
     *
     * @param callback
     */
    public static void getOpenCityList(final GetDataCallback<JSONObject> callback) {
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(UrlConstant.URL_OPEN_CITY_CODE, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("test01", response.toString());
                                callback.onRepose(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onErrorResponse(error);
                            }
                        });

        MyApplication.getRequestQueue().add(jsonObjectRequest);
    }


    /**
     * 获取已开放的城市（用于主页选择城市）
     * @param context
     * @param callback
     */
    public static void getOpenCityListArea(Context context,
                                           final GetDataCallback<Areas> callback) {
        GsonRequest<Areas> request = new GsonRequest<>(
                context, Request.Method.GET,
                UrlConstant.URL_SELECT_CITY,
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
                });

        MyApplication.getRequestQueue().add(request);
    }
}
