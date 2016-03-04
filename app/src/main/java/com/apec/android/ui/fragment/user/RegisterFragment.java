package com.apec.android.ui.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.domain.goods.Good;
import com.apec.android.ui.activity.goods.GoodsDetailActivity;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.ViewHolder;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.goods.GoodsFPresenter;
import com.apec.android.ui.presenter.user.RegisterPresenter;

import java.util.ArrayList;

/**
 *
 * Created by Administrator on 2016/2/26.
 */
public class RegisterFragment extends BaseFragment<RegisterPresenter.IView,
        RegisterPresenter> implements RegisterPresenter.IView {

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_register;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initView(view);
    }


    private void initView(View view) {
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
}
