package com.apec.android.domain.goods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanlei on 2016/3/9.
 */
public class SkuAttribute {
    private int id;
    private String name;
    private ArrayList<SkuAttrValue> attributeValues;

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

    public ArrayList<SkuAttrValue> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(ArrayList<SkuAttrValue> attributeValues) {
        this.attributeValues = attributeValues;
    }
}
