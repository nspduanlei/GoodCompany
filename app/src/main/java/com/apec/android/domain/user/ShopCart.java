package com.apec.android.domain.user;

import com.apec.android.domain.goods.Sku;

import java.util.ArrayList;

/**
 * Created by duanlei on 2016/3/21.
 */
public class ShopCart {
    private int total;
    private String totalPrice;
    private ArrayList<Skus> skus;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<Skus> getSkus() {
        return skus;
    }

    public void setSkus(ArrayList<Skus> skus) {
        this.skus = skus;
    }
}
