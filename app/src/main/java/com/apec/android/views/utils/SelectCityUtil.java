package com.apec.android.views.utils;

import android.app.Activity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import com.android.volley.VolleyError;
import com.apec.android.R;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.entities.user.Area;
import com.apec.android.domain.entities.user.Areas;
import com.apec.android.util.StringUtils;
import com.apec.android.views.adapter.listView.CommonAdapter;
import com.apec.android.views.adapter.listView.MyViewHolder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

/**
 * Created by duanlei on 2016/3/16.
 */
public class SelectCityUtil implements OnClickListener {

    public DialogPlus dialog;
    Activity mActivity;
    SelectArea mSelectArea;

    //forShow
    boolean isShowArea;
    int maxArea = 2;

    public SelectCityUtil(Activity activity, SelectArea selectArea,
                          String selCity, String selArea, String selRoad,
                          int selCityId, int selAreaId, int selRoadId) {
        this(activity, selectArea);
        if (!StringUtils.isNullOrEmpty(selCity)) {
            rbCity.setVisibility(View.VISIBLE);
            rbCity.setText(selCity);

        }
        if (!StringUtils.isNullOrEmpty(selArea)) {
            rbArea.setVisibility(View.VISIBLE);
            rbArea.setText(selArea);
        }
        if (!StringUtils.isNullOrEmpty(selRoad)) {
            rbRoad.setVisibility(View.VISIBLE);
            rbRoad.setText(selRoad);
        }

        if (selCityId != 0) {
            curIndex = 1;
            obtainAreaForShow(1);
            this.selCity = selCityId;
        }
        if (selAreaId != 0) {
            this.selArea = selAreaId;
        }
        if (selRoadId != 0) {
            this.selRoad = selRoadId;
        }

        isShowArea = true;
        rbPlease.setVisibility(View.GONE);
    }

    public interface SelectArea{
        void selectCityFinish(String areaStr, int selCityId, int selAreaId, int selRoadId);
    }

    public SelectCityUtil(Activity activity, SelectArea selectArea) {
        mActivity = activity;
        mSelectArea = selectArea;

        View dialogView = activity.getLayoutInflater()
                .inflate(R.layout.fragment_select_city, null);

        initView(dialogView);

        ViewHolder viewHolder = new ViewHolder(dialogView);

        dialog = new DialogPlus.Builder(activity)
                .setContentHolder(viewHolder)
                .setCancelable(true)
                .setGravity(DialogPlus.Gravity.BOTTOM)
                .setOnClickListener(this)
                .create();

        if (!isShowArea) {
            obtainArea(1);
        }
    }

    ArrayList<Area> mData;
    BaseAdapter mAdapter;
    Area curArea;
    RadioButton rbCity, rbArea, rbRoad, rbPlease;
    ArrayList<Area> cityList, areaList, roadList;
    int curIndex = 1;
    int selCity, selArea, selRoad;

    private void initView(View view) {
        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<Area>(mActivity, mData, R.layout.select_city_item) {
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

        ListView areas = (ListView) view.findViewById(R.id.lv_areas);
        areas.setAdapter(mAdapter);

        areas.setOnItemClickListener((parent, view1, position, id) -> {
            curArea = mData.get(position);
            switch (curIndex) {
                case 1: //城市选择
                    if (rbCity.getVisibility() == View.GONE) {
                        rbCity.setVisibility(View.VISIBLE);
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
                    rbPlease.setChecked(true);

                    //获取下一级数据
                    obtainArea(curArea.getId());
                    break;
                case 2: //地区选择
                    if (rbArea.getVisibility() == View.GONE) {
                        rbArea.setVisibility(View.VISIBLE);
                    }
                    rbArea.setText(curArea.getAreaName());
                    selArea = curArea.getId();

                    //地区选择完，切换到选择街道
//                        curIndex = 3;
//                        if (rbRoad.getVisibility() == View.GONE) {
//                            rbPlease.setChecked(true);
//                        } else {
//                            rbRoad.setChecked(true);
//                        }

                    //清除掉与上一次选择关联的区或街道
                    //selRoad = 0;
                    //rbRoad.setVisibility(View.GONE);
                    //rbPlease.setVisibility(View.VISIBLE);
                    //rbPlease.setChecked(true);

                    //TODO 不选择街道
                    rbArea.setChecked(true);
                    rbPlease.setVisibility(View.GONE);

                    //街道选择完成关闭弹窗
                    dismiss();

                    break;
                case 3: //街道选择
                    if (rbRoad.getVisibility() == View.GONE) {
                        rbRoad.setVisibility(View.VISIBLE);
                    }
                    rbRoad.setText(curArea.getAreaName());
                    selRoad = curArea.getId();
                    rbRoad.setChecked(true);
                    rbPlease.setVisibility(View.GONE);

                    //街道选择完成关闭弹窗
                    dismiss();
                    break;
            }
        });

        rbCity = (RadioButton) view.findViewById(R.id.rb_city);
        rbArea = (RadioButton) view.findViewById(R.id.rb_area);
        rbRoad = (RadioButton) view.findViewById(R.id.rb_road);
        rbPlease = (RadioButton) view.findViewById(R.id.rb_please_select);
    }

    /**
     * 获取地区数据
     * @param id
     */
    public void obtainArea(int id) {
//        UserInteract.obtainArea(mActivity, new GetDataCallback<Areas>() {
//            @Override
//            public void onRepose(Areas response) {
//                if (response.getH().getCode() == 200) {
//                    //获取地区成功
//                    ArrayList<Area> areas = response.getB();
//
//                    if (areas.size() > 0) { //如果有地区数据
//                        switch (curIndex) {
//                            case 1:
//                                cityList = areas;
//                                break;
//                            case 2:
//                                areaList = areas;
//                                break;
//                            case 3:
//                                roadList = areas;
//                                break;
//                        }
//                        mData.clear();
//                        mData.addAll(areas);
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }, String.valueOf(id));
    }


    public void obtainAreaForShow(int id) {
//        UserInteract.obtainArea(mActivity, new GetDataCallback<Areas>() {
//            @Override
//            public void onRepose(Areas response) {
//                if (response.getH().getCode() == 200) {
//                    //获取地区成功
//                    ArrayList<Area> areas = response.getB();
//
//                    if (areas.size() > 0) { //如果有地区数据
//                        switch (curIndex) {
//                            case 1:
//                                cityList = areas;
//                                curIndex = 2;
//                                obtainAreaForShow(selArea);
//                                break;
//                            case 2:
//                                areaList = areas;
//                                rbCity.performClick();
//                                break;
//                            case 3:
//                                roadList = areas;
//                                break;
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }, String.valueOf(id));
    }

    @Override
    public void onClick(DialogPlus dialog, View view) {
        switch (view.getId()) {
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
                dismiss();
                break;
        }
    }

    public void dismiss() {
        String areaStr = rbCity.getText().toString() + rbArea.getText().toString() +
                rbRoad.getText().toString();
        dialog.dismiss();
        mSelectArea.selectCityFinish(areaStr, selCity, selArea, selRoad);
    }
}
