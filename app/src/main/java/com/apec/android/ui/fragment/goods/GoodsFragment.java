package com.apec.android.ui.fragment.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.domain.goods.Good;
import com.apec.android.ui.activity.goods.GoodsDetailActivity;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.ViewHolder;
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
        return new GoodsFPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        mPresenter.fetchGoods();
    }

    ListView mListView;
    ArrayList<Good> mData = new ArrayList<>();
    CommonAdapter<Good> commonAdapter;
    private Good mGood;
    FrameLayout loading;

    private void initView(View view) {
        //测试
        TextView test = (TextView) view.findViewById(R.id.tv_test);
        test.setText(String.valueOf(mID));

        //商品列表初始化
        mListView = (ListView) view.findViewById(R.id.lv_goods);
        commonAdapter = new CommonAdapter<Good>(getActivity(),
                mData, R.layout.goods_item) {
            @Override
            public void convert(ViewHolder holder, Good good) {
                mGood = good;
                holder.setText(R.id.tv_name, good.getGoodsName())
                        .setOnClickLister(R.id.ll_item, onItemClickListener);
            }
        };

        mListView.setAdapter(commonAdapter);

        loading = (FrameLayout) view.findViewById(R.id.fl_loading);
    }

    /**
     * item点击监听
     */
    View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_item:
                    Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("goodsId", mGood.getId());

                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };


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
        mData.addAll(goods);
        commonAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isReady() {
        return isAdded();
    }
}
