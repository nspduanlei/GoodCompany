package com.apec.android.config;

/**
 * 常量类
 * Created by Administrator on 2016/2/25.
 */
public class Constants {

    //测试接口地址
    //public final static String TEST_BASE_URL = "http://shoptest.ap-ec.cn/api/mall";
    public final static String TEST_BASE_URL = "http://shoptest.ap-ec.cn/testapi";

    //正式接口地址
    public final static String OFFICIAL_BASE_URL = "";

    //商品最大购买数量
    public final static int MAX_GOODS_COUNT = 100;

    /**
     * result
     */
    public final static int RESULT_CODE_LOGIN_SUCCESS = 100; //登录成功返回
    public final static int RESULT_CODE_ADDRESS_SUCCESS = 101; //默认地址设置成功返回
    public final static int RESULT_CODE_EDIT = 102;  //地址修改成功


    /**
     * request
     */
    public final static int REQUEST_CODE_LOGIN = 1001; //登录
    public final static int REQUEST_CODE_ADDR = 1002;  //地址管理
    public final static int REQUEST_CODE_LOGIN_PAY = 1003; //支付
    public final static int REQUEST_CODE_EDIT = 1004; //地址详情修改

    /**
     * default
     */
    public final static int DEFAULT_CITY_ID = 100;
    public final static String DEFAULT_CITY_NAME = "深圳市";
}
