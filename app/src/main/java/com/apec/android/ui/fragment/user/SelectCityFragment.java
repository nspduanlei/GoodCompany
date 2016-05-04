package com.apec.android.ui.fragment.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.apec.android.R;
import com.apec.android.domain.user.Area;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.presenter.user.SelectCityPresenter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/26.
 */
public class SelectCityFragment extends BaseListFragment<SelectCityPresenter.IView,
        SelectCityPresenter> implements SelectCityPresenter.IView, View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static SelectCityFragment newInstance() {
        SelectCityFragment fragment = new SelectCityFragment();
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_select_city;
    }

    @Override
    protected SelectCityPresenter createPresenter() {
        return new SelectCityPresenter(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        mPresenter.obtainArea(1);
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


    @Override
    public void getAreaBack(ArrayList<Area> areas) {
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }

    @Override
    public void onClick(View v) {
    }

}
