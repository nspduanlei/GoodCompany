package com.apec.android.domain.entities.goods;

import java.util.ArrayList;

/**
 * 商品分类
 * Created by duanlei on 2016/3/16.
 */
public class CateGory {
    private int categoryId;
    private String categoryName;
    private ArrayList<CateGory> subNode;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<CateGory> getSubNode() {
        return subNode;
    }

    public void setSubNode(ArrayList<CateGory> subNode) {
        this.subNode = subNode;
    }

}
