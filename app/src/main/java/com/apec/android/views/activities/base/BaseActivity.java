package com.apec.android.views.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.apec.android.app.MyApplication;

import butterknife.ButterKnife;

/**
 * Created by duanlei on 2016/5/10.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
        ButterKnife.bind(this);

        MyApplication myApplication = (MyApplication) getApplication();
        initDependencyInjector(myApplication);
        initPresenter();
    }

    protected abstract void initUi();
    protected abstract void initDependencyInjector(MyApplication application);
    protected abstract void initPresenter();
}
