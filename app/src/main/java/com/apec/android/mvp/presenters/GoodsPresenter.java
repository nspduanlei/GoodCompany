package com.apec.android.mvp.presenters;


import android.util.Log;

import com.apec.android.config.Constants;
import com.apec.android.domain.entities.goods.Goods;
import com.apec.android.domain.entities.user.Area;
import com.apec.android.domain.entities.user.Areas;
import com.apec.android.domain.entities.user.OpenCity;
import com.apec.android.domain.usercase.CityIsOpenUseCase;
import com.apec.android.domain.usercase.GetAllCityUseCase;
import com.apec.android.mvp.views.GoodsView;
import com.apec.android.mvp.views.View;
import com.apec.android.util.L;
import com.apec.android.util.LocationHelp;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by duanlei on 2016/5/10.
 */
public class GoodsPresenter implements Presenter {

    GetAllCityUseCase mGetAllCityUseCase;
    LocationHelp mLocationHelp;
    CityIsOpenUseCase mCityIsOpenUseCase;

    String mCityName, mCityCode;
    int mCityId;

    GoodsView mGoodsView;

    @Inject
    public GoodsPresenter(GetAllCityUseCase getAllCityUseCase,
                          LocationHelp locationHelp,
                          CityIsOpenUseCase cityIsOpenUseCase) {
        mLocationHelp = locationHelp;
        mGetAllCityUseCase = getAllCityUseCase;
        mCityIsOpenUseCase = cityIsOpenUseCase;
    }

    public void initPresenter(int cityId) {
        mCityId = cityId;
    }

    /**
     * 启动定位
     */
    private void startLocation() {
        mGoodsView.startLocation();

        mLocationHelp.startLocation(aMapLocation -> {
            mCityName = aMapLocation.getCity();
            mCityCode = aMapLocation.getCityCode();
            Log.e("test001", "定位成功--->code: " + mCityCode + ", name: " + mCityName);
            //城市编码获取成功，获取城市id
            mLocationHelp.shopLocation();

            //TODO  test

            mCityName = "深圳";
            mCityCode = "0755";
            getOpenCityFile();
        });
    }

    public void getOpenCityFile() {
        mCityIsOpenUseCase.execute()
                .observeOn(Schedulers.io())
                .doOnNext(cityList -> {
                    //将开放城市信息存储在数据库中
                    for (OpenCity openCity : cityList) {
                        if (mCityCode.equals(openCity.getCityCode())) {
                            openCity.setLocation(true);
                        }
                    }
                    DataSupport.saveAll(cityList);
                })

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::cityIsOpenReceived, this::manageIsOpenError);

    }

    private void manageIsOpenError(Throwable throwable) {
    }

    private void cityIsOpenReceived(ArrayList<OpenCity> data) {
        mGoodsView.locationSuccess(data, mCityName);
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
        startLocation();
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
