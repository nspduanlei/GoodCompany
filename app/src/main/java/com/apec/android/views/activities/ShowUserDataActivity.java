package com.apec.android.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.config.Constants;
import com.apec.android.domain.entities.user.User;
import com.apec.android.injector.components.DaggerUserComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.utils.UserUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.tungdx.mediapicker.MediaItem;
import vn.tungdx.mediapicker.MediaOptions;
import vn.tungdx.mediapicker.activities.MediaPickerActivity;

/**
 * Created by duanlei on 2016/6/10.
 * 编辑用户资料
 */
public class ShowUserDataActivity extends BaseActivity {

    @BindView(R.id.iv_pic)
    ImageView mIvPic;

    @BindView(R.id.tv_shop_name)
    TextView mTvShopName;
//    @BindView(R.id.tv_user)
//    TextView mTvUser;
//    @BindView(R.id.tv_phone)
//    TextView mTvPhone;

    private static final int REQUEST_MEDIA = 100;

    User mUser;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_user_info, R.string.user_data_title);
    }

    @Override
    protected void initUi() {
        bindUser();
    }

    private void bindUser() {
        mUser = UserUtil.getUser();
        if (mUser != null) {
            mTvShopName.setText(mUser.getShopName());
//            mTvUser.setText(mUser.getName());
//            mTvPhone.setText(mUser.getPhone());
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
    }

//    @OnClick(R.id.rl_phone)
//    void onPhoneClicked() {
//        Intent intent = new Intent(this, EditUserDataActivity.class);
//        intent.putExtra("useInfo", 1);
//        startActivityForResult(intent, Constants.REQUEST_CODE_EDIT_USER_DATA);
//    }

    @OnClick(R.id.rl_shop_name)
    void onShopNameClicked() {
        Intent intent = new Intent(this, EditUserDataActivity.class);
        intent.putExtra("useInfo", 3);
        startActivityForResult(intent, Constants.REQUEST_CODE_EDIT_USER_DATA);
    }

//    @OnClick(R.id.rl_user_name)
//    void onUserNameClicked() {
//        Intent intent = new Intent(this, EditUserDataActivity.class);
//        intent.putExtra("useInfo", 2);
//        startActivityForResult(intent, Constants.REQUEST_CODE_EDIT_USER_DATA);
//    }

    @OnClick(R.id.rl_shop_pic)
    void onShopPicClicked() {
        //TODO 选择照片
        MediaOptions.Builder builder = new MediaOptions.Builder();
        MediaOptions options = builder.setIsCropped(true).setFixAspectRatio(true)
                .build();
        MediaPickerActivity.open(this, REQUEST_MEDIA, options);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE_EDIT_USER_DATA) {
            if (resultCode == Constants.RESULT_CODE_EDIT_USER_DATA_SUCCESS) {
                bindUser();
            }
        } else if (requestCode == REQUEST_MEDIA) {
            if (resultCode == RESULT_OK) {
                ArrayList<MediaItem> medias = MediaPickerActivity.getMediaItemSelected(data);
                String path = medias.get(0).getPathCropped(this);
                saveHeadImg(path);
                Picasso.with(this).load(medias.get(0).getUriCropped()).into(mIvPic);
                Intent mIntent = new Intent(MainActivity.ACTION_USER_UPDATE);
                sendBroadcast(mIntent);
            }
        }
    }

    private void saveHeadImg(String path) {
        mUser.setShopPic(path);
        UserUtil.updateUser(mUser);
    }
}
