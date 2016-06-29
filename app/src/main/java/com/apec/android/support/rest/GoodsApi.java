package com.apec.android.support.rest;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.goods.GetAllAttribute;
import com.apec.android.domain.entities.goods.GoodDetail;
import com.apec.android.domain.entities.goods.SkuList;
import com.apec.android.domain.entities.order.OrderBack;
import com.apec.android.domain.entities.order.OrderListBack;
import com.apec.android.domain.entities.transport.ArrivalTime;
import com.apec.android.domain.entities.transport.ReceiptDefault;
import com.apec.android.domain.entities.transport.ReceiptList;
import com.apec.android.domain.entities.transport.TransportInfo;
import com.apec.android.domain.entities.user.Areas;
import com.apec.android.domain.entities.user.OpenCity;
import com.apec.android.domain.entities.user.ShopCartBack;
import com.apec.android.domain.entities.user.UserBack;
import com.apec.android.domain.entities.user.Version;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface GoodsApi {
    //@GET("list/goods")

    @GET("query/sku/by/categoryId")
    Observable<SkuList> getGoods(@Query("categoryId") int cid, @Query("cityId") int cityId);

    @GET("citys")
    Observable<Areas> getCity();

    @FormUrlEncoded
    @POST("sms/getcode")
    Observable<NoBody> getVerCode(@Field("mobile") String mobile, @Field("type") int type);

    @FormUrlEncoded
    @POST("user/sms/login")
    Observable<UserBack> submitVerCode(@Field("phone") String phone, @Field("code") String code);

    @FormUrlEncoded
    @POST("cart/add")
    Observable<NoBody> addShoppingCart(@Field("skuId") int skuId, @Field("num") int num);

    @GET("goods")
    Observable<GoodDetail> getGoodDetail(@Query("id") int goodsId);

    @GET("goods/all/attribute")
    Observable<GetAllAttribute> getGoodAttrs(@Query("id") int goodsId);

    @GET("arrivaltime")
    Observable<ArrivalTime> getArrivalTime();

    @GET("address/all/")
    Observable<ReceiptList> getAllAddress();

    @FormUrlEncoded
    @POST("address/default/set")
    Observable<NoBody> setDefaultAddress(@Field("id") int addressId);

    @FormUrlEncoded
    @POST("address/add/")
    Observable<NoBody> addReceiptInfo(@Field("takeGoodsPhone") String phone,
                                      @Field("takeGoodsUser") String userName,
                                      @Field("addreAreacounty") int addressAreaCounty,
                                      @Field("addreCity") int addressCity,
                                      @Field("addreDetailAddress") String detailAddress);

    @FormUrlEncoded
    @POST("address/del")
    Observable<NoBody> delAddress(@Field("id") int addressId);

    @FormUrlEncoded
    @POST("address/update")
    Observable<NoBody> updateReceiptInfo(@Field("addressId") int addressId,
                                         @Field("takeGoodsPhone") String phone,
                                         @Field("takeGoodsUser") String userName,
                                         @Field("addreAreacounty") int addressAreaCounty,
                                         @Field("addreCity") int addressCity,
                                         @Field("addreDetailAddress") String detailAddress);
    @GET("address/default")
    Observable<ReceiptDefault> getDefaultAddress();

    @FormUrlEncoded
    @POST("order/addBatch")
    Observable<NoBody> createOrder(@Field("orderJson") String json);

    @GET("orders")
    Observable<OrderListBack> getAllOrder(@Query("state") int state);

    @GET("order")
    Observable<OrderBack> getOrderDetail(@Query("id") int orderId);

    @GET("order/cancel")
    Observable<NoBody> cancelOrder(@Query("id") int orderId);

    @GET("cart/items")
    Observable<ShopCartBack> getAllCart(@Query("cityId") int cityId);

    @FormUrlEncoded
    @POST("cart/del")
    Observable<NoBody> deleteCart(@Query("skuId") int skuId);

    @GET("citymaping.json")
    Observable<ArrayList<OpenCity>> cityIsOpen();

    @FormUrlEncoded
    @POST("order/add")
    Observable<NoBody> createOneOrder(@Field("skuId") int skuId,
                                      @Field("addressId") int addressId,
                                      @Field("num") int num);
    @GET("area")
    Observable<Areas> getArea(@Query("id") int id);

    @FormUrlEncoded
    @POST("user/complete")
    Observable<NoBody> completeUser(@Field("userShop") String shopName,
                                    @Field("userName") String name,
                                    @Field("userCity") String cityId,
                                    @Field("userAreacounty") String areaId,
                                    @Field("userAddress") String detail);

    @FormUrlEncoded
    @POST("cart/addBatch")
    Observable<NoBody> addBatchShoppingCart(@Field("skuJson") String json);

    @FormUrlEncoded
    @POST("user/update")
    Observable<NoBody> updateUser(@Field("userPhone") String phone,
                                  @Field("userShop") String shop,
                                  @Field("userName") String userName);

    @GET("appVersion")
    Observable<Version> getVersion();

    @GET("logistics")
    Observable<TransportInfo> getTransport(@Query("orderId") int orderId);
}
