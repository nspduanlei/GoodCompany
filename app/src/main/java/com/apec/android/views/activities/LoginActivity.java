package com.apec.android.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.injector.components.DaggerGoodsComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.LoginPresenter;
import com.apec.android.mvp.views.LoginView;
import com.apec.android.util.KeyBoardUtils;
import com.apec.android.util.L;
import com.apec.android.util.StringUtils;
import com.apec.android.util.T;
import com.apec.android.views.activities.base.BaseActivity;
import com.apec.android.views.utils.LoginHandler;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/5/11.
 */
public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.et_phone_number)
    EditText mEtPhoneNumber;

    @BindView(R.id.btn_get_code)
    public Button mBtnGetCode;

    @BindView(R.id.et_verify_code)
    EditText mEtVerifyCode;
    @BindView(R.id.tv_hint)
    TextView mTvHint;

    @BindView(R.id.tv_hint_down)
    public TextView mTvHintDown;

    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;

    @Inject
    LoginPresenter mLoginPresenter;

    LoginHandler myHandler;
    public int mDown = 60;
    Timer mTimer;
    TimerTask timerTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTimer();
    }

    @Override
    protected void initUi() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        DaggerGoodsComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(application.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mLoginPresenter.attachView(this);
        mLoginPresenter.onCreate();
    }


    private void initTimer() {
        myHandler = new LoginHandler(this);
        mTimer = new Timer(true);
    }

    @Override
    public void showLoadingView() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mPbLoading.setVisibility(View.GONE);
    }

    private void startTimer() {
        mBtnGetCode.setEnabled(false);
        //isDown = true;
        mDown = 60;
        //1s执行一次
        if (timerTask != null) {
            timerTask.cancel(); //将原任务从队列移除
        }

        //为timer提供一个定时执行的任务，在 Timer线程中无法直接操作 UI线程
        timerTask = new TimerTask() {
            @Override
            public void run() {
                myHandler.obtainMessage(LoginHandler.HANDLER_TIMER).sendToTarget();
            }
        };

        mTimer.schedule(timerTask, 1000, 1000);
    }


    @OnClick(R.id.btn_start)
    void onStartClick(View view) {
        //提交验证码
        //验证手机号验证码是否输入
        if (StringUtils.isNullOrEmpty(mEtPhoneNumber.getText().toString())) {
            T.showShort(this, "请填写您的手机号");
        } else if (StringUtils.isNullOrEmpty(mEtVerifyCode.getText().toString())){
            T.showShort(this, "请填写短信验证码");
        } else {
            KeyBoardUtils.closeKeybord(mEtPhoneNumber, this);
            mLoginPresenter.submitVerCode(mEtPhoneNumber.getText().toString(),
                    mEtVerifyCode.getText().toString());
        }
    }

    String phoneNumberStr;

    @OnClick(R.id.btn_get_code)
    void onGetCode(View view) {
        //获取验证码
        //关闭键盘
        KeyBoardUtils.closeKeybord(mEtPhoneNumber, this);

        //验证手机号格式
        phoneNumberStr = mEtPhoneNumber.getText().toString();
        String msg = StringUtils.checkMobile(phoneNumberStr);
        if (msg.equals("")) {
            mLoginPresenter.getVerificationCode(phoneNumberStr);
        } else {
            T.showShort(this, msg);
        }
    }


    @Override
    public void getVerCodeReceived() {
        //验证码发送成功
        String str_1 = String.format(getString(R.string.hint_register_1),
                phoneNumberStr);
        showHint(str_1);
        //启动倒计时
        L.d("test00", "启动计时器");
        mTvHintDown.setVisibility(View.VISIBLE);
        startTimer();
    }

    @Override
    public void bindUser() {
        //登录成功
        Intent mIntent = new Intent(GoodsActivity.ACTION_USER_UPDATE);
        sendBroadcast(mIntent);

        setResult(Constants.RESULT_CODE_LOGIN_SUCCESS);
        finish();
    }

    @Override
    public void completeData() {
        //未完善资料
    }

    @Override
    public void verCodeError() {
        //验证码错误
        T.showShort(this, "验证码输入错误");
    }

    /**
     * 显示提示
     */
    public void showHint(String msg) {
        if (mTvHint.getVisibility() == View.GONE) {
            mTvHint.setVisibility(View.VISIBLE);
            mTvHint.setText(msg);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLoginPresenter.onStop();
    }
}
