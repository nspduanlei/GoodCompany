package com.apec.android.ui.fragment.goods;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.goods.GoodsFPresenter;
import com.apec.android.ui.presenter.goods.GoodsPresenter;

/**
 * Created by Administrator on 2016/2/26.
 */
public class GoodsFragment extends BaseFragment implements GoodsFPresenter.IView {

    public static final String EXTRA_CATEGORY_ID = "CATEGORY_ID";

    GoodsFPresenter goodsFPresenter;

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView test = (TextView) view.findViewById(R.id.tv_test);
        test.setText(String.valueOf(mID));

        goodsFPresenter = new GoodsFPresenter(this);
        goodsFPresenter.fetchGoods();
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
    public void showGoodTypes() {

    }

    @Override
    public void isReady() {
        isAdded();
    }
}
