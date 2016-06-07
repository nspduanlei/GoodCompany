package com.apec.android.domain.entities.goods;

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
    private List<SkuAttribute> nonkeyAttr;
    private List<Pic> pics;
    private String skuRemark;
    /**
     * 1 上架
     * 2 下架
     * 3 删除
     */
    private int status;
    //购物车数量
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public List<SkuAttribute> getNonkeyAttr() {
        return nonkeyAttr;
    }

    public void setNonkeyAttr(List<SkuAttribute> nonkeyAttr) {
        this.nonkeyAttr = nonkeyAttr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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
