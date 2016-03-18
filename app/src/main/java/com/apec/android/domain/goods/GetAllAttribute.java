package com.apec.android.domain.goods;

import com.apec.android.domain.H;

import java.util.ArrayList;

/**
 * Created by duanlei on 2016/3/17.
 */
public class GetAllAttribute {
    private H h;
    private ArrayList<SkuAttribute> b;

    public H getH() {
        return h;
    }

    public void setH(H h) {
        this.h = h;
    }

    public ArrayList<SkuAttribute> getB() {
        return b;
    }

    public void setB(ArrayList<SkuAttribute> b) {
        this.b = b;
    }
}
