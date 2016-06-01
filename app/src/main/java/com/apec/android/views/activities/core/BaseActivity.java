package com.apec.android.views.activities.core;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by duanlei on 2016/5/10.
 */
public abstract class BaseActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    protected TextView mToolbarTitle;
    protected Toolbar mToolbar;


    public static final int MODE_BACK = 0;
    public static final int MODE_DRAWER = 1;
    public static final int MODE_NONE = 2;
    public static final int MODE_HOME = 3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication myApplication = (MyApplication) getApplication();
        initDependencyInjector(myApplication);

        setUpContentView();

        initUi();

        initPresenter();
    }

    //设置布局，在里面调用setContentView方法
    protected abstract void setUpContentView();

    //初始化视图
    protected abstract void initUi();

    //初始化依赖，方便后面的对象注入
    protected abstract void initDependencyInjector(MyApplication application);

    //初始化Presenter
    protected abstract void initPresenter();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(layoutResID, -1, -1, MODE_BACK);
    }

    public void setContentView(int layoutResID, int titleResId) {
        setContentView(layoutResID, titleResId, -1, MODE_BACK);
    }

    public void setContentView(int layoutResID, int titleResId, int mode) {
        setContentView(layoutResID, titleResId, -1, mode);
    }

    public void setContentView(int layoutResID, int titleResId, int menuId, int mode) {
        super.setContentView(layoutResID);

        ButterKnife.bind(this);
        setUpToolbar(titleResId, menuId, mode);
    }

    protected void setUpToolbar(int titleResId, int menuId, int mode) {
        if (mode != MODE_NONE) {

            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);

            if (mode == MODE_BACK) {
                mToolbar.setNavigationIcon(R.drawable.arrow_top);
            }
            mToolbar.setNavigationOnClickListener(view -> onNavigationBtnClicked());
            setUpTitle(titleResId);
            setUpMenu(menuId);
        }
    }

    protected void setUpTitle(int titleResId) {
        if (titleResId > 0 && mToolbarTitle != null) {
            mToolbarTitle.setText(titleResId);
        }
    }

    protected void setUpMenu(int menuId) {
        if (mToolbar != null) {
            mToolbar.getMenu().clear();
            if (menuId > 0) {
                mToolbar.inflateMenu(menuId);
                mToolbar.setOnMenuItemClickListener(this);
            }
        }
    }

    protected void onNavigationBtnClicked() {
        finish();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
