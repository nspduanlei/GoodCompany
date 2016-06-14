package com.apec.android.views.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.domain.entities.user.OpenCity;
import com.apec.android.views.adapter.listView.CommonAdapter;
import com.apec.android.views.adapter.listView.MyViewHolder;
import com.apec.android.views.view.CityChangeInterface;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by duanlei on 2016/6/14.
 */
public class LocationDialog {

    Activity mActivity;

    DialogPlus mDialog;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_sure)
    TextView mTvSure;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_hint)

    TextView mTvHint;
    @BindView(R.id.gv_open_city)
    GridView mGvOpenCity;
    @BindView(R.id.tv_hint_1)
    TextView mTvHint1;

    @BindView(R.id.pl_loading)
    GifImageView mPlLoading;

    BaseAdapter mAdapter;
    List<OpenCity> mData;

    int mCityId;
    int mSelectCityId;
    String mSelectCityName;

    CityChangeInterface mCityChangeInterface;

    @Inject
    public LocationDialog(Context context) {
        mActivity = (Activity) context;

        View dialogView = mActivity.getLayoutInflater()
                .inflate(R.layout.dialog_location, null);
        ButterKnife.bind(this, dialogView);

        ViewHolder viewHolder = new ViewHolder(dialogView);

        mDialog = new DialogPlus.Builder(mActivity)
                .setContentHolder(viewHolder)
                .setCancelable(false)
                .setOnBackPressListener(dialogPlus -> mDialog.dismiss())
                .setGravity(DialogPlus.Gravity.CENTER)
                .setBackgroundColorResourceId(R.color.transparency)
                .setOnClickListener((dialog1, view) -> {
                    switch (view.getId()) {
                        case R.id.tv_cancel:
                            mDialog.dismiss();
                            break;
                        case R.id.tv_sure:

                            if (mSelectCityId != mCityId) {
                                mCityId = mSelectCityId;
                                mCityChangeInterface.cityChange(mSelectCityId, mSelectCityName);
                            }

                            mDialog.dismiss();
                            break;
                        default:
                            break;
                    }
                })
                .create();
    }

    /**
     * 正在定位
     */
    public void showLocationDialog() {
        mDialog.show();
        mPlLoading.setVisibility(View.VISIBLE);
        mTvHint1.setVisibility(View.VISIBLE);
        mTvHint1.setText("正在定位城市。。。");
    }

    /**
     * 定位失败
     */
    public void locationFail() {
        mPlLoading.setVisibility(View.GONE);
        mTvTitle.setVisibility(View.VISIBLE);
        mTvTitle.setText("很抱歉");

        mTvHint.setVisibility(View.VISIBLE);
        mTvHint.setText(mActivity.getString(R.string.location_hint_1));

        mTvHint1.setVisibility(View.VISIBLE);
        mTvHint1.setText(mActivity.getString(R.string.location_hint_2));

        initGridView();

    }

    private void initGridView() {
        mGvOpenCity.setVisibility(View.VISIBLE);

        if (mData == null) {
            mData = CityUtil.queryAll();
        }
        if (mAdapter == null) {
            mAdapter = new CommonAdapter<OpenCity>(mActivity, mData, R.layout.item_open_city) {
                @Override
                public void convert(MyViewHolder holder, OpenCity openCity) {
                    holder.setText(R.id.rb_city_name, openCity.getCityName());

                    holder.setSelected(R.id.rb_city_name, openCity.isSelect());

                    holder.setOnCheckChangeListerRadio(R.id.rb_city_name, (compoundButton, b) -> {
                        if (b) {
                            //当选中一个城市其它城市都设置城未选中
                            mSelectCityId = mData.get(holder.getMPosition()).getCityId();
                            mSelectCityName = mData.get(holder.getMPosition()).getCityName();

                            for (OpenCity area : mData) {
                                if (area.getCityId() == mSelectCityId) {
                                    area.setSelect(true);
                                } else {
                                    area.setSelect(false);
                                }
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            };
        }
        mGvOpenCity.setAdapter(mAdapter);
    }


    public void locationSuccess() {
        mDialog.dismiss();
    }

    public void setCityId(int cityId) {
        mCityId = cityId;
    }

    public void setCityChangeInterface(CityChangeInterface cityChangeInterface) {
        mCityChangeInterface = cityChangeInterface;
    }

    public void selectLocation() {
        mPlLoading.setVisibility(View.GONE);
        mTvTitle.setVisibility(View.VISIBLE);
        mTvHint1.setVisibility(View.GONE);
        mTvHint.setVisibility(View.GONE);

        mTvTitle.setText("选择城市");

        initGridView();

        mTvSure.setVisibility(View.VISIBLE);
        mTvCancel.setVisibility(View.VISIBLE);

        mDialog.show();
    }
}
