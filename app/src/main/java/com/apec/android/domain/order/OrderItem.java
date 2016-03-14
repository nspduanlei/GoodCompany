package com.apec.android.domain.order;

import com.apec.android.domain.goods.Sku;

/**
 * Created by duanlei on 2016/3/10.
 */
public class OrderItem {
    private int id;
    private Sku sku;
    private int num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
