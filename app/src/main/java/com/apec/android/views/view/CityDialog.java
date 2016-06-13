package com.apec.android.views.view;

import android.app.Activity;
import android.view.View;
import android.widget.GridView;

import com.apec.android.R;
import com.apec.android.domain.entities.user.Area;
import com.apec.android.domain.entities.user.OpenCity;
import com.apec.android.util.SPUtils;
import com.apec.android.views.adapter.listView.CommonAdapter;
import com.apec.android.views.adapter.listView.MyViewHolder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanlei on 2016/5/11.
 */
public class CityDialog {

    Activity mActivity;
    List<OpenCity> mCityData = new ArrayList<>();
    CommonAdapter mCityAdapter;

    int mSelectCityId;
    String mSelectCityName;

    CityChangeInterface mCityChangeInterface;

    public CityDialog(Activity activity, List<OpenCity> areas,
                      CityChangeInterface cityChangeInterface) {
        mActivity = activity;
        mCityData = areas;
        mCityChangeInterface = cityChangeInterface;
    }

    public void showSelectCityDialog() {
        View dialogView = mActivity.getLayoutInflater()
                .inflate(R.layout.dialog_select_city, null);

        GridView gridView = (GridView) dialogView.findViewById(R.id.gv_open_city);

        mCityAdapter = new CommonAdapter<OpenCity>(mActivity,
                mCityData, R.layout.item_open_city) {

            @Override
            public void convert(final MyViewHolder holder, final OpenCity city) {

                final int cityId = (int) SPUtils.get(mContext,
                        SPUtils.LOCATION_CITY_ID, 0);

                holder.setText(R.id.rb_city_name, city.getCityName());

                if (city.getCityId() == cityId) {
                    holder.setSelected(R.id.rb_city_name, true);
                } else {
                    holder.setSelected(R.id.rb_city_name, false);
                }

                holder.setOnCheckChangeListerRadio(R.id.rb_city_name,
                        (compoundButton, b) -> {
                            if (b) {
                                mSelectCityId = mCityData.get(holder.getMPosition()).getCityId();
                                mSelectCityName = mCityData.get(holder.getMPosition())
                                        .getCityName();

                                for (OpenCity area : mCityData) {
                                    if (area.getCityId() == mSelectCityId) {
                                        area.setSelect(true);
                                    } else {
                                        area.setSelect(false);
                                    }
                                }
                                mCityAdapter.notifyDataSetChanged();
                            }
                        });
            }
        };

        gridView.setAdapter(mCityAdapter);

        ViewHolder viewHolder = new ViewHolder(dialogView);
        DialogPlus dialog = new DialogPlus.Builder(mActivity)
                .setContentHolder(viewHolder)
                .setCancelable(true)
                .setGravity(DialogPlus.Gravity.CENTER)
                .setBackgroundColorResourceId(R.color.transparency)
                .setOnClickListener((dialog1, view) -> {
                    switch (view.getId()) {
                        case R.id.tv_cancel:
                            dialog1.dismiss();
                            break;
                        case R.id.tv_sure:
                            dialog1.dismiss();
                            mCityChangeInterface.cityChange(mSelectCityId, mSelectCityName);
                            break;
                        default:
                            break;
                    }
                })
                .create();
        dialog.show();
    }

}
