package com.apec.android.support.rest;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.goods.GetAllAttribute;
import com.apec.android.domain.entities.goods.Goods;
import com.apec.android.domain.entities.goods.GoodDetail;
import com.apec.android.domain.entities.order.OrderBack;
import com.apec.android.domain.entities.order.OrderListBack;
import com.apec.android.domain.entities.transport.ArrivalTime;
import com.apec.android.domain.entities.transport.ReceiptDefault;
import com.apec.android.domain.entities.transport.ReceiptInfo;
import com.apec.android.domain.entities.transport.ReceiptList;
import com.apec.android.domain.entities.user.Areas;
import com.apec.android.domain.entities.user.ShopCartBack;
import com.apec.android.domain.entities.user.UserBack;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface GoodsApi {
    @GET("list/goods")
    Observable<Goods> getCharacters(@Query("categoryId") int cid, @Query("cityId") int cityId);

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
    @POST("cart/create/order")
    Observable<NoBody> createOrder(@Field("skuIds") String skus, @Field("addressId") int addressId);

    @GET("orders")
    Observable<OrderListBack> getAllOrder();

    @GET("order")
    Observable<OrderBack> getOrderDetail(@Query("id") int orderId);

    @GET("order/cancel")
    Observable<NoBody> cancelOrder(@Query("id") int orderId);

    @GET("cart/items")
    Observable<ShopCartBack> getAllCart(@Query("cityId") int cityId);

    @FormUrlEncoded
    @POST("cart/del")
    Observable<NoBody> deleteCart(@Query("skuId") int skuId);

}
