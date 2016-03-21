package com.apec.android.domain.user;

import com.apec.android.domain.goods.Sku;

/**
 * Created by duanlei on 2016/3/21.
 */
public class Skus {
    private int count;
    private Sku sku;
    private boolean isSelectCart = true;

    public boolean isSelectCart() {
        return isSelectCart;
    }

    public void setIsSelectCart(boolean isSelectCart) {
        this.isSelectCart = isSelectCart;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }
}
