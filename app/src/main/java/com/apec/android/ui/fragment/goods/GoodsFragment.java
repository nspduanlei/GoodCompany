package com.apec.android.ui.fragment.goods;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.domain.goods.Good;
import com.apec.android.domain.goods.Goods;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.ViewHolder;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.goods.GoodsFPresenter;
import com.apec.android.ui.presenter.goods.GoodsPresenter;

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

        mPresenter.fetchGoods();
        initView(view);
    }

    ListView mListView;
    ArrayList<Good> mData = new ArrayList<>();
    CommonAdapter<Good> commonAdapter;

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
                holder.setText(R.id.tv_name, good.getGoodsName());
            }
        };

        mListView.setAdapter(commonAdapter);
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
    public void showGoods(ArrayList goods) {
        mData.addAll(goods);
        commonAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isReady() {
        return isAdded();
    }
}
