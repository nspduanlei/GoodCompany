package com.apec.android.views.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.databinding.ActivityCompleteBinding;
import com.apec.android.views.activities.core.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/6/10.
 */
public class CompleteActivity extends BaseActivity {

    ActivityCompleteBinding mBinding;

    @Override
    protected void setUpContentView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_complete);
        ButterKnife.bind(this);
        setUpToolbar(R.string.title_complete, -1, MODE_BACK);
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

    @OnClick(R.id.btn_finish)
    void onFinishClicked(View view) {
        setResult(Constants.RESULT_CODE_COMPLETE_SUCCESS);
        finish();
    }

}
