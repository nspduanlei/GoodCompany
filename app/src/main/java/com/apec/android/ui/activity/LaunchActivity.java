package com.apec.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.apec.android.R;
import com.apec.android.domain.user.User;
import com.apec.android.ui.activity.goods.GoodsActivity;
import com.apec.android.ui.activity.user.RegisterActivity;
import com.apec.android.ui.activity.user.RegisterFActivity;
import com.apec.android.ui.activity.user.ShoppingCartActivity;
import com.apec.android.ui.presenter.goods.GoodsPresenter;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 启动页
 */
public class LaunchActivity extends MVPBaseActivity<GoodsPresenter.IView, GoodsPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected GoodsPresenter createPresenter() {
        return new GoodsPresenter();
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
     * @param view
     */
    public void addData(View view) {
        User user = new User();
        user.setUsername("duanlei");
        user.setPassword("11111");
        user.saveThrows();
    }

    public void updateData(View view) {
        User user = new User();
        user.setUsername("duanlei1111");
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
}
