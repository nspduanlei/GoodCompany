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

}
