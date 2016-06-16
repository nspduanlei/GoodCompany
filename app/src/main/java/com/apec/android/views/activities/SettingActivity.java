package com.apec.android.views.activities;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.utils.FileUtils;
import com.apec.android.views.utils.LoginUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/6/10.
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.btn_login_out)
    Button mBtnLoginOut;
    @BindView(R.id.tv_cache_num)
    TextView mTvCacheNum;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_setting, R.string.setting_title);
    }

    @Override
    protected void initUi() {
        File cacheDir = getCacheDir();
        mTvCacheNum.setText(FileUtils.getAutoFileOrFilesSize(cacheDir));

        if (!LoginUtil.isLogin(this)) {
            mBtnLoginOut.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

    @OnClick(R.id.btn_login_out)
    void onLoginOutClicked(View view) {
        LoginUtil.showLoginOutDialog(this, view);
    }

    @OnClick(R.id.rl_cache)
    void onClearCacheClicked(View view) {
        FileUtils.delete(getCacheDir());
        mTvCacheNum.setText("0M");
    }

}
