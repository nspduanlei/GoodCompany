package com.apec.android.support.rest;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.goods.Goods;
import com.apec.android.domain.entities.user.Areas;
import com.apec.android.domain.entities.user.UserBack;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface GoodsApi {
    @GET("list/goods")
    Observable<Goods> getCharacters(@Query("categoryId") int cid, @Query("cityId") int cityId);

    @GET("citys")
    Observable<Areas> getCity();

    @POST("sms/getcode")
    Observable<NoBody> getVerCode(@Query("mobile") String mobile, @Query("type") int type);

    @POST("user/sms/login")
    Observable<UserBack> submitVerCode(@Query("phone") String phone, @Query("code") String code);
}
