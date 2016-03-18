package com.apec.android.ui.fragment.goods;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.domain.goods.Good;
import com.apec.android.domain.goods.SkuAttribute;
import com.apec.android.ui.activity.BaseActivity;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.ViewHolder;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.presenter.goods.GoodsDetailPresenter;

import java.util.ArrayList;

/**
 * 商品详情
 * Created by duanlei on 2016/3/2.
 */
public class GoodsDetailFragment extends BaseListFragment<GoodsDetailPresenter.IView,
        GoodsDetailPresenter> implements GoodsDetailPresenter.IView, View.OnClickListener {

    public final static String EXTRA_GOODS_ID = "goods_id";

    public static GoodsDetailFragment newInstance(int gId) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_GOODS_ID, gId);
        GoodsDetailFragment fragment = new GoodsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private int goodsId;
    private ArrayList<SkuAttribute> datas;
    private BaseAdapter mAdapter;


    private View footerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goodsId = getArguments().getInt(EXTRA_GOODS_ID);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_goods_detail;
    }

    @Override
    protected GoodsDetailPresenter createPresenter() {
        return new GoodsDetailPresenter(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        mPresenter.fetchGoodsAttrs(goodsId);
    }

    private void initView(View view) {
        /**
         * 导航栏初始化
         */
        ImageView back = (ImageView) view.findViewById(R.id.iv_back);
        back.setOnClickListener(this);

        TextView title = (TextView) view.findViewById(R.id.tv_top_title);
        title.setText("订单");

        footerView = getActivity().getLayoutInflater()
                .inflate(R.layout.goods_detail_bottom, null);

        datas = new ArrayList<>();
        mAdapter = new CommonAdapter<SkuAttribute>(getActivity(), datas,
                R.layout.sku_attr_item) {
            @Override
            public void convert(ViewHolder holder, SkuAttribute skuAttribute) {
                holder.setText(R.id.tv_attr_name, skuAttribute.getName());
                holder.setRadioGroup(R.id.rg_attrs, skuAttribute.getAttributeValues());
            }
        };
        setListAdapter(mAdapter);
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
    public void getAllAttrSuccess(ArrayList<SkuAttribute> attrs) {
        datas.clear();
        datas.addAll(attrs);
        mAdapter.notifyDataSetChanged();

        getListView().addFooterView(footerView, null, false);
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
        }
    }
}
