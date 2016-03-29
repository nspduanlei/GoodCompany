package com.apec.android.ui.fragment.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.apec.android.R;
import com.apec.android.domain.goods.Good;
import com.apec.android.ui.activity.goods.GoodsDetailActivity;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.MyViewHolder;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.goods.GoodsFPresenter;

import java.util.ArrayList;

/**
 *
 * Created by Administrator on 2016/2/26.
 */
public class GoodsFragment extends BaseFragment<GoodsFPresenter.IView, GoodsFPresenter>
        implements GoodsFPresenter.IView {

    public static final String EXTRA_CATEGORY_ID = "CATEGORY_ID";
    private int mID;

    /**
     * 得到商品展示的fragment
     * @param cId 类型id
     * @return
     */
    public static GoodsFragment newInstance(int cId) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_CATEGORY_ID, cId);
        GoodsFragment fragment = new GoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mID = getArguments().getInt(EXTRA_CATEGORY_ID);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_goods;
    }

    @Override
    protected GoodsFPresenter createPresenter() {
        return new GoodsFPresenter(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        mPresenter.fetchGoods(mID);
    }

    ListView mListView;
    ArrayList<Good> mData = new ArrayList<>();
    CommonAdapter<Good> commonAdapter;
    FrameLayout loading;

    private void initView(View view) {
        //商品列表初始化
        mListView = (ListView) view.findViewById(R.id.lv_goods);
        commonAdapter = new CommonAdapter<Good>(getActivity(),
                mData, R.layout.goods_item) {
            @Override
            public void convert(MyViewHolder holder, Good good) {
                holder.setText(R.id.tv_name, good.getGoodsName());
//                if (good.getPics().size() != 0) {
//                    holder.setImageUrl(R.id.iv_pic, good.getPics().get(0).getUrl());
//                }
            }
        };
        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(GoodsDetailFragment.EXTRA_GOODS_ID
                        , mData.get(position).getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        loading = (FrameLayout) view.findViewById(R.id.fl_loading);
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
    public void showGoods(ArrayList goods) {
        mData.clear();
        mData.addAll(goods);
        commonAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isReady() {
        return isAdded();
    }
}
