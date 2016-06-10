package com.apec.android.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.views.activities.ManageAddressActivity;
import com.apec.android.views.activities.OrdersActivity;
import com.apec.android.views.activities.ServiceActivity;
import com.apec.android.views.activities.SettingActivity;
import com.apec.android.views.adapter.listView.CommonAdapter;
import com.apec.android.views.adapter.listView.MyViewHolder;
import com.apec.android.views.fragments.core.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by duanlei on 2016/6/7.
 */
public class MeFragment extends BaseFragment {


    @BindView(R.id.lv_menu)
    ListView mLvMenu;

    ArrayList<String> mData = new ArrayList<>();

    @Override
    protected void initUI(View view) {

        mData.add("我的订单");
        mData.add("收货地址");
        mData.add("客服中心");
        mData.add("系统设置");
        mData.add("检测更新");

        BaseAdapter adapter = new CommonAdapter<String>(getActivity(), mData, R.layout.item_me_menu) {
            @Override
            public void convert(MyViewHolder holder, String s) {
                holder.setText(R.id.text, s);
            }
        };

        mLvMenu.setAdapter(adapter);

        mLvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = null;

                switch (i) {
                    case 0: //我的订单
                        intent = new Intent(getActivity(), OrdersActivity.class);
                        break;
                    case 1: //收货地址
                        intent = new Intent(getActivity(), ManageAddressActivity.class);
                        break;
                    case 2: //客服中心
                        intent = new Intent(getActivity(), ServiceActivity.class);
                        break;
                    case 3: //系统设置
                        intent = new Intent(getActivity(), SettingActivity.class);
                        break;
                }

                if (intent != null) {
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {

    }

    @Override
    protected void initPresenter() {

    }

}
