package com.apec.android.domain.entities.goods;

import com.apec.android.domain.H;

import java.util.ArrayList;

/**
 * Created by duanlei on 16/8/13.
 */
public class CategoryBack {
    private H h;
    private ArrayList<Category> b;

    public H getH() {
        return h;
    }

    public void setH(H h) {
        this.h = h;
    }

    public ArrayList<Category> getB() {
        return b;
    }

    public void setB(ArrayList<Category> b) {
        this.b = b;
    }
}
