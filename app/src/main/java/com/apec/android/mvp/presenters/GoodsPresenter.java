package com.apec.android.mvp.presenters;


import android.util.Log;

import com.apec.android.config.Constants;
import com.apec.android.domain.entities.goods.Goods;
import com.apec.android.domain.entities.user.Area;
import com.apec.android.domain.entities.user.Areas;
import com.apec.android.domain.usercase.GetAllCityUseCase;
import com.apec.android.mvp.views.GoodsView;
import com.apec.android.mvp.views.View;
import com.apec.android.util.LocationHelp;

import javax.inject.Inject;

import rx.functions.Func1;

/**
 * Created by duanlei on 2016/5/10.
 */
public class GoodsPresenter implements Presenter {

    GetAllCityUseCase mGetAllCityUseCase;
    LocationHelp mLocationHelp;
    String mCityName, mCityCode;
    int mCityId;

    GoodsView mGoodsView;

    @Inject
    public GoodsPresenter(GetAllCityUseCase getAllCityUseCase, LocationHelp locationHelp) {
        mLocationHelp = locationHelp;
        mGetAllCityUseCase = getAllCityUseCase;
    }

    public void initPresenter(int cityId) {
        mCityId = cityId;
    }

    /**
     * 启动定位
     */
    public void startLocation() {
        mLocationHelp.startLocation(aMapLocation -> {
            mCityName = aMapLocation.getCity();
            mCityCode = aMapLocation.getCityCode();
            Log.e("test001", "定位成功--->code: " + mCityCode + ", name: " + mCityName);
            //城市编码获取成功，获取城市id
            getOpenCityFile();
            mLocationHelp.shopLocation();
        });
    }

    public void getOpenCityFile() {
        //TODO 获取开放城市数据，匹配 cityCode

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mGoodsView = (GoodsView) v;
    }

    @Override
    public void onCreate() {

    }

    public void getOpenCityList() {
        mGetAllCityUseCase.execute()
                .map(areas -> {
                    if (mCityId != 0) {
                        for (Area area : areas.getB()) {
                            if (area.getId() == mCityId) {
                                area.setSelect(true);
                            }
                        }
                    }
                    return areas;
                })
                .subscribe(this::onGoodsReceived, this::manageGoodsError);
    }

    private void onGoodsReceived(Areas areas) {
        if (areas.getH().getCode() == Constants.SUCCESS_CODE) {
            mGoodsView.bindCity(areas.getB());
        } else {
            mGoodsView.showErrorView(areas.getH().getMsg());
        }
    }

    //发生错误
    private void manageGoodsError(Throwable error) {
        mGoodsView.showErrorView(error.toString());
    }
}
