package com.apec.android.domain.transport;

import android.os.Parcel;
import android.os.Parcelable;

import com.apec.android.domain.user.Address;

/**
 * 收货人信息
 * Created by duanlei on 2016/3/11.
 */
public class GoodsReceipt implements Parcelable {

    private int userId;
    private int addressId;
    private String name;
    private String phone;
    private Address addrRes;
    private boolean defalut;

    public boolean isDefalut() {
        return defalut;
    }

    public void setDefalut(boolean defalut) {
        this.defalut = defalut;
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeInt(addressId);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeParcelable(addrRes, flags);
    }

    protected GoodsReceipt(Parcel in) {
        userId = in.readInt();
        addressId = in.readInt();
        name = in.readString();
        phone = in.readString();
        addrRes = in.readParcelable(Address.class.getClassLoader());
    }

    public static final Creator<GoodsReceipt> CREATOR = new Creator<GoodsReceipt>() {
        @Override
        public GoodsReceipt createFromParcel(Parcel in) {
            return new GoodsReceipt(in);
        }

        @Override
        public GoodsReceipt[] newArray(int size) {
            return new GoodsReceipt[size];
        }
    };
}
