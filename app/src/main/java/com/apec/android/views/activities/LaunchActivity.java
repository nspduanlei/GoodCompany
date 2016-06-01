package com.apec.android.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.views.activities.core.BaseActivity;

/**
 * Created by duanlei on 2016/5/11.
 */
public class LaunchActivity extends BaseActivity {

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_launch, -1, MODE_NONE);
    }

    @Override
    protected void initUi() {
        initIntent();
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

    private void initIntent() {
        //是否第一次登录
//        if ((int) SPUtils.get(this, SPUtils.IS_FIRST_LAUNCH, 0) == 0) {
//            SPUtils.put(this, SPUtils.IS_FIRST_LAUNCH, 1);
//            //是第一次进入app，到引导页
//            Intent intent = new Intent(this, GuideActivity.class);
//            startActivity(intent);
//        } else {
//            new Handler().postDelayed(() -> {
//                Intent intent = new Intent(LaunchActivity.this, GoodsActivity.class);
//                startActivity(intent);
//            }, 2000);
//        }

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(LaunchActivity.this, GoodsActivity.class);
            startActivity(intent);

            this.finish();
        }, 2000);


    }
}
