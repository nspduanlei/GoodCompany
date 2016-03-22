package com.apec.android.ui.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.domain.transport.GoodsReceipt;
import com.apec.android.ui.activity.user.EditDataActivity;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.MyViewHolder;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.presenter.user.ManageAddrPresenter;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 *
 * Created by Administrator on 2016/2/26.
 */
public class ManageAddrFragment extends BaseListFragment<ManageAddrPresenter.IView,
        ManageAddrPresenter> implements ManageAddrPresenter.IView, View.OnClickListener {

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
    protected ManageAddrPresenter createPresenter() {
        return new ManageAddrPresenter(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        mPresenter.getAllAddress();
    }

    private ArrayList<GoodsReceipt> datas;
    private FrameLayout loading;


    private void initView(View view) {

        TextView title = (TextView) view.findViewById(R.id.tv_top_title);
        title.setText("地址管理");

        view.findViewById(R.id.iv_back).setOnClickListener(this);
        loading = (FrameLayout) view.findViewById(R.id.fl_loading);
        view.findViewById(R.id.btn_add_address).setOnClickListener(this);


        datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(new GoodsReceipt());
        }

        setListAdapter(new CommonAdapter<GoodsReceipt>(getActivity(), datas,
                R.layout.manage_address_item) {
            @Override
            public void convert(MyViewHolder holder, GoodsReceipt goodsReceipt) {

            }
        });
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyCase() {

    }

    @Override
    public void needLogin() {

    }

    @Override
    public boolean isReady() {
        return isAdded();
    }

    private final static int REQUEST_CODE_EDIT = 1001;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;

            case R.id.btn_add_address:
                Intent intent = new Intent(getActivity(), EditDataActivity.class);
                startActivityForResult(intent, REQUEST_CODE_EDIT);
                break;

            case R.id.tv_select_area:

                break;
        }
    }
}
