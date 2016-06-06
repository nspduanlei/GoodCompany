package com.apec.android.domain.entities.user;

import org.litepal.crud.DataSupport;

/**
 * Created by duanlei on 2016/4/21.
 */
public class OpenCity  extends DataSupport {

    private String cityId;
    private String cityCode;
    private String cityName;
    private boolean isLocation;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public boolean isLocation() {
        return isLocation;
    }

    public void setLocation(boolean location) {
        isLocation = location;
    }
}
