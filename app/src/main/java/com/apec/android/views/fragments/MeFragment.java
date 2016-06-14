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
import com.apec.android.injector.components.DaggerGoodsComponent;
import com.apec.android.injector.components.DaggerUserComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.util.AppUtils;
import com.apec.android.views.activities.ManageAddressActivity;
import com.apec.android.views.activities.OrdersActivity;
import com.apec.android.views.activities.ServiceActivity;
import com.apec.android.views.activities.SettingActivity;
import com.apec.android.views.adapter.listView.CommonAdapter;
import com.apec.android.views.adapter.listView.MyViewHolder;
import com.apec.android.views.fragments.core.BaseFragment;
import com.apec.android.views.widget.NoScrollListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/6/7.
 */
public class MeFragment extends BaseFragment {

    @BindView(R.id.lv_menu)
    NoScrollListView mLvMenu;

    ArrayList<MyMenu> mData = new ArrayList<>();

    //HeadViewHolder mHeadViewHolder;

    @BindView(R.id.iv_header)
    ImageView mIvHeader;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.v_msg_new)
    View mVMsgNew;

    @Override
    protected void initUI(View view) {
        fillData();
        //initHeader();
    }

    private void fillData() {
        mData.add(new MyMenu(R.drawable.icon_order, "我的订单", true, null, null));
        mData.add(new MyMenu(R.drawable.icon_address, "收货地址", true, null, null));
        mData.add(new MyMenu(R.drawable.icon_quan, "优惠券", true, "(敬请期待)", null));
        mData.add(new MyMenu(R.drawable.icon_point, "积分兑换", true, "(敬请期待)", null));
        mData.add(new MyMenu(R.drawable.icon_server, "客服中心", true, null, null));
        mData.add(new MyMenu(R.drawable.icon_version, "检查更新", true, null,
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
                case 5: //系统设置
                    intent = new Intent(getActivity(), SettingActivity.class);
                    break;
            }
            if (intent != null) {
                startActivity(intent);
            }
        });
    }

//    private void initHeader() {
//        View headView = getActivity().getLayoutInflater().inflate(R.layout.layout_me_head, null);
//        mHeadViewHolder = new HeadViewHolder(headView);
//        mLvMenu.addHeaderView(headView);
//    }

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

    }

    @OnClick(R.id.iv_msg)
    void onMsgClicked(View view) {

    }

    @OnClick(R.id.iv_setting)
    void onSettingClicked(View view) {

    }
}
