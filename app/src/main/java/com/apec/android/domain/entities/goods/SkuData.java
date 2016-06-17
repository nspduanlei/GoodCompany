package com.apec.android.domain.entities.goods;

import com.apec.android.domain.entities.user.Skus;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by duanlei on 2016/3/9.
 * 购物车商品，用于存入数据库
 */
public class SkuData extends DataSupport {
    private int id;

    @Column(unique = true, defaultValue = "unknown")
    private String skuId;

    private int goodsId;
    private String skuName;

    private String price;
    private String attrName;
    //净含量
    private String attrValue;
    /**
     * 1 上架
     * 2 下架
     * 3 删除
     */
    private int status;
    //购物车数量
    private int count;

    private boolean isSelect;

    private String pic;

    //是否添加到远程数据库
    private boolean isSave;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
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

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public SkuData() {

    }

    public SkuData(Sku sku) {
        this.skuId = String.valueOf(sku.getId());
        this.goodsId = sku.getGoodsId();
        this.skuName = sku.getSkuName();
        this.price = sku.getPrice();
        this.count = sku.getCount();

        for (SkuAttribute skuAttribute:sku.getNonkeyAttr()) {
            if (skuAttribute.getType().equals("2")) {
                //净含量属性
                this.attrName = skuAttribute.getName();
                this.attrValue = skuAttribute.getAttributeValues().get(0).getName();
            }
        }
        this.status = sku.getStatus();
        this.pic = sku.getPics().get(0).getUrl();
    }

    public SkuData(Skus item) {
        Sku sku = item.getSku();

        this.skuId = String.valueOf(sku.getId());
        this.goodsId = sku.getGoodsId();
        this.skuName = sku.getSkuName();
        this.price = sku.getPrice();
        this.count = item.getCount();

        for (SkuAttribute skuAttribute:sku.getNonkeyAttr()) {
            if (skuAttribute.getType().equals("2")) {
                //净含量属性
                this.attrName = skuAttribute.getName();
                this.attrValue = skuAttribute.getAttributeValues().get(0).getName();
            }
        }
        this.status = sku.getStatus();
        this.pic = sku.getPics().get(0).getUrl();
    }

}
