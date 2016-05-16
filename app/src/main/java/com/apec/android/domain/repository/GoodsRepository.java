package com.apec.android.domain.repository;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.goods.GetAllAttribute;
import com.apec.android.domain.entities.goods.Goods;
import com.apec.android.domain.entities.goods.GoodDetail;
import com.apec.android.domain.entities.transport.ArrivalTime;
import com.apec.android.domain.entities.user.Areas;
import com.apec.android.domain.entities.user.UserBack;

import rx.Observable;

/**
 * Created by duanlei on 2016/5/10.
 */
public interface GoodsRepository {
    /**
     * 获取是商品列表
     * @param cid 类型id
     * @param cityId  开放城市id
     * @return
     */
    Observable<Goods> getGoods(final int cid, final int cityId);

    /**
     * 获取开放城市
     * @return
     */
    Observable<Areas> getCity();

    /**
     * 获取验证码
     * @param mobile
     * @param type
     * @return
     */
    Observable<NoBody> getVerCode(final String mobile, final int type);

    /**
     * 提交验证码
     * @param phone
     * @param vCode
     * @return
     */
    Observable<UserBack> submitVerCode(final String phone, final String vCode);

    /**
     * 加入购物车
     * @param skuId
     * @param num
     * @return
     */
    Observable<NoBody> addShoppingCart(final int skuId, final int num);

    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    Observable<GoodDetail> getGoodDetail(final int goodsId);

    /**
     * 获取商品全部规格属性
     * @param goodId
     * @return
     */
    Observable<GetAllAttribute> getGoodAttrs(final int goodId);

    /**
     * 获取到货时间
     * @return
     */
    Observable<ArrivalTime> getArrivalTime();
}
