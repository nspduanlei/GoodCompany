package com.apec.android.ui.presenter.goods;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.android.volley.VolleyError;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.goods.GateGory;
import com.apec.android.domain.goods.interator.GoodsInteract;
import com.apec.android.domain.user.Area;
import com.apec.android.domain.user.Areas;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;
import com.apec.android.util.LocationHelp;
import com.apec.android.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/2/26.
 */
public class GoodsPresenter extends BasePresenter<GoodsPresenter.IView> {

    public GoodsPresenter(Context context) {
        super(context);
    }


    LocationHelp mLocationHelp = new LocationHelp();
    String cityCode, cityName;

    /**
     * 启动定位
     */
    public void startLocation() {
        mLocationHelp.startLocation(mContext, new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (isViewAttached()) {
                    cityName = aMapLocation.getCity();
                    cityCode = aMapLocation.getCityCode();
                    Log.e("test001", "定位成功--->code: " + cityCode + ", name: " + cityName);
                    //城市编码获取成功，获取城市id
                    if (StringUtils.isNullOrEmpty(cityCode)) {
                        //定位失败
                        getView().locationFail();
                    } else {
                        getOpenCityList();
                    }
                    mLocationHelp.shopLocation();
                }
            }
        });
    }

    public void getOpenCityList() {
        GoodsInteract.getOpenCityList(
                new GetDataCallback<JSONObject>() {
                    @Override
                    public void onRepose(JSONObject response) {
                        if (!isViewAttached()) {
                            return;
                        }
                        try {
                            int cityId = response.getInt(cityCode);
                            getView().locationSuccess(cityId, cityName);
                        } catch (JSONException e) {
                            //定位失败
                            getView().locationFail();
                            //e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (isViewAttached()) {
                            getView().locationFail();
                        }
                    }
                });
    }


    /**
     * 获取商品类型
     */
    public void fetchGoodTypes() {
        if (isViewAttached()) {
            getView().showLoading();
        }
        GoodsInteract.fetchCategory(
                mContext,
                new GetDataCallback<GateGory>() {
                    @Override
                    public void onRepose(GateGory response) {
                        if (isViewAttached()) {
                            getView().hideLoading();
                        }
                        int code = response.getH().getCode();
                        if (code == 200) {

                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    /**
     * 获取供选择的开放城市
     */
    public void getOpenCityListArea() {
        if (isViewAttached()) {
            getView().showLoading();
        }
        GoodsInteract.getOpenCityListArea(
                mContext,
                new GetDataCallback<Areas>() {
                    @Override
                    public void onRepose(Areas response) {
                        if (!isViewAttached()) {
                            return;
                        }

                        getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            getView().getCitySuccess(response.getB());
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    public interface IView extends BaseViewInterface {
        void showGoodTypes();

        void locationSuccess(int cityId, String cityName);

        void getCitySuccess(List<Area> areas);

        void locationFail();
    }
}
