package com.apec.android.mvp.presenters;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.apec.android.domain.entities.goods.CategoryBack;
import com.apec.android.domain.entities.transport.ReceiptDefault;
import com.apec.android.domain.entities.user.OpenCity;
import com.apec.android.domain.usercase.CityIsOpenUseCase;
import com.apec.android.domain.usercase.GetCategoryUseCase;
import com.apec.android.domain.usercase.GetDefaultAddressUseCase;
import com.apec.android.mvp.views.GoodsView;
import com.apec.android.mvp.views.View;
import com.apec.android.util.L;
import com.apec.android.util.LocationHelp;
import com.apec.android.util.StringUtils;
import com.apec.android.views.utils.CityUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by duanlei on 2016/5/10.
 */
public class GoodsPresenter implements Presenter {

    LocationHelp mLocationHelp;
    CityIsOpenUseCase mCityIsOpenUseCase;
    GetDefaultAddressUseCase mGetDefaultAddressUseCase;
    GetCategoryUseCase mGetCategoryUseCase;

    String mCityName, mCityCode;

    int mCityId = 0;

    GoodsView mGoodsView;

    boolean isReLocation = false;

    @Inject
    public GoodsPresenter(LocationHelp locationHelp,
                          CityIsOpenUseCase cityIsOpenUseCase,
                          GetDefaultAddressUseCase getDefaultAddressUseCase,
                          GetCategoryUseCase getCategoryUseCase) {
        mLocationHelp = locationHelp;
        mCityIsOpenUseCase = cityIsOpenUseCase;
        mGetDefaultAddressUseCase = getDefaultAddressUseCase;
        mGetCategoryUseCase = getCategoryUseCase;
    }

    /**
     * 启动定位
     */
    public void startLocation() {
        mGoodsView.startLocation();
        mLocationHelp.startLocation(aMapLocation -> {
            locationBack(aMapLocation);
        });

    }

    public void getOpenCityFile() {
        mCityIsOpenUseCase.execute()
                .observeOn(Schedulers.io())
                .doOnNext(cityList -> {
                    //将开放城市信息存储在数据库中
                    for (OpenCity openCity : cityList) {
                        if (mCityCode != null && mCityCode.equals(openCity.getCityCode())) {
                            openCity.setLocation(true);
                            mCityId = openCity.getCityId();
                        }
                        CityUtil.addData(openCity);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::cityIsOpenReceived, this::manageIsOpenError);
    }

    private void manageIsOpenError(Throwable throwable) {
        L.e("error");
        mGoodsView.showErrorView("");
    }

    private void cityIsOpenReceived(ArrayList<OpenCity> data) {
        if (mCityId == 0) {
            mGoodsView.locationFail(isReLocation);
        } else {
            mGoodsView.locationSuccess(mCityId, mCityName, isReLocation);
        }
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

    //获取默认地址
    public void getDefaultAddress() {
        mGetDefaultAddressUseCase.execute()
                .subscribe(this::onDefaultAddressReceived, this::manageDefaultAddressError);
    }

    private void manageDefaultAddressError(Throwable throwable) {

    }

    private void onDefaultAddressReceived(ReceiptDefault receiptDefault) {
        if (receiptDefault.getH().getCode() == 200) {
            mGoodsView.bindDefaultAddress(receiptDefault.getB());
        }
    }

    public void updateSelectCity(int cityId) {
        new Thread(() -> {
            CityUtil.updateSelect(cityId);
        }).start();
    }

    /**
     * 重新定位
     */
    public void reLocation() {
        mCityId = 0;
        isReLocation = true;
        mGoodsView.startLocation();
        mLocationHelp.startLocation(aMapLocation -> {
            locationBack(aMapLocation);
        });
    }

    private void locationBack(AMapLocation aMapLocation) {

        if (aMapLocation.getErrorCode() == 0) {
            mCityName = aMapLocation.getCity();
            mCityCode = aMapLocation.getCityCode();

            if (StringUtils.isNullOrEmpty(mCityName)) {
                mCityName.replace("市", "");
            }
        } else {

        }
        mLocationHelp.shopLocation();
        getOpenCityFile();
    }

    //获取商品所以类型
    public void getCategory() {
        mGetCategoryUseCase.execute().subscribe(this::onGetCategoryReceived,
                this::manageDefaultAddressError);
    }

    private void onGetCategoryReceived(CategoryBack categoryBack) {
        if (categoryBack.getH().getCode() == 200) {
            mGoodsView.onGetCategory(categoryBack.getB());
        }
    }
}
