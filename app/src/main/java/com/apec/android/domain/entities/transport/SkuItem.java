package com.apec.android.domain.entities.transport;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by duanlei on 2016/6/29.
 */
public class SkuItem implements Parcelable {

    private String goodsName;
    private String skuName;
    private int num;
    private int skuId;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(goodsName);
        parcel.writeString(skuName);
        parcel.writeInt(num);
        parcel.writeInt(skuId);
    }

    protected SkuItem(Parcel in) {
        goodsName = in.readString();
        skuName = in.readString();
        num = in.readInt();
        skuId = in.readInt();
    }

    public static final Creator<SkuItem> CREATOR = new Creator<SkuItem>() {
        @Override
        public SkuItem createFromParcel(Parcel in) {
            return new SkuItem(in);
        }

        @Override
        public SkuItem[] newArray(int size) {
            return new SkuItem[size];
        }
    };
}
