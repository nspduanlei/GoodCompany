package com.apec.android.support.rest;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.goods.GetAllAttribute;
import com.apec.android.domain.entities.goods.Goods;
import com.apec.android.domain.entities.goods.GoodDetail;
import com.apec.android.domain.entities.transport.ArrivalTime;
import com.apec.android.domain.entities.user.Areas;
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
}
