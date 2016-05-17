package com.apec.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.apec.android.R;
import com.apec.android.domain.entities.user.User;
import com.apec.android.ui.activity.order.MyOrdersActivity;
import com.apec.android.ui.activity.order.OrderActivity;
import com.apec.android.ui.activity.user.EditDataActivity;
import com.apec.android.ui.activity.user.ManageAddrActivity;
import com.apec.android.ui.activity.user.RegisterActivity;
import com.apec.android.ui.activity.user.RegisterFActivity;
import com.apec.android.ui.activity.user.ShoppingCartActivity;
import com.apec.android.ui.presenter.user.LaunchPresenter;
import com.apec.android.util.SPUtils;
import com.apec.android.views.activities.GoodsActivity;
import com.umeng.analytics.AnalyticsConfig;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 启动页
 */
public class LaunchActivity extends MVPBaseActivity<LaunchPresenter.IView, LaunchPresenter>
        implements LaunchPresenter.IView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        /** 设置是否对日志信息进行加密, 默认false(不加密). */
        //AnalyticsConfig.enableEncrypt(true);

        //是否第一次登录
        if ((int)SPUtils.get(this, SPUtils.IS_FIRST_LAUNCH, 0) == 0) {
            SPUtils.put(this, SPUtils.IS_FIRST_LAUNCH, 1);
            //是第一次进入app，到引导页
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
        } else {
//            //不是第一次进入，判断是否登录
//            if (StringUtils.isNullOrEmpty(
//                    (String) SPUtils.get(this, SPUtils.SESSION_ID, ""))) {
//                //session_id为空没有登录，进入登录页面
//                Intent intent = new Intent(this, RegisterFActivity.class);
//                startActivity(intent);
//            } else {
//                //session_id存在，进入商品展示页
//                Intent intent = new Intent(this, GoodsActivity.class);
//                startActivity(intent);
//            }
            Intent intent = new Intent(this, GoodsActivity.class);
            startActivity(intent);
        }
        this.finish();
    }

    @Override
    protected LaunchPresenter createPresenter() {
        return new LaunchPresenter(this);
    }

    public void login(View view) {
        Intent intent = new Intent(this, RegisterFActivity.class);
        startActivity(intent);
    }

    public void share(View view) {
        Toast.makeText(this, "登录", Toast.LENGTH_SHORT).show();
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterFActivity.class);
        startActivity(intent);
    }

    /**
     * sqlite添加数据测试
     *
     * @param view
     */
    public void addData(View view) {
        User user = new User();
        user.setName("duanlei");
        user.setPhone("11111");
        user.saveThrows();
    }

    public void updateData(View view) {
        User user = new User();
        user.setName("duanlei1111");
        user.update(10);
    }

    public void deleteData(View view) {
        DataSupport.delete(User.class, 10);
    }

    public void queryData(View view) {
        //User user = DataSupport.find(User.class, 10);
        User user = DataSupport.findFirst(User.class);
        List<User> users = DataSupport.findAll(User.class);
        Toast.makeText(this, user.toString() + "-------------" + users.size(), Toast.LENGTH_LONG).show();
    }

    public void gotoGoods(View view) {
        Intent intent = new Intent(this, GoodsActivity.class);
        startActivity(intent);
    }

    public void registerData(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void shoppingCart(View view) {
        Intent intent = new Intent(this, ShoppingCartActivity.class);
        startActivity(intent);
    }

    public void order(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    public void myOrder(View view) {
        Intent intent = new Intent(this, MyOrdersActivity.class);
        startActivity(intent);
    }

    public void editData(View view) {
        Intent intent = new Intent(this, EditDataActivity.class);
        startActivity(intent);
    }

    public void manageData(View view) {
        Intent intent = new Intent(this, ManageAddrActivity.class);
        startActivity(intent);
    }
}
