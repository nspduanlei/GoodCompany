package com.apec.android.config;

/**
 * 常量类
 * Created by Administrator on 2016/2/25.
 */
public class Constants {

    //测试接口地址
    //public final static String TEST_BASE_URL = "http://shoptest.ap-ec.cn/api/mall/";
    //public static final String TEST_BASE_URL = "http://shoptest.ap-ec.cn/testapi/";
    public static final String TEST_BASE_URL = "http://192.168.8.104:8080/";

    //正式接口地址
    public static final String OFFICIAL_BASE_URL = "";

    //商品最大购买数量
    public static final int MAX_GOODS_COUNT = 1000;

    public static final int SUCCESS_CODE = 200;

    /**
     * result
     */
    public static final int RESULT_CODE_LOGIN_SUCCESS = 100; //登录成功返回
    public static final int RESULT_CODE_EDIT = 102;  //地址修改成功
    public static final int RESULT_CODE_ADDRESS_CHANGE = 103; //切换地址
    public static final int RESULT_CODE_UPDATE_ADDRESS_SUCCESS = 104; //添加或者修改地址成功
    public static final int RESULT_CODE_CANCEL_ORDER = 105; //取消订单
    public static final int RESULT_CODE_SELECT_ADDRESS = 106; // 选择地址成功
    public static final int RESULT_CODE_COMPLETE_SUCCESS = 107; //完善资料成功
    public static final int RESULT_CODE_ORDER_SUCCESS = 108; //下单成功
    public static final int RESULT_CODE_SET_DEFAULT_ADDR = 109; //默认地址设置成功返回
    public static final int RESULT_CODE_EDIT_USER_DATA_SUCCESS = 110; //编辑用户资料成功
    /**
     * request
     */
    public static final int REQUEST_CODE_LOGIN = 1001; //登录
    public static final int REQUEST_CODE_ADDR = 1002;  //地址管理
    public static final int REQUEST_CODE_LOGIN_PAY = 1003; //支付
    public static final int REQUEST_CODE_EDIT = 1004; //地址详情修改
    public static final int REQUEST_CODE_UPDATE_ADDRESS = 1005; //添加或者修改地址
    public static final int REQUEST_CODE_ORDER_DETAIL = 1006; //跳转到订单详情
    public static final int REQUEST_CODE_LOGIN_ORDER = 1007; //登录, 从下单去登录
    public static final int REQUEST_CODE_COMPLETE = 1008; //登录 到完善资料页
    public static final int REQUEST_CODE_TRUE_ORDER = 1009; //跳转到确认订单
    public static final int REQUEST_CODE_EDIT_USER_DATA = 1010; //编辑用户资料

    /**
     * default
     */
    public static final int DEFAULT_CITY_ID = 100;
    public static final String DEFAULT_CITY_NAME = "深圳市";
}
