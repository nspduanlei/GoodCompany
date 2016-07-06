package com.apec.android.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.domain.entities.user.Address;
import com.apec.android.domain.entities.user.User;
import com.apec.android.injector.components.DaggerLoginComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.LoginPresenter;
import com.apec.android.mvp.views.LoginView;
import com.apec.android.util.KeyBoardUtils;
import com.apec.android.util.L;
import com.apec.android.util.SPUtils;
import com.apec.android.util.StringUtils;
import com.apec.android.util.T;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.utils.LoginHandler;
import com.apec.android.views.utils.SelectCityUtil;
import com.apec.android.views.utils.UserUtil;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/5/11.
 */
public class LoginActivity extends BaseActivity implements LoginView, SelectCityUtil.SelectArea {

    @BindView(R.id.et_phone_number)
    EditText mEtPhoneNumber;

    @BindView(R.id.btn_get_code)
    public Button mBtnGetCode;

    @BindView(R.id.et_verify_code)
    EditText mEtVerifyCode;

    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;

    @BindView(R.id.tv_hint_complete)
    TextView mTvHintComplete;
    @BindView(R.id.et_shop)
    EditText mEtShop;
    @BindView(R.id.tv_area)
    TextView mTvArea;
    @BindView(R.id.tv_area_detail)
    TextView mTvAreaDetail;
    @BindView(R.id.et_area_detail)
    EditText mEtAreaDetail;
    @BindView(R.id.et_user_name)
    EditText mEtUserName;
    @BindView(R.id.btn_finish)
    Button mBtnFinish;
    @BindView(R.id.ll_complete)
    LinearLayout mLlComplete;

    @Inject
    LoginPresenter mLoginPresenter;

    LoginHandler myHandler;
    public int mDown = 60;
    Timer mTimer;
    TimerTask timerTask;

    int mCityId, mAreaId;
    String mCity, mArea;

    @Inject
    SelectCityUtil mSelectCityUtil;

    //完善资料信息
    User mUser;

    boolean isNewUser = false;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_login, R.string.title_login);
    }

    @Override
    protected void initUi() {
        initTimer();
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        DaggerLoginComponent.builder()
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
        } else if (StringUtils.isNullOrEmpty(mEtVerifyCode.getText().toString())) {
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
        //启动倒计时
        L.d("test00", "启动计时器");
        startTimer();
    }

    private void uploadArgument() {
        String value = (String) SPUtils.get(this, SPUtils.REGISTRATION_ID, "");
        if (!StringUtils.isNullOrEmpty(value)) {
            mLoginPresenter.uploadArgument(value, 3);
        }
    }

    @Override
    public void bindUser(User user) {
        //上传参数
        uploadArgument();

        mUser = user;

        //登录成功，显示信息验证
        showComplete();

        //显示信息
        mEtShop.setText(user.getShopName());
        mTvArea.setText(String.format("%s%s",
                user.getAddrRes().getCity(), user.getAddrRes().getArea()));
        mEtAreaDetail.setText(user.getAddrRes().getDetail());
        mEtPhoneNumber.setText(user.getPhone());
        mEtUserName.setText(user.getName());

        mCityId = Integer.valueOf(user.getAddrRes().getCityId());
        mAreaId = Integer.valueOf(user.getAddrRes().getAreaId());
        mCity = user.getAddrRes().getCity();
        mArea = user.getAddrRes().getArea();

        Intent mIntent = new Intent(MainActivity.ACTION_USER_UPDATE);
        sendBroadcast(mIntent);
        setResult(Constants.RESULT_CODE_LOGIN_SUCCESS);
    }

    @Override
    public void completeData() {
        uploadArgument();

        isNewUser = true;
        //未完善资料, 显示表单填写
        showComplete();
        mTvHintComplete.setVisibility(View.VISIBLE);
    }

    private void showComplete() {
        mLlComplete.setVisibility(View.VISIBLE);
    }

    @Override
    public void verCodeError() {
        //验证码错误
        T.showShort(this, "验证码输入错误");
    }

    @Override
    public void completeSuccess() {
        //信息提交成功
        T.showShort(this, "资料提交成功");

        mUser.setPhone(phoneNumberStr);
        UserUtil.saveUser(mUser);
        finishExit();

        Intent mIntent = new Intent(MainActivity.ACTION_USER_UPDATE);
        sendBroadcast(mIntent);
        setResult(Constants.RESULT_CODE_LOGIN_SUCCESS);
    }

    @Override
    protected void onStop() {
        mLoginPresenter.onStop();
        super.onStop();
    }

    @Override
    public void selectCityFinish(String areaStr, int selCityId, int selAreaId) {
        //选择城市和地区的回调
        mTvArea.setText(areaStr);
        mCityId = selCityId;
        mAreaId = selAreaId;
    }

    @OnClick(R.id.btn_finish)
    void onSubmitCompleteClicked(View view) {
        String userShop = mEtShop.getText().toString();
        String userName = mEtUserName.getText().toString();
        int userCity = mCityId;
        int userArea = mAreaId;
        String userAddress = mEtAreaDetail.getText().toString();

        if (StringUtils.isNullOrEmpty(userShop) ||
                StringUtils.isNullOrEmpty(userName) ||
                userCity == 0 ||
                userArea == 0 ||
                StringUtils.isNullOrEmpty(userAddress)) {

            T.showShort(this, "请填写完所用表单数据。。");

        } else {
            if(isNewUser) {
                mUser = new User();
                mUser.setName(userName);
                mUser.setShopName(userShop);

                Address address = new Address();
                address.setCityId(String.valueOf(userCity));
                address.setAreaId(String.valueOf(userArea));
                address.setDetail(userAddress);
                mUser.setAddrRes(address);

                mLoginPresenter.completeUser(mUser);
            } else {
                boolean isEdit = false;

                if (!mUser.getName().equals(userName)) {
                    mUser.setName(userName);
                } else if (!mUser.getShopName().equals(userShop)) {
                    mUser.setShopName(userShop);
                } else if (!mUser.getAddrRes().getCityId().equals(String.valueOf(userCity))) {
                    Address address = mUser.getAddrRes();
                    address.setCityId(String.valueOf(userCity));
                    mUser.setAddrRes(address);
                } else if (!mUser.getAddrRes().getAreaId().equals(String.valueOf(userArea))) {
                    Address address = mUser.getAddrRes();
                    address.setAreaId(String.valueOf(userArea));
                    mUser.setAddrRes(address);
                } else if (!mUser.getAddrRes().getDetail().equals(userAddress)) {
                    Address address = mUser.getAddrRes();
                    address.setDetail(userAddress);
                    mUser.setAddrRes(address);
                }

                if (isEdit) {
                    //提交数据
                    mLoginPresenter.completeUser(mUser);
                } else {
                    finishExit();
                }
            }

        }
    }

    @OnClick(R.id.ll_select_area)
    void onSelectAddressClicked(View view) {
        if (mUser == null) {
            mSelectCityUtil.setData(this);
            mSelectCityUtil.dialog.show();
        } else {
            mSelectCityUtil.setData(this, mCity, mArea, mCityId, mAreaId);
            mSelectCityUtil.dialog.show();
        }
    }

    private void finishExit() {
        setResult(Constants.RESULT_CODE_LOGIN_SUCCESS);
        finish();
    }

}
