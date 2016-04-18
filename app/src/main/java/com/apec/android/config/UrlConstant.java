package com.apec.android.config;

/**
 * Created by Administrator on 2016/2/25.
 */
public class UrlConstant {

    public static final String URL_BASE = Constants.TEST_BASE_URL;

    //商品类别
    public static final String URL_CATEGORY = URL_BASE + "/category/all";

    //商品列表
    public static final String URL_GOODS = URL_BASE + "/list/goods";

    //商品详情
    public static final String URL_GOODS_DETAIL = URL_BASE + "/goods";

    //获取短信验证码
    public static final String URL_SMS_GETCODE = URL_BASE + "/sms/getcode";

    //提交短信验证码
    public static final String URL_SMS_LOGIN = URL_BASE + "/user/sms/login";

    //地区选择
    public static final String URL_AREA = URL_BASE + "/area";

    //完善资料
    public static final String URL_COMPLETE = URL_BASE + "/user/complete";

    //获取商品全部规格属性
    public static final String URL_ALL_ARRT = URL_BASE + "/goods/all/attribute";

    //根据规格属性获取sku
    public static final String URL_SKU_ATTR = URL_BASE + "/query/sku/by/attribute";

    //加入购物车
    public static final String URL_ADD_SHOPPING_CART = URL_BASE + "/cart/add";

    //获取购物车
    public static final String URL_OBTAIN_SHOPPING_CART = URL_BASE + "/cart/items";

    //获取全部地址
    public static final String URL_OBTAIN_ADDRESS_ALL = URL_BASE + "/address/all/";

    //新增收货地址
    public static final String URL_ADD_ADDRESS = URL_BASE + "/address/add/";

    //设置默认收货地址
    public static final String URL_DEFAULT_ADDRESS = URL_BASE + "/address/default/set";

    //删除收货地址
    public static final String URL_DELETE_ADDRESS = URL_BASE + "/address/del";

    //更新收货地址
    public static final String URL_UPDATE_ADDRESS = URL_BASE + "/address/update";


    //获取默认收货地址
    public static final String URL_GET_DEFAULT_ADDRESS = URL_BASE + "/address/default";

    //购物车创建订单
    public static final String URL_CART_CREATE_ORDER = URL_BASE + "/cart/create/order";

    //我的订单列表
    public static final String URL_MY_ORDERS = URL_BASE + "/orders";

    //我的订单列表
    public static final String URL_ORDER = URL_BASE + "/order";

    //取消订单
    public static final String URL_CANCEL_ORDER = URL_BASE + "/order/cancel";

    //获取用户信息
    public static final String URL_USER_INFO = URL_BASE + "/user";

    //修改用户信息
    public static final String URL_UPDATE_USER_INFO = URL_BASE + "/user/update";

    //获取到货时间
    public static final String URL_ARRIVALTIME = URL_BASE + "/arrivaltime";

    //删除购物车
    public static final String URL_DEL_SHOPPING_CART = URL_BASE + "/cart/del";
}
