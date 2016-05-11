package com.apec.android.support.rest;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.goods.Goods;
import com.apec.android.domain.entities.user.Areas;
import com.apec.android.domain.entities.user.UserBack;
import com.apec.android.domain.repository.GoodsRepository;
import javax.inject.Inject;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RestDataSource implements GoodsRepository {
    public static String END_POINT = "http://shoptest.ap-ec.cn/testapi/";
    private final GoodsApi mGoodsApi;

    @Inject
    public RestDataSource() {
        OkHttpClient client = new OkHttpClient();

//        GoodsInterceptor signingInterceptor =
//                new GoodsInterceptor("", "");
//        client.interceptors().add(signingInterceptor);

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
}
