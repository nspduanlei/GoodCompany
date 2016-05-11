package com.apec.android.domain.entities.goods;

import java.util.ArrayList;

/**
 * Created by duanlei on 2016/3/9.
 */
public class SkuAttribute {
    private int id;
    private String name;

    /**
     * 1:非净含量属性，2:净含量属性
     */
    private String type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
