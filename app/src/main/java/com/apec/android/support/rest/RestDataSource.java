package com.apec.android.support.rest;

import android.content.Context;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.goods.GetAllAttribute;
import com.apec.android.domain.entities.goods.GoodDetail;
import com.apec.android.domain.entities.goods.Goods;
import com.apec.android.domain.entities.transport.ArrivalTime;
import com.apec.android.domain.entities.transport.ReceiptDefault;
import com.apec.android.domain.entities.transport.ReceiptInfo;
import com.apec.android.domain.entities.transport.ReceiptList;
import com.apec.android.domain.entities.user.Areas;
import com.apec.android.domain.entities.user.UserBack;
import com.apec.android.domain.repository.GoodsRepository;
import com.apec.android.support.rest.interceptors.CacheInterceptor;
import com.apec.android.support.rest.interceptors.HeaderInterceptor;
import com.apec.android.support.rest.interceptors.LoggingInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RestDataSource implements GoodsRepository {

    private static final long HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;

    public static String END_POINT = "http://shoptest.ap-ec.cn/testapi/";
    private final GoodsApi mGoodsApi;

    @Inject
    public RestDataSource(Context context) {

        //开启响应数据缓存到文件系统功能
        final File cacheDir = new File(context.getCacheDir(), "httpResponseCache");

        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor(context))
                .addInterceptor(new CacheInterceptor(context))
                .addNetworkInterceptor(new HeaderInterceptor(context))
                .addNetworkInterceptor(new StethoInterceptor()) //debug
                .cache(new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE))
                .build();

        Retrofit goodsApiAdapter = new Retrofit.Builder()
                .baseUrl(END_POINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        mGoodsApi = goodsApiAdapter.create(GoodsApi.class);
    }

    @Override
    public Observable<Goods> getGoods(int cid, int cityId) {
        return mGoodsApi.getCharacters(cid, cityId);
    }

    @Override
    public Observable<Areas> getCity() {
        return mGoodsApi.getCity();
    }

    @Override
    public Observable<NoBody> getVerCode(String mobile, int type) {
        return mGoodsApi.getVerCode(mobile, type);
    }

    @Override
    public Observable<UserBack> submitVerCode(String phone, String vCode) {
        return mGoodsApi.submitVerCode(phone, vCode);
    }

    @Override
    public Observable<NoBody> addShoppingCart(int skuId, int num) {
        return mGoodsApi.addShoppingCart(skuId, num);
    }

    @Override
    public Observable<GoodDetail> getGoodDetail(int goodsId) {
        return mGoodsApi.getGoodDetail(goodsId);
    }

    @Override
    public Observable<GetAllAttribute> getGoodAttrs(int goodId) {
        return mGoodsApi.getGoodAttrs(goodId);
    }

    @Override
    public Observable<ArrivalTime> getArrivalTime() {
        return mGoodsApi.getArrivalTime();
    }

    @Override
    public Observable<ReceiptList> getAllAddress() {
        return mGoodsApi.getAllAddress();
    }

    @Override
    public Observable<NoBody> setDefaultAddress(int addressId) {
        return mGoodsApi.setDefaultAddress(addressId);
    }

    @Override
    public Observable<NoBody> addReceiptInfo(ReceiptInfo receiptInfo) {
        return mGoodsApi.addReceiptInfo(receiptInfo.getPhone(), receiptInfo.getUserName(),
                receiptInfo.getAddressAreaCounty(), receiptInfo.getAddressCity(),
                receiptInfo.getDetailAddress());
    }

    @Override
    public Observable<NoBody> delAddress(int addressId) {
        return mGoodsApi.delAddress(addressId);
    }

    @Override
    public Observable<NoBody> updateReceiptInfo(ReceiptInfo receiptInfo) {
        return mGoodsApi.updateReceiptInfo(receiptInfo.getAddressId(), receiptInfo.getPhone(),
                receiptInfo.getUserName(), receiptInfo.getAddressAreaCounty(),
                receiptInfo.getAddressCity(), receiptInfo.getDetailAddress());
    }

    @Override
    public Observable<ReceiptDefault> getDefaultAddress() {
        return mGoodsApi.getDefaultAddress();
    }
}
