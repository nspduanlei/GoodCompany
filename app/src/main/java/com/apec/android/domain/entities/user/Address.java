package com.apec.android.domain.entities.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by duanlei on 2016/3/10.
 */
public class Address implements Parcelable {
    private String province;
    private String provinceId;
    private String city;
    private String cityId;
    private String area;
    private String areaId;
    private String detail;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(province);
        dest.writeString(provinceId);
        dest.writeString(city);
        dest.writeString(cityId);
        dest.writeString(area);
        dest.writeString(areaId);
        dest.writeString(detail);
    }

    protected Address(Parcel in) {
        province = in.readString();
        provinceId = in.readString();
        city = in.readString();
        cityId = in.readString();
        area = in.readString();
        areaId = in.readString();
        detail = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
