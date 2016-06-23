package com.apec.android.domain.entities.user;

import com.apec.android.domain.entities.goods.SkuData;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanlei on 2016/3/21.
 */
public class ShopCartData {
    private int total;
    private String totalPrice;
    private List<SkuData> skus;


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

    public List<SkuData> getSkus() {
        return skus;
    }

    public void setSkus(List<SkuData> skus) {
        this.skus = skus;
    }

    /**
     * 批量加入购物车
     * @param skus
     */
    public static void addCarts(ArrayList<Skus> skus) {
        ArrayList<SkuData> skuDatas = new ArrayList<>();
        for (Skus item:skus) {
            skuDatas.add(new SkuData(item));
        }
        DataSupport.saveAll(skuDatas);
    }
}
