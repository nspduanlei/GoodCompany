package com.apec.android.domain.entities.user;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by duanlei on 2016/4/21.
 */
public class OpenCity extends DataSupport {

    private int id;

    @Column(unique = true, defaultValue = "unknown")
    private int cityId;
    private String cityCode;
    private String cityName;
    private boolean isLocation;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
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
