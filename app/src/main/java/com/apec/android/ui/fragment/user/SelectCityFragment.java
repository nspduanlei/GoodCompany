package com.apec.android.ui.fragment.user;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.apec.android.R;
import com.apec.android.domain.user.Area;
import com.apec.android.ui.adapter.CommonAdapter;
import com.apec.android.ui.adapter.MyViewHolder;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.presenter.user.SelectCityPresenter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/26.
 */
public class SelectCityFragment extends BaseListFragment<SelectCityPresenter.IView,
        SelectCityPresenter> implements SelectCityPresenter.IView, View.OnClickListener {

    ArrayList<Area> mData;
    BaseAdapter mAdapter;
    Area curArea;
    RadioButton rbCity, rbArea, rbRoad, rbPlease;
    ArrayList<Area> cityList, areaList, roadList;
    int curIndex = 1;
    int selCity, selArea, selRoad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<Area>(getActivity(), mData, R.layout.select_city_item) {
            @Override
            public void convert(MyViewHolder holder, Area area) {
                holder.setText(R.id.tv_area_name, area.getAreaName());
                switch (curIndex) {
                    case 1:
                        if (area.getId() == selCity) {
                            holder.setTextColor(R.id.tv_area_name, R.color.color_test);
                        } else {
                            holder.setTextColor(R.id.tv_area_name, R.color.color_text_1);
                        }
                        break;
                    case 2:
                        if (area.getId() == selArea) {
                            holder.setTextColor(R.id.tv_area_name, R.color.color_test);
                        } else {
                            holder.setTextColor(R.id.tv_area_name, R.color.color_text_1);
                        }
                        break;
                    case 3:
                        if (area.getId() == selRoad) {
                            holder.setTextColor(R.id.tv_area_name, R.color.color_test);
                        } else {
                            holder.setTextColor(R.id.tv_area_name, R.color.color_text_1);
                        }
                        break;
                }
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
    }

    private void initView(View view) {
        rbCity = (RadioButton) view.findViewById(R.id.rb_city);
        rbArea = (RadioButton) view.findViewById(R.id.rb_area);
        rbRoad = (RadioButton) view.findViewById(R.id.rb_road);
        rbPlease = (RadioButton) view.findViewById(R.id.rb_please_select);
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

        if (areas.size() > 0) { //如果有地区数据
            switch (curIndex) {
                case 1:
                    cityList = areas;
                    break;
                case 2:
                    areaList = areas;
                    break;
                case 3:
                    roadList = areas;
                    break;
            }
            mData.clear();
            mData.addAll(areas);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        curArea = mData.get(position);
        switch (curIndex) {
            case 1: //城市选择
                if (rbCity.getVisibility() == View.GONE) {
                    rbCity.setVisibility(View.VISIBLE);
                    rbCity.setOnClickListener(this);
                }
                rbCity.setText(curArea.getAreaName());
                selCity = curArea.getId();

                //城市选择完，切换到选择地区
                curIndex = 2;
                if (rbArea.getVisibility() == View.GONE) {
                    rbPlease.setChecked(true);
                } else {
                    rbArea.setChecked(true);
                }

                //清除掉与上一次选择关联的区或街道
                selArea = 0;
                selRoad = 0;
                rbArea.setVisibility(View.GONE);
                rbRoad.setVisibility(View.GONE);
                rbPlease.setVisibility(View.VISIBLE);
                break;
            case 2: //地区选择
                if (rbArea.getVisibility() == View.GONE) {
                    rbArea.setVisibility(View.VISIBLE);
                    rbArea.setOnClickListener(this);
                }
                rbArea.setText(curArea.getAreaName());
                selArea = curArea.getId();

                //地区选择完，切换到选择街道
                curIndex = 3;
                if (rbRoad.getVisibility() == View.GONE) {
                    rbPlease.setChecked(true);
                } else {
                    rbRoad.setChecked(true);
                }

                //清除掉与上一次选择关联的区或街道
                selRoad = 0;
                rbRoad.setVisibility(View.GONE);
                rbPlease.setVisibility(View.VISIBLE);
                break;
            case 3: //街道选择
                if (rbRoad.getVisibility() == View.GONE) {
                    rbRoad.setVisibility(View.VISIBLE);
                    rbRoad.setOnClickListener(this);
                }
                rbRoad.setText(curArea.getAreaName());
                selRoad = curArea.getId();

                //街道选择完成关闭弹窗

                //TODO 关闭弹窗
                break;
        }

        mPresenter.obtainArea(curArea.getId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_city: //重新选择城市
                curIndex = 1;
                rbCity.setChecked(true);

                mData.clear();
                mData.addAll(cityList);
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.rb_area: //重新选择地区
                curIndex = 2;
                rbArea.setChecked(true);

                mData.clear();
                mData.addAll(areaList);
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.rb_road: //重新选择街道
                curIndex = 3;
                rbRoad.setChecked(true);

                mData.clear();
                mData.addAll(roadList);
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.rb_please_select: //切换到当前的选择
                rbPlease.setChecked(true);
                if (roadList != null) {
                    curIndex = 3;
                    mData.clear();
                    mData.addAll(roadList);
                    mAdapter.notifyDataSetChanged();
                } else if (areaList != null) {
                    curIndex = 2;
                    mData.clear();
                    mData.addAll(areaList);
                    mAdapter.notifyDataSetChanged();
                }
                break;

            case R.id.btn_cancel:
                Toast.makeText(getActivity(), "退出选择", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
