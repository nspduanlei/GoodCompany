package com.apec.android.domain.goods;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/29.
 */
public class Good {
    private int id;
    private String goodsName;
    private int goodsType;
    private String goodsLevel;
    private String goodsRemark;
    private String price;

    private ArrayList<Pic> pics;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsLevel() {
        return goodsLevel;
    }

    public void setGoodsLevel(String goodsLevel) {
        this.goodsLevel = goodsLevel;
    }

    public String getGoodsRemark() {
        return goodsRemark;
    }

    public void setGoodsRemark(String goodsRemark) {
        this.goodsRemark = goodsRemark;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<Pic> getPics() {
        return pics;
    }

    public void setPics(ArrayList<Pic> pics) {
        this.pics = pics;
    }
}
