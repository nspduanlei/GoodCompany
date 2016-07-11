package com.apec.android.views.activities;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.domain.entities.user.User;
import com.apec.android.injector.components.DaggerUserComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.EditUserDataPresenter;
import com.apec.android.mvp.views.EditUserDataView;
import com.apec.android.util.StringUtils;
import com.apec.android.util.T;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.utils.UserUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/6/10.
 * 编辑用户资料
 */
public class EditUserDataActivity extends BaseActivity implements EditUserDataView {


    @Inject
    EditUserDataPresenter mPresenter;

    @BindView(R.id.et_user_info)
    EditText mEtUserInfo;

    @BindView(R.id.iv_text_cancel)
    ImageView mIvTextCancel;

    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;

    @BindView(R.id.tv_user_info)
    TextView mTvUserInfo;

    int mInfoType;
    String mUserInfoStr;

    final int PHONE = 1;
    final int USER_NAME = 2;
    final int SHOP_NAME = 3;

    User mUser;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_eidt_user_info, R.string.edit_data_title,
                R.menu.menu_submit, 0);
    }

    @Override
    protected void initUi() {

        mEtUserInfo.addTextChangedListener(new EditChangedListener());

        mInfoType = getIntent().getIntExtra("useInfo", 0);

        mUser = UserUtil.getUser();

        if (mUser == null) {
            return;
        }
        switch (mInfoType) {
            case PHONE: //手机号
                mUserInfoStr = "手机号";
                mEtUserInfo.setText(mUser.getPhone());
                break;
            case USER_NAME: //联系人
                mUserInfoStr = "联系人";
                mEtUserInfo.setText(mUser.getName());
                break;
            case SHOP_NAME: //店铺名
                mUserInfoStr = "商家店面可以由中英文、数字组成";

                setUpTitle(R.string.update_shop_name);

                mEtUserInfo.setText(mUser.getShopName());
                break;
            default:
                break;
        }

        mTvUserInfo.setText(mUserInfoStr);
        if (StringUtils.isNullOrEmpty(mTvUserInfo.getText().toString())) {
            mIvTextCancel.setVisibility(View.GONE);
        }


    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        DaggerUserComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(application.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void showLoadingView() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sure:
                //TODO 提交修改
                String info = mEtUserInfo.getText().toString();
                String msg = "";

                switch (mInfoType) {
                    case PHONE:
                        if(info.equals(mUser.getPhone())) {
                            msg = "内容没有修改";
                        } else {
                            msg = StringUtils.checkMobile(info);
                        }
                        break;
                    case USER_NAME:
                        if(info.equals(mUser.getName())) {
                            msg = "内容没有修改";
                        } else {
                            msg = StringUtils.checkUserName(info);
                        }
                        break;
                    case SHOP_NAME:
                        if(info.equals(mUser.getShopName())) {
                            msg = "内容没有修改";
                        } else {
                            msg = StringUtils.checkShopName(info);
                        }
                        break;
                    default:
                        break;
                }

                if (!msg.equals("")) {
                    T.showShort(this, msg);
                } else {
                    mUser = UserUtil.getUser();

                    if (mUser == null) {
                        return true;
                    }

                    if (mInfoType == PHONE) {
                        mUser.setPhone(info);
                    } else if (mInfoType == USER_NAME) {
                        mUser.setName(info);
                    } else if (mInfoType == SHOP_NAME) {
                        mUser.setShopName(info);
                    }

                    mPresenter.submitInfo(mUser);
                }

                break;
        }
        return true;
    }

    @OnClick(R.id.iv_text_cancel)
    void onTextCancelClicked(View v) {
        mEtUserInfo.setText("");
        mIvTextCancel.setVisibility(View.GONE);
    }

    @Override
    public void updateUserSuccess() {
        UserUtil.updateUser(mUser);

        Intent mIntent = new Intent(MainActivity.ACTION_USER_UPDATE);
        sendBroadcast(mIntent);

        T.showShort(this, "修改成功");
        setResult(Constants.RESULT_CODE_EDIT_USER_DATA_SUCCESS);
        finish();
    }


    class EditChangedListener implements TextWatcher {
        private CharSequence temp;//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 14;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
            editStart = mEtUserInfo.getSelectionStart();
            editEnd = mEtUserInfo.getSelectionEnd();

            if ((mInfoType == USER_NAME || mInfoType == SHOP_NAME) && temp.length() > charMaxNum) {
                T.showShort(EditUserDataActivity.this, "你输入的字数已经超过了限制！");
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                mEtUserInfo.setText(s);
                mEtUserInfo.setSelection(tempSelection);
            }

            if (temp.length() > 0) {
                mIvTextCancel.setVisibility(View.VISIBLE);
            } else {
                mIvTextCancel.setVisibility(View.GONE);
                T.showShort(EditUserDataActivity.this, "输入不能为空");
            }
        }
    }

}
