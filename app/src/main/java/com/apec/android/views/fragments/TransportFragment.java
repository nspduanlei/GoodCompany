package com.apec.android.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.domain.entities.transport.SkuItem;
import com.apec.android.domain.entities.transport.TransportInfoItem;
import com.apec.android.views.adapter.listView.CommonAdapter;
import com.apec.android.views.adapter.listView.MyViewHolder;
import com.apec.android.views.fragments.core.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by duanlei on 2016/6/28.
 */
public class TransportFragment extends BaseFragment {

    public static final String EXTRA_DATA = "transport_data";

    @BindView(R.id.iv_header)
    ImageView mIvHeader;
    @BindView(R.id.tv_company)
    TextView mTvCompany;
    @BindView(R.id.tv_driver)
    TextView mTvDriver;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.lv_sku)
    ListView mLvSku;
    @BindView(R.id.tv_number)
    TextView mTvNumber;

    private TransportInfoItem mTransportInfo;

    public static TransportFragment newInstance(TransportInfoItem transportInfo) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_DATA, transportInfo);
        TransportFragment fragment = new TransportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initUI(View view) {
        mTransportInfo = getArguments().getParcelable(EXTRA_DATA);

        if (mTransportInfo == null) {
            return;
        }

        mTvCompany.setText(mTransportInfo.getSupplierName());
        mTvDriver.setText(mTransportInfo.getName());
        mTvPhone.setText(mTransportInfo.getPhone());
        mTvNumber.setText(mTransportInfo.getTrackingNumber());

        mLvSku.setAdapter(new CommonAdapter<SkuItem>(getActivity(), mTransportInfo.getDital(),
                R.layout.item_sku_transport) {
            @Override
            public void convert(MyViewHolder holder, SkuItem skuItem) {

                if (holder.getMPosition() % 2 == 0) {
                    holder.setVisibility(R.id.iv_1, View.VISIBLE);
                    holder.setVisibility(R.id.iv_2, View.GONE);
                } else {
                    holder.setVisibility(R.id.iv_2, View.VISIBLE);
                    holder.setVisibility(R.id.iv_1, View.GONE);
                }

                holder.setText(R.id.tv_good_name, skuItem.getSkuName())
                        .setText(R.id.tv_count, String.format("x%d", skuItem.getNum()));
            }
        });

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_transport;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {

    }

    @Override
    protected void initPresenter() {

    }

}
