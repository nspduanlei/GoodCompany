package com.apec.android.domain.transport;

import com.apec.android.domain.H;

import java.util.ArrayList;

/**
 * Created by duanlei on 2016/3/23.
 */
public class ReceiptList {
    private H h;
    private ArrayList<GoodsReceipt> b;

    public H getH() {
        return h;
    }

    public void setH(H h) {
        this.h = h;
    }

    public ArrayList<GoodsReceipt> getB() {
        return b;
    }

    public void setB(ArrayList<GoodsReceipt> b) {
        this.b = b;
    }
}
