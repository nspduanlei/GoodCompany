package com.apec.android.domain.entities.goods;

import com.apec.android.domain.H;

import java.util.ArrayList;

/**
 * Created by duanlei on 2016/3/16.
 */
public class GateGory {
    private H h;
    private ArrayList<CateGory> b;

    public H getH() {
        return h;
    }

    public void setH(H h) {
        this.h = h;
    }

    public ArrayList<CateGory> getB() {
        return b;
    }

    public void setB(ArrayList<CateGory> b) {
        this.b = b;
    }
}
