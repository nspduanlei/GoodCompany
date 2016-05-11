package com.apec.android.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.apec.android.ui.activity.BaseActivity;
import com.apec.android.ui.activity.GuideActivity;
import com.apec.android.util.SPUtils;

/**
 * Created by duanlei on 2016/5/11.
 */
public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSetting();
        initIntent();
    }

    private void initSetting() {

    }

    private void initIntent() {
        //是否第一次登录
        if ((int) SPUtils.get(this, SPUtils.IS_FIRST_LAUNCH, 0) == 0) {
            SPUtils.put(this, SPUtils.IS_FIRST_LAUNCH, 1);
            //是第一次进入app，到引导页
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, GoodsActivity.class);
            startActivity(intent);
        }
        this.finish();
    }
}
