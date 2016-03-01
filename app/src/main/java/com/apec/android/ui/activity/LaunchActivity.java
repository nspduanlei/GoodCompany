package com.apec.android.ui.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.apec.android.R;
import com.apec.android.domain.user.User;
import com.apec.android.ui.activity.goods.GoodsActivity;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

/**
 * 启动页
 */
public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        Toast.makeText(this, "登录", Toast.LENGTH_SHORT).show();
    }

    public void share(View view) {
        Toast.makeText(this, "登录", Toast.LENGTH_SHORT).show();
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

    public void gotoGoods(View view){
        Intent intent = new Intent(this, GoodsActivity.class);
        startActivity(intent);
    }

}
