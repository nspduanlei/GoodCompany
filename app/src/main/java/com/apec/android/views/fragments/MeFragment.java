package com.apec.android.views.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.domain.entities.user.MyMenu;
import com.apec.android.domain.entities.user.User;
import com.apec.android.domain.entities.user.Version;
import com.apec.android.injector.components.DaggerGoodsComponent;
import com.apec.android.injector.components.DaggerUserComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.mvp.presenters.EditAddressPresenter;
import com.apec.android.mvp.presenters.MePresenter;
import com.apec.android.mvp.views.MeView;
import com.apec.android.support.ImageHelp;
import com.apec.android.support.rest.RestDataSource;
import com.apec.android.util.AppUtils;
import com.apec.android.util.T;
import com.apec.android.views.activities.EditUserDataActivity;
import com.apec.android.views.activities.ManageAddressActivity;
import com.apec.android.views.activities.MessageActivity;
import com.apec.android.views.activities.OrdersActivity;
import com.apec.android.views.activities.ServiceActivity;
import com.apec.android.views.activities.SettingActivity;
import com.apec.android.views.activities.ShowUserDataActivity;
import com.apec.android.views.adapter.listView.CommonAdapter;
import com.apec.android.views.adapter.listView.MyViewHolder;
import com.apec.android.views.fragments.core.BaseFragment;
import com.apec.android.views.utils.LoginUtil;
import com.apec.android.views.utils.UpdateVersionUtil;
import com.apec.android.views.utils.UserUtil;
import com.apec.android.views.widget.NoScrollListView;
import com.loveplusplus.update.UpdateChecker;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/6/7.
 */
public class MeFragment extends BaseFragment implements MeView {

    @BindView(R.id.lv_menu)
    ListView mLvMenu;

    ArrayList<MyMenu> mData = new ArrayList<>();

    @BindView(R.id.iv_header)
    ImageView mIvHeader;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
//    @BindView(R.id.v_msg_new)
//    View mVMsgNew;

    boolean isLogin = false;
    User mUser;

    @Inject
    MePresenter mPresenter;

    @Override
    protected void initUI(View view) {
        fillData();
        updateUser();
    }

    private void fillData() {
        mData.add(new MyMenu(R.drawable.icon_order, "我的订单", false, null, null));
        mData.add(new MyMenu(R.drawable.icon_address, "收货地址", false, null, null));
        mData.add(new MyMenu(R.drawable.icon_quan, "优惠券", false, "(敬请期待)", null));
        mData.add(new MyMenu(R.drawable.icon_point, "积分兑换", false, "(敬请期待)", null));
        mData.add(new MyMenu(R.drawable.icon_server, "客服中心", false, null, null));
        mData.add(new MyMenu(R.drawable.icon_version, "检查更新", false, null,
                "当前版本v" + AppUtils.getVersionName(getActivity())));

        BaseAdapter adapter = new CommonAdapter<MyMenu>(getActivity(), mData, R.layout.item_me_menu) {
            @Override
            public void convert(MyViewHolder holder, MyMenu myMenu) {
                holder.setText(R.id.tv_title, myMenu.getTitle())
                        .setImageResource(R.id.iv_icon, myMenu.getResId());

                if (myMenu.getContext() == null) {
                    holder.setVisibility(R.id.tv_content, View.GONE);
                } else {
                    holder.setVisibility(R.id.tv_content, View.VISIBLE);
                    holder.setText(R.id.tv_content, myMenu.getContext());
                }

                if (myMenu.getHint() == null) {
                    holder.setVisibility(R.id.tv_hint, View.GONE);
                } else {
                    holder.setVisibility(R.id.tv_hint, View.VISIBLE);
                    holder.setText(R.id.tv_hint, myMenu.getHint());
                }

                if (myMenu.isHasNew()) {
                    holder.setVisibility(R.id.v_has_new, View.VISIBLE);
                } else {
                    holder.setVisibility(R.id.v_has_new, View.GONE);
                }

            }
        };

        mLvMenu.setAdapter(adapter);

        mLvMenu.setOnItemClickListener((adapterView, view1, i, l) -> {
            Intent intent = null;
            switch (i) {
                case 0: //我的订单
                    intent = new Intent(getActivity(), OrdersActivity.class);
                    break;
                case 1: //收货地址
                    intent = new Intent(getActivity(), ManageAddressActivity.class);
                    break;
                case 4: //客服中心
                    intent = new Intent(getActivity(), ServiceActivity.class);
                    break;
                case 5: //版本更新
                    //TODO 检测版本
                    mPresenter.getVersion();
                    break;
            }
            if (intent != null) {
                startActivity(intent);
            }
        });
    }


    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {
        DaggerUserComponent.builder()
                .activityModule(new ActivityModule(getActivity()))
                .appComponent(myApplication.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }

//    @OnClick(R.id.fl_msg)
//    void onMsgClicked(View view) {
//        Intent intent = new Intent(getActivity(), MessageActivity.class);
//        startActivity(intent);
//    }

    @OnClick(R.id.tv_edit)
    void onSettingClicked(View view) {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_user)
    void onUserClicked() {
        if (LoginUtil.gotoLoginNew(getActivity())) {
            Intent intent = new Intent(getActivity(), ShowUserDataActivity.class);
            startActivity(intent);
        }
    }

    public void updateUser() {
        isLogin = LoginUtil.isLogin();
        if (isLogin) {
            mUser = UserUtil.getUser();
            if (mUser != null) {
                mTvUserName.setText(mUser.getShopName());
//                if (mUser.getShopPic() != null) {
//                    ImageHelp.displayCircle(getActivity(), mUser.getShopPic(), mIvHeader);
//                }

                //头像
                if (mUser.getShopPic() != null) {
                    File file = new File(mUser.getShopPic());
                    if (file.exists()) {
                        ImageHelp.displayLocalFileCircle(getActivity(), file, mIvHeader);
                    }
                } else {
                    mIvHeader.setImageResource(R.drawable.head_default);
                }
            }
        } else {
            mTvUserName.setText("请登录");
        }
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void getVersionSuccess(Version version) {
        int appCode = AppUtils.getVersionCode(getActivity());
        if (appCode >= version.getB().getVersionNo()) {
            //已是最新版本
            T.showShort(getActivity(), "当前已是最新版本");
        } else {
            //不是最新版本
            UpdateChecker.checkForDialog(getActivity(), RestDataSource.END_POINT + "appVersion");
            //new UpdateVersionUtil(getActivity(), version.getB()).showDialog();
        }
    }
}
