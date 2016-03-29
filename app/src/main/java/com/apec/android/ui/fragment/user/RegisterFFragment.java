package com.apec.android.ui.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apec.android.R;
import com.apec.android.config.Constants;
import com.apec.android.domain.H;
import com.apec.android.domain.user.UserBack;
import com.apec.android.ui.activity.goods.GoodsActivity;
import com.apec.android.ui.activity.user.RegisterActivity;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.user.RegisterFPresenter;
import com.apec.android.util.ColorPhrase;
import com.apec.android.util.KeyBoardUtils;
import com.apec.android.util.L;
import com.apec.android.util.SPUtils;
import com.apec.android.util.StringUtils;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

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
        return new RegisterFPresenter(getActivity());
    }

    /**
     * handler
     */
    private MyHandler myHandler;
    public final static int HANDLER_TIMER = 4;
    private String phoneNumberStr;
    private Timer mTimer;
    private TimerTask timerTask;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initTimer();
    }

    private void initTimer() {
        myHandler = new MyHandler(this);
        mTimer = new Timer(true);
    }


    int down = 60;
    //是否正在倒计时
    boolean isDown = false;

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
                case HANDLER_TIMER: //倒计时
                    if (!theFragment.isAdded()) {
                        break;
                    }
                    if (theFragment.down > 0) {
                        String downStr_other = String.format(
                                theFragment.getString(R.string.hint_register_2), theFragment.down--);

                        CharSequence chars = ColorPhrase.from(downStr_other).withSeparator("{}")
                                .innerColor(0xFFE6454A).outerColor(0xFF666666).format();

                        theFragment.hint_down.setText(chars);
                    } else {
                        theFragment.isDown = false;
                        theFragment.hint_down.setText("如果您还没收到短信，请尝试重新获取");
                    }
                    break;
            }
        }
    }

    private void startTimer() {
        isDown = true;
        down = 60;
        //1s执行一次
        if (timerTask != null) {
            timerTask.cancel(); //将原任务从队列移除
        }

        //为timer提供一个定时执行的任务，在 Timer线程中无法直接操作 UI线程
        timerTask = new TimerTask() {
            @Override
            public void run() {
                myHandler.obtainMessage(HANDLER_TIMER).sendToTarget();
            }
        };

        mTimer.schedule(timerTask, 1000, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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


    /**
     * 验证码发送成功
     */
    @Override
    public void sendCodeBack() {
        String str_1 = String.format(getString(R.string.hint_register_1),
                phoneNumberStr);

        showHint(str_1);

        //启动倒计时
        L.d("test00", "启动计时器");
        hint_down.setVisibility(View.VISIBLE);
        startTimer();
    }

    @Override
    public void submitCodeBack(UserBack userBack) {
        switch (userBack.getH().getCode()) {
            case 200: //登录成功，已完善资料
                SPUtils.put(getActivity(), SPUtils.USER_NAME, userBack.getB().getName());

                Intent mIntent = new Intent(GoodsActivity.ACTION_USER_UPDATE);
                getActivity().sendBroadcast(mIntent);

                SPUtils.put(getActivity(), SPUtils.PHONE, phoneNumberStr);
                getActivity().setResult(Constants.RESULT_CODE_LOGIN_SUCCESS);
                getActivity().finish();
                break;
            case 4017: //登录成功未完善资料
                Intent mIntent1 = new Intent(GoodsActivity.ACTION_USER_UPDATE);
                getActivity().sendBroadcast(mIntent1);

                SPUtils.put(getActivity(), SPUtils.PHONE, phoneNumberStr);
                Intent intent1 = new Intent(getActivity(), RegisterActivity.class);
                getActivity().setResult(Constants.RESULT_CODE_LOGIN_SUCCESS);
                startActivity(intent1);
                getActivity().finish();
                break;
            case 4025: //验证码输入有误
                Toast.makeText(getActivity(), "验证码输入错误", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getActivity(), userBack.getH().getMsg(), Toast.LENGTH_SHORT).show();
                break;
        }
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
                    mPresenter.submitVerCode(phoneNumber.getText().toString(),
                            verCode.getText().toString());
                }

                break;

            case R.id.btn_get_code: //获取验证码
                if (isDown) {
                    break;
                }
                KeyBoardUtils.closeKeybord(phoneNumber, getActivity());

                //验证手机号格式
                phoneNumberStr = phoneNumber.getText().toString();
                String msg = StringUtils.checkMobile(phoneNumberStr);
                if (msg.equals("")) {
                    mPresenter.getVerificationCode(phoneNumberStr);
                } else {
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
