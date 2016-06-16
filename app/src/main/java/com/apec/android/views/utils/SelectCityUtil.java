package com.apec.android.views.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import com.apec.android.R;
import com.apec.android.domain.entities.user.Area;
import com.apec.android.domain.entities.user.Areas;
import com.apec.android.domain.usercase.GetAreaUseCase;
import com.apec.android.util.StringUtils;
import com.apec.android.views.adapter.listView.CommonAdapter;
import com.apec.android.views.adapter.listView.MyViewHolder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by duanlei on 2016/3/16.
 */
public class SelectCityUtil implements OnClickListener {

    public DialogPlus dialog;

    SelectArea mSelectArea;

    //forShow
    boolean isShowArea;

    GetAreaUseCase mGetAreaUseCase;
    Context mContext;

    @Inject
    public SelectCityUtil(Context context, GetAreaUseCase getAreaUseCase) {
        mContext = context;
        mGetAreaUseCase = getAreaUseCase;
    }

    /**
     * 编辑地址
     * @param selectArea
     * @param selCity
     * @param selArea
     * @param selCityId
     * @param selAreaId
     */
    public void setData(SelectArea selectArea,
                        String selCity, String selArea,
                        int selCityId, int selAreaId) {
        setData(selectArea);

        if (!StringUtils.isNullOrEmpty(selCity)) {
            rbCity.setVisibility(View.VISIBLE);
            rbCity.setText(selCity);

        }
        if (!StringUtils.isNullOrEmpty(selArea)) {
            rbArea.setVisibility(View.VISIBLE);
            rbArea.setText(selArea);
        }

        if (selCityId != 0) {
            curIndex = 1;
            obtainAreaForShow(1);
            this.selCity = selCityId;
        }
        if (selAreaId != 0) {
            this.selArea = selAreaId;
        }

        isShowArea = true;
        rbPlease.setVisibility(View.GONE);
    }

    public void setData(SelectArea selectArea) {
        mSelectArea = selectArea;
        View dialogView = ((Activity) mContext).getLayoutInflater()
                .inflate(R.layout.fragment_select_city, null);
        initView(dialogView);
        ViewHolder viewHolder = new ViewHolder(dialogView);
        dialog = new DialogPlus.Builder(mContext)
                .setContentHolder(viewHolder)
                .setCancelable(true)
                .setGravity(DialogPlus.Gravity.BOTTOM)
                .setOnClickListener(this)
                .create();
        if (!isShowArea) {
            obtainArea(1);
        }
    }

    public interface SelectArea {
        void selectCityFinish(String areaStr, int selCityId, int selAreaId);
    }

    ArrayList<Area> mData;
    BaseAdapter mAdapter;
    Area curArea;

    RadioButton rbCity, rbArea, rbPlease;

    ArrayList<Area> cityList, areaList;

    int curIndex = 1;

    int selCity, selArea;

    private void initView(View view) {
        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<Area>(mContext, mData, R.layout.select_city_item) {
            @Override
            public void convert(MyViewHolder holder, Area area) {
                holder.setText(R.id.tv_area_name, area.getAreaName());
                switch (curIndex) {
                    case 1:
                        if (area.getId() == selCity) {
                            holder.setTextColor(R.id.tv_area_name, R.color.select_city);
                        } else {
                            holder.setTextColor(R.id.tv_area_name, R.color.text_6);
                        }
                        break;
                    case 2:
                        if (area.getId() == selArea) {
                            holder.setTextColor(R.id.tv_area_name, R.color.select_city);
                        } else {
                            holder.setTextColor(R.id.tv_area_name, R.color.text_6);
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
                    rbArea.setVisibility(View.GONE);
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

                    rbArea.setChecked(true);
                    rbPlease.setVisibility(View.GONE);

                    //街道选择完成关闭弹窗
                    dismiss();
                    break;
            }
        });

        rbCity = (RadioButton) view.findViewById(R.id.rb_city);
        rbArea = (RadioButton) view.findViewById(R.id.rb_area);
        rbPlease = (RadioButton) view.findViewById(R.id.rb_please_select);
    }

    /**
     * 获取地区数据
     *
     * @param id
     */
    public void obtainArea(int id) {
        mGetAreaUseCase.setData(id);
        mGetAreaUseCase.execute().subscribe(this::onAreaReceived, this::managerError);
    }

    private void managerError(Throwable throwable) {

    }

    private void onAreaReceived(Areas response) {
        if (response.getH().getCode() == 200) {
            //获取地区成功
            ArrayList<Area> areas = response.getB();

            if (areas.size() > 0) { //如果有地区数据
                switch (curIndex) {
                    case 1:
                        cityList = areas;
                        break;
                    case 2:
                        areaList = areas;
                        break;
                }
                mData.clear();
                mData.addAll(areas);
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    public void obtainAreaForShow(int id) {
        mGetAreaUseCase.setData(id);
        mGetAreaUseCase.execute().subscribe(this::onAreaShowReceived, this::managerError);
    }

    private void onAreaShowReceived(Areas response) {
        if (response.getH().getCode() == 200) {
            //获取地区成功
            ArrayList<Area> areas = response.getB();

            if (areas.size() > 0) { //如果有地区数据
                switch (curIndex) {
                    case 1:
                        cityList = areas;
                        curIndex = 2;
                        obtainAreaForShow(selCity);
                        break;
                    case 2:
                        areaList = areas;
                        rbArea.performClick();
                        break;
                }
            }
        }
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

            case R.id.rb_please_select: //切换到当前的选择
                rbPlease.setChecked(true);

                curIndex = 2;
                mData.clear();
                mData.addAll(areaList);
                mAdapter.notifyDataSetChanged();

                break;

            case R.id.btn_cancel:
                dialog.dismiss();
                break;
        }
    }

    public void dismiss() {
        String areaStr = rbCity.getText().toString() + rbArea.getText().toString();
        dialog.dismiss();
        mSelectArea.selectCityFinish(areaStr, selCity, selArea);
    }
}
