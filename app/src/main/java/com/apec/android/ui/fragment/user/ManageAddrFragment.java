package com.apec.android.ui.fragment.user;

import android.os.Bundle;
import android.view.View;

import com.apec.android.R;
import com.apec.android.domain.transport.GoodsReceipt;
import com.apec.android.domain.user.Address;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.ViewHolder;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.presenter.user.RegisterPresenter;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * Created by Administrator on 2016/2/26.
 */
public class ManageAddrFragment extends BaseListFragment<RegisterPresenter.IView,
        RegisterPresenter> implements RegisterPresenter.IView {

    public static ManageAddrFragment newInstance() {
        ManageAddrFragment fragment = new ManageAddrFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_manage_addr;
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

    ArrayList<GoodsReceipt> datas;

    private void initView(View view) {

        datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(new GoodsReceipt());
        }

        setListAdapter(new CommonAdapter<GoodsReceipt>(getActivity(), datas,
                R.layout.manage_address_item) {
            @Override
            public void convert(ViewHolder holder, GoodsReceipt goodsReceipt) {

            }
        });
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
