package com.apec.android.ui.fragment.user;

import android.os.Bundle;
import android.view.View;

import com.apec.android.R;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.user.EditDataPresenter;
import com.apec.android.ui.presenter.user.RegisterPresenter;

/**
 *
 * Created by Administrator on 2016/2/26.
 */
public class EditDataFragment extends BaseFragment<EditDataPresenter.IView,
        EditDataPresenter> implements EditDataPresenter.IView {

    public static EditDataFragment newInstance() {
        EditDataFragment fragment = new EditDataFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_edit_data;
    }

    @Override
    protected EditDataPresenter createPresenter() {
        return new EditDataPresenter();
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
