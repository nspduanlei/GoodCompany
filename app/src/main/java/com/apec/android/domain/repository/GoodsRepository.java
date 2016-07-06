package com.apec.android.domain.repository;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.goods.GetAllAttribute;
import com.apec.android.domain.entities.goods.GoodDetail;
import com.apec.android.domain.entities.goods.Goods;
import com.apec.android.domain.entities.goods.SkuList;
import com.apec.android.domain.entities.order.OrderBack;
import com.apec.android.domain.entities.order.OrderListBack;
import com.apec.android.domain.entities.transport.ArrivalTime;
import com.apec.android.domain.entities.transport.ReceiptDefault;
import com.apec.android.domain.entities.transport.ReceiptInfo;
import com.apec.android.domain.entities.transport.ReceiptList;
import com.apec.android.domain.entities.transport.TransportInfo;
import com.apec.android.domain.entities.user.Areas;
import com.apec.android.domain.entities.user.OpenCity;
import com.apec.android.domain.entities.user.ShopCartBack;
import com.apec.android.domain.entities.user.UserBack;
import com.apec.android.domain.entities.user.Version;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by duanlei on 2016/5/10.
 */
public interface GoodsRepository {
    /**
     * 获取是商品列表
     *
     * @param cid    类型id
     * @param cityId 开放城市id
     * @return
     */
    Observable<SkuList> getGoods(final int cid, final int cityId);

    /**
     * 获取开放城市
     *
     * @return
     */
    Observable<Areas> getCity();

    /**
     * 获取验证码
     *
     * @param mobile
     * @param type
     * @return
     */
    Observable<NoBody> getVerCode(final String mobile, final int type);

    /**
     * 提交验证码
     *
     * @param phone
     * @param vCode
     * @return
     */
    Observable<UserBack> submitVerCode(final String phone, final String vCode);

    /**
     * 加入购物车
     *
     * @param skuId
     * @param num
     * @return
     */
    Observable<NoBody> addShoppingCart(final int skuId, final int num);

    /**
     * 获取商品详情
     *
     * @param goodsId
     * @return
     */
    Observable<GoodDetail> getGoodDetail(final int goodsId);

    /**
     * 获取商品全部规格属性
     *
     * @param goodId
     * @return
     */
    Observable<GetAllAttribute> getGoodAttrs(final int goodId);

    /**
     * 获取到货时间
     *
     * @return
     */
    Observable<ArrivalTime> getArrivalTime();

    /**
     * 获取全部地址
     *
     * @return
     */
    Observable<ReceiptList> getAllAddress();

    /**
     * 设置默认地址
     *
     * @param addressId
     * @return
     */
    Observable<NoBody> setDefaultAddress(final int addressId);

    /**
     * 添加收货信息
     *
     * @param receiptInfo
     * @return
     */
    Observable<NoBody> addReceiptInfo(final ReceiptInfo receiptInfo);

    /**
     * 删除地址
     *
     * @param addressId
     * @return
     */
    Observable<NoBody> delAddress(final int addressId);

    /**
     * 编辑收货信息
     *
     * @param receiptInfo
     * @return
     */
    Observable<NoBody> updateReceiptInfo(final ReceiptInfo receiptInfo);

    /**
     * 获取默认收货信息
     *
     * @return
     */
    Observable<ReceiptDefault> getDefaultAddress();


    /************************************* 订单 **************************************/

    /**
     * 创建订单
     * @param json
     * @return
     */
    Observable<NoBody> createOrder(final String json);

    /**
     * 获取我的订单列表
     * @return
     */
    Observable<OrderListBack> getAllOrder(int state);

    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    Observable<OrderBack> getOrderDetail(String orderId);

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    Observable<NoBody> cancelOrder(String orderId);

    /*********************************购物车*************************************/
    /**
     * 获取我的购物车
     * @return
     */
    Observable<ShopCartBack> getAllCart(int cityId);

    /**
     * 删除购物车
     * @param skuId
     * @return
     */
    Observable<NoBody> deleteCart(int skuId);


    Observable<ArrayList<OpenCity>> cityIsOpen();


    //修改用户信息
    Observable<NoBody> updateUser(String phone, String shop, String userName);

    //快速创建订单
    Observable<NoBody> createOneOrder(int skuId, int addressId, int num);

    //地区选择
    Observable<Areas> getArea(int id);

    Observable<NoBody> completeUser(String shopName, String name, String cityId, String areaId, String detail);

    //批量加入购物车
    Observable<NoBody> addBatchShoppingCart(String json);

    //获取最新的版本信息
    Observable<Version> getVersion();

    Observable<TransportInfo> getTransport(String orderId);

    //上传参数
    Observable<NoBody> uploadArgument(String name, int nameType);
}