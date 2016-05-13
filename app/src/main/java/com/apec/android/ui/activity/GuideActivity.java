package com.apec.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.apec.android.R;
import com.apec.android.ui.activity.goods.GoodsActivity;

/**
 * Created by duanlei on 2016/3/14.
 */
public class GuideActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        new Handler().postAtTime(new Runnable() {
            @Override
            public void run() {
                start(null);
            }
        }, 3000);
    }

    public void start(View view) {
        Intent intent = new Intent(this, GoodsActivity.class);
        startActivity(intent);
        this.finish();
    }
}
