package com.apec.android.domain.goods;

import java.util.List;

/**
 * Created by duanlei on 2016/3/9.
 */
public class SkuAttribute {
    private int id;
    private String name;
    private List<SkuAttrValue> attributeValues;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SkuAttrValue> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(List<SkuAttrValue> attributeValues) {
        this.attributeValues = attributeValues;
    }
}
