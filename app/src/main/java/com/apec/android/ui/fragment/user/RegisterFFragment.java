package com.apec.android.ui.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apec.android.R;
import com.apec.android.config.Constants;
import com.apec.android.ui.activity.user.RegisterActivity;
import com.apec.android.ui.activity.user.RegisterFActivity;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.user.RegisterFPresenter;
import com.apec.android.ui.presenter.user.RegisterPresenter;
import com.apec.android.util.KeyBoardUtils;
import com.apec.android.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 注册，手机验证
 * Created by Administrator on 2016/2/26.
 */
public class RegisterFFragment extends BaseFragment<RegisterFPresenter.IView,
        RegisterFPresenter> implements RegisterFPresenter.IView, View.OnClickListener {

    public static RegisterFFragment newInstance() {
        RegisterFFragment fragment = new RegisterFFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_register_f;
    }

    @Override
    protected RegisterFPresenter createPresenter() {
        return new RegisterFPresenter();
    }

    /**
     * handler
     */
    private MyHandler myHandler;
    //通过智能验证
    public final static int HANDLER_GET_CODE_Z = 0;

    //发送验证码成功
    public final static int HANDLER_SEND_CODE = 1;

    //提交验证码成功
    public final static int HANDLER_SUBMIT_CODE = 2;

    public final static int HANDLER_ERROR = 3;

    public final static int HANDLER_TIMER = 4;

    private String phoneNumberStr;

    private Timer mTimer;
    private TimerTask timerTask;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        //初始化短信验证
        initSMS();

        initTimer();
    }

    private void initTimer() {
        //为timer提供一个定时执行的任务，在 Timer线程中无法直接操作 UI线程
        timerTask = new TimerTask() {
            @Override
            public void run() {
                myHandler.obtainMessage(HANDLER_TIMER).sendToTarget();
            }
        };
        mTimer = new Timer(true);
    }

    private void initSMS() {
        myHandler = new MyHandler(this);

        //初始化短信sdk
        SMSSDK.initSDK(getActivity(), Constants.SHARESDK_APP_KEY,
                Constants.SHARESDK_APP_SECRET);
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    Log.d("test00", "提交验证码成功");
                    myHandler.obtainMessage(HANDLER_SUBMIT_CODE).sendToTarget();

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    Log.d("test00", "获取验证码成功");

                    boolean smart = (Boolean) data;
                    if (smart) {
                        //通过智能验证
                        myHandler.obtainMessage(HANDLER_GET_CODE_Z).sendToTarget();
                    } else {
                        //走正常的验证码获取流程
                        myHandler.obtainMessage(HANDLER_SEND_CODE).sendToTarget();
                    }

                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                    Log.d("test00", "返回支持发送验证码的国家列表");
                }
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(((Throwable) data).getMessage());

                    myHandler.obtainMessage(HANDLER_ERROR,
                            jsonObject.getString("description")).sendToTarget();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    int down = 60;

    static class MyHandler extends Handler {
        WeakReference<RegisterFFragment> mFragment;

        MyHandler(RegisterFFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            RegisterFFragment theFragment = mFragment.get();
            theFragment.hideLoading();

            switch (msg.what) {
                case HANDLER_GET_CODE_Z: //获取验证码该号码是安全号码

                    theFragment.showHint(
                            String.format(theFragment.getString(R.string.hint_register_3),
                                    theFragment.phoneNumberStr));

                    theFragment.verCode.setText("获取验证码成功");
                    theFragment.verCode.setEnabled(false);

                    break;

                case HANDLER_SUBMIT_CODE: //提交验证码成功

                    Intent intent = new Intent(theFragment.getActivity(), RegisterActivity.class);
                    theFragment.startActivity(intent);
                    break;

                case HANDLER_SEND_CODE: //发送验证码成功

                    String str_1 = String.format(theFragment.getString(R.string.hint_register_1),
                            theFragment.phoneNumberStr);

                    theFragment.showHint(str_1);


                    //启动倒计时
                    String downStr = String.format(theFragment.getString(R.string.hint_register_2),
                            theFragment.down);
                    theFragment.hint_down.setVisibility(View.VISIBLE);
                    theFragment.hint_down.setText(downStr);

                    theFragment.startTimer();

                    break;

                case HANDLER_ERROR:
                    String error_msg = (String) msg.obj;
                    theFragment.showHint(error_msg);
                    break;

                case HANDLER_TIMER: //倒计时

                    if (theFragment.down > 0) {
                        String downStr_other = String.format(
                                theFragment.getString(R.string.hint_register_2), theFragment.down--);
                        theFragment.hint_down.setText(downStr_other);
                    } else {
                        theFragment.mTimer.cancel();
                        theFragment.down = 60;
                        theFragment.hint_down.setText("如果您还没收到短信，请尝试重新获取");
                    }

                    break;
            }
        }
    }

    private void startTimer() {
        //1s执行一次
        mTimer.schedule(timerTask, 1000, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        timerTask.cancel();
    }

    /**
     * 显示提示
     */
    public void showHint(String msg) {
        if (hint.getVisibility() == View.GONE) {
            hint.setVisibility(View.VISIBLE);
            hint.setText(msg);
        }
    }

    /**
     * 隐藏提示
     */
    public void hideHint() {
        hint.setVisibility(View.GONE);
    }

    EditText phoneNumber, verCode;
    Button getVerCode;
    TextView hint, hint_down;
    FrameLayout loading;

    private void initView(View view) {

        Button start = (Button) view.findViewById(R.id.btn_start);
        start.setOnClickListener(this);

        phoneNumber = (EditText) view.findViewById(R.id.et_phone_number);
        verCode = (EditText) view.findViewById(R.id.et_verify_code);

        getVerCode = (Button) view.findViewById(R.id.btn_get_code);
        getVerCode.setOnClickListener(this);

        hint = (TextView) view.findViewById(R.id.tv_hint);
        loading = (FrameLayout) view.findViewById(R.id.fl_loading);
        hint_down = (TextView) view.findViewById(R.id.tv_hint_down);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyCase() {

    }

    @Override
    public boolean isReady() {
        return isAdded();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start: //提交验证码
                //验证手机号了验证码是否输入
                if (StringUtils.isNullOrEmpty(phoneNumber.getText().toString())) {
                    Toast.makeText(getActivity(), "请填写您的手机号", Toast.LENGTH_SHORT).show();
                } else if (StringUtils.isNullOrEmpty(verCode.getText().toString())){
                    Toast.makeText(getActivity(), "请填写短信验证码", Toast.LENGTH_SHORT).show();
                } else {
                    KeyBoardUtils.closeKeybord(phoneNumber, getActivity());
                    SMSSDK.submitVerificationCode("86",
                            phoneNumber.getText().toString(),
                            verCode.getText().toString());
                    showLoading();
                }

                break;

            case R.id.btn_get_code: //获取验证码

                if (down != 60) {
                    break;
                }

                KeyBoardUtils.closeKeybord(phoneNumber, getActivity());

                //验证手机号格式
                phoneNumberStr = phoneNumber.getText().toString();
                String msg = StringUtils.checkMobile(phoneNumberStr);
                if (msg.equals("")) {
                    SMSSDK.getVerificationCode("86", phoneNumberStr);
                    showLoading();
                } else {
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
