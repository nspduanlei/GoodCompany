package com.apec.android.views.activities;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.views.activities.core.BaseActivity;

/**
 * Created by duanlei on 2016/6/10.
 */
public class ServiceActivity extends BaseActivity {
    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_service, R.string.setting_title);
    }

    @Override
    protected void initUi() {

    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }
}
