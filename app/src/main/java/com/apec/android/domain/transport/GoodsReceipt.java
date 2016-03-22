package com.apec.android.domain.transport;

import com.apec.android.domain.user.Address;

/**
 * 收货人信息
 * Created by duanlei on 2016/3/11.
 */
public class GoodsReceipt {
    private int userId;
    private int addressId;
    private String name;
    private String phone;
    private Address addrRes;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddrRes() {
        return addrRes;
    }

    public void setAddrRes(Address addrRes) {
        this.addrRes = addrRes;
    }
}
