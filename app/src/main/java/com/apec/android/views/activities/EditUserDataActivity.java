package com.apec.android.views.activities;

import android.widget.EditText;
import android.widget.ImageView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.domain.entities.user.User;
import com.apec.android.injector.components.DaggerUserComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.EditAddressPresenter;
import com.apec.android.mvp.presenters.EditUserDataPresenter;
import com.apec.android.mvp.views.EditUserDataView;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.utils.UserUtil;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by duanlei on 2016/6/10.
 * 编辑用户资料
 */
public class EditUserDataActivity extends BaseActivity implements EditUserDataView {

    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.et_shop_name)
    EditText mEtShopName;
    @BindView(R.id.et_user)
    EditText mEtUser;
    @BindView(R.id.et_phone)
    EditText mEtPhone;

    @Inject
    EditUserDataPresenter mPresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_user_info, R.string.user_data_title);
    }

    @Override
    protected void initUi() {
        User user = UserUtil.getUser();
        if (user != null) {
            mEtShopName.setText(user.getShopName());
            mEtPhone.setText(user.getPhone());
            mEtUser.setText(user.getPhone());
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

    }

    @Override
    public void hideLoadingView() {

    }
}
