package com.apec.android.domain.goods;

import java.util.List;

/**
 * Created by duanlei on 2016/3/9.
 */
public class Sku {
    private int id;
    private int goodsId;
    private String skuName;
    private String price;
    private List<SkuAttribute> attributeNames;
    private List<Pic> pics;
    private String skuRemark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<SkuAttribute> getAttributeNames() {
        return attributeNames;
    }

    public void setAttributeNames(List<SkuAttribute> attributeNames) {
        this.attributeNames = attributeNames;
    }

    public List<Pic> getPics() {
        return pics;
    }

    public void setPics(List<Pic> pics) {
        this.pics = pics;
    }

    public String getSkuRemark() {
        return skuRemark;
    }

    public void setSkuRemark(String skuRemark) {
        this.skuRemark = skuRemark;
    }
}