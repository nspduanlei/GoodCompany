package com.apec.android.domain.entities.transport;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by duanlei on 2016/6/29.
 */
public class TransportInfoItem implements Parcelable {

    private int orderId;
    private String supplierName;
    private String name;
    private String phone;

    //运单号
    private String trackingNumber;

    private List<SkuItem> dital;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public List<SkuItem> getDital() {
        return dital;
    }

    public void setDital(List<SkuItem> dital) {
        this.dital = dital;
    }


    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(orderId);
        parcel.writeString(supplierName);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeTypedList(dital);
    }


    protected TransportInfoItem(Parcel in) {
        orderId = in.readInt();
        supplierName = in.readString();
        name = in.readString();
        phone = in.readString();
        dital = in.createTypedArrayList(SkuItem.CREATOR);
    }

    public static final Creator<TransportInfoItem> CREATOR = new Creator<TransportInfoItem>() {
        @Override
        public TransportInfoItem createFromParcel(Parcel in) {
            return new TransportInfoItem(in);
        }

        @Override
        public TransportInfoItem[] newArray(int size) {
            return new TransportInfoItem[size];
        }
    };
}
