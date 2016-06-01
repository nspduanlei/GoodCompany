package com.apec.android.domain.entities.transport;

/**
 * Created by duanlei on 2016/6/1.
 * 收货人信息
 */
public class ReceiptInfo {

    private int addressId;
    private String phone;
    private String userName;
    private int addressCity;
    private int addressAreaCounty;
    private String detailAddress;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(int addressCity) {
        this.addressCity = addressCity;
    }

    public int getAddressAreaCounty() {
        return addressAreaCounty;
    }

    public void setAddressAreaCounty(int addressAreaCounty) {
        this.addressAreaCounty = addressAreaCounty;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }


    public ReceiptInfo(int addressId, String phone, String userName, int addressCity,
                       int addressAreaCounty, String detailAddress) {
        this.addressId = addressId;
        this.phone = phone;
        this.userName = userName;
        this.addressCity = addressCity;
        this.addressAreaCounty = addressAreaCounty;
        this.detailAddress = detailAddress;
    }

    public ReceiptInfo(String phone, String userName, int addressCity,
                       int addressAreaCounty, String detailAddress) {
        this.phone = phone;
        this.userName = userName;
        this.addressCity = addressCity;
        this.addressAreaCounty = addressAreaCounty;
        this.detailAddress = detailAddress;
    }
}
