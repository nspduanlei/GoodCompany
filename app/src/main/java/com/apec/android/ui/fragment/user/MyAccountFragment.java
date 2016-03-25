package com.apec.android.ui.fragment.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.domain.user.Area;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.presenter.user.MyAccountPresenter;
import com.apec.android.ui.presenter.user.SelectCityPresenter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/26.
 */
public class MyAccountFragment extends BaseFragment<MyAccountPresenter.IView,
        MyAccountPresenter> implements MyAccountPresenter.IView, View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static MyAccountFragment newInstance() {
        MyAccountFragment fragment = new MyAccountFragment();
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my_account;
    }

    @Override
    protected MyAccountPresenter createPresenter() {
        return new MyAccountPresenter(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        //mPresenter.obtainArea(1);
    }

    private void initView(View view) {

        TextView title = (TextView) view.findViewById(R.id.tv_top_title);
        title.setText("个人中心");
        view.findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmptyCase() {

    }

    @Override
    public boolean isReady() {
        return isAdded();
    }


    @Override
    public void getAreaBack(ArrayList<Area> areas) {
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
        }
    }

}
