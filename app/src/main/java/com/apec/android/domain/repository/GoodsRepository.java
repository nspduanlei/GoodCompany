package com.apec.android.domain.repository;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.goods.Goods;
import com.apec.android.domain.entities.user.Areas;
import com.apec.android.domain.entities.user.UserBack;

import rx.Observable;

/**
 * Created by duanlei on 2016/5/10.
 */
public interface GoodsRepository {
    Observable<Goods> getGoods(final int cid, final int cityId);
    Observable<Areas> getCity();
    Observable<NoBody> getVerCode(final String mobile, final int type);
    Observable<UserBack> submitVerCode(final String phone, final String vCode);
}
