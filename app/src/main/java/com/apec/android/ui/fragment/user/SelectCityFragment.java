package com.apec.android.ui.fragment.user;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.apec.android.R;
import com.apec.android.domain.user.Area;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.ViewHolder;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.presenter.user.RegisterPresenter;
import com.apec.android.ui.presenter.user.SelectCityPresenter;
import com.apec.android.util.DensityUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/26.
 */
public class SelectCityFragment extends BaseListFragment<SelectCityPresenter.IView,
        SelectCityPresenter> implements SelectCityPresenter.IView {

    ArrayList<Area> mData;
    BaseAdapter mAdapter;
    LinearLayout llAreas;
    Area curArea;

    ArrayList<Integer> selects;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mData = new ArrayList<>();
        selects = new ArrayList<>();

        mAdapter = new CommonAdapter<Area>(getActivity(), mData, R.layout.select_city_item) {
            @Override
            public void convert(ViewHolder holder, Area area) {
                holder.setText(R.id.tv_area_name, area.getAreaName());
            }
        };

        setListAdapter(mAdapter);
    }

    public static SelectCityFragment newInstance() {
        SelectCityFragment fragment = new SelectCityFragment();
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_select_city;
    }

    @Override
    protected SelectCityPresenter createPresenter() {
        return new SelectCityPresenter(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        mPresenter.obtainArea(1);
        selects.add(1);
    }


    private void initView(View view) {
        llAreas = (LinearLayout) view.findViewById(R.id.ll_areas);
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


    @Override
    public void getAreaBack(ArrayList<Area> areas) {
        if (curArea != null) {
            TextView textView = new TextView(getActivity());
            textView.setText(curArea.getAreaName());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(DensityUtils.dp2px(getActivity(), 10), 0, 0, 0);
            textView.setLayoutParams(layoutParams);
            llAreas.addView(textView);
        }

        mData.clear();
        mData.addAll(areas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
        curArea = mData.get(position);
        mPresenter.obtainArea(curArea.getId());
        selects.add(curArea.getId());
    }
}
