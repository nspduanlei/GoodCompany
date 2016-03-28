package com.apec.android.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apec.android.R;
import com.apec.android.ui.activity.goods.GoodsActivity;
import com.apec.android.ui.activity.user.RegisterFActivity;

/**
 * Created by duanlei on 2016/3/14.
 */
public class GuideActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }

    public void start(View view) {
        Intent intent = new Intent(this, GoodsActivity.class);
        startActivity(intent);
        this.finish();
    }
}
