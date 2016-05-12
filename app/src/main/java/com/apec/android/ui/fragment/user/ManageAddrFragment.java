package com.apec.android.ui.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.config.Constants;
import com.apec.android.domain.transport.GoodsReceipt;
import com.apec.android.ui.activity.user.EditDataActivity;
import com.apec.android.ui.activity.user.RegisterFActivity;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.MyViewHolder;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.presenter.user.ManageAddrPresenter;
import com.apec.android.util.T;

import java.util.ArrayList;

/**
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

    private ArrayList<GoodsReceipt> mData;
    private BaseAdapter mAdapter;
    private FrameLayout loading;

    public final static String EXTRA_EDIT_ADDRESS = "goodsReceipt";


    private void initView(View view) {
        TextView title = (TextView) view.findViewById(R.id.tv_top_title);
        title.setText("地址管理");

        view.findViewById(R.id.iv_back).setOnClickListener(this);
        loading = (FrameLayout) view.findViewById(R.id.fl_loading);
        view.findViewById(R.id.btn_add_address).setOnClickListener(this);

        mData = new ArrayList<>();

        mAdapter = new CommonAdapter<GoodsReceipt>(getActivity(), mData,
                R.layout.manage_address_item) {
            @Override
            public void convert(final MyViewHolder holder, GoodsReceipt goodsReceipt) {

                holder.setText(R.id.tv_user_name, goodsReceipt.getName())
                        .setText(R.id.tv_phone_number, goodsReceipt.getPhone())
                        .setText(R.id.tv_address, goodsReceipt.getAddrRes().getDetail());

                if (goodsReceipt.isDefalut()) {
                    holder.setChecked(R.id.cb_default_address, true);
                } else {
                    holder.setChecked(R.id.cb_default_address, false);
                }

                holder.setOnClickLister(R.id.cb_default_address, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mData.get(holder.getMPosition()).isDefalut()) {
                            //如果该项没有被选择，则将它设置为默认地址
                            mPresenter.setDefaultAddress(
                                    mData.get(holder.getMPosition()).getAddressId());
                        }
                    }
                });

                holder.setOnClickLister(R.id.tv_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //删除地址
                        mPresenter.deleteAddress(
                                mData.get(holder.getMPosition()).getAddressId());
                    }
                });

                holder.setOnClickLister(R.id.tv_edit, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //编辑地址
                        GoodsReceipt item = mData.get(holder.getMPosition());
                        Intent intent = new Intent(getActivity(), EditDataActivity.class);
                        intent.putExtra(EXTRA_EDIT_ADDRESS, item);
                        startActivityForResult(intent, Constants.REQUEST_CODE_EDIT);
                    }
                });
            }
        };

        setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (getActivity().getIntent().getStringExtra("isCart") != null) { //如果是从购物车进入，选择地址后直接返回
            Bundle bundle = new Bundle();
            bundle.putParcelable("address", mData.get(position));
            getActivity().setResult(Constants.RESULT_CODE_ADDRESS_CHANGE,
                    getActivity().getIntent().putExtras(bundle));
            getActivity().finish();
        }

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
    public void showNoConnection() {

    }

    @Override
    public void hideNoConnection() {

    }

    @Override
    public void deleteSuccess() {
        mPresenter.getAllAddress();
        getActivity().setResult(Constants.RESULT_CODE_ADDRESS_SUCCESS);
    }

    @Override
    public void needLogin() {
        T.showShort(getActivity(), R.string.please_login);
        //Toast.makeText(getActivity(), R.string.please_login, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), RegisterFActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_LOGIN);
    }

    @Override
    public void setDefaultSuccess() {
        getActivity().setResult(Constants.RESULT_CODE_ADDRESS_SUCCESS);
        mPresenter.getAllAddress();
    }

    @Override
    public void getAllAddressSuccess(ArrayList<GoodsReceipt> receipts) {
        mData.clear();
        mData.addAll(receipts);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isReady() {
        return isAdded();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;

            case R.id.btn_add_address:
                Intent intent = new Intent(getActivity(), EditDataActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_EDIT);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case Constants.REQUEST_CODE_LOGIN:
                if (resultCode == Constants.RESULT_CODE_LOGIN_SUCCESS) {
                    mPresenter.getAllAddress();
                } else {
                    getActivity().finish();
                }
                break;
            case Constants.REQUEST_CODE_EDIT:
                if (resultCode == Constants.RESULT_CODE_EDIT) { //编辑收货地址提交成功
                    mPresenter.getAllAddress();
                    getActivity().setResult(Constants.RESULT_CODE_ADDRESS_SUCCESS);
                }
                break;
        }
    }

}
