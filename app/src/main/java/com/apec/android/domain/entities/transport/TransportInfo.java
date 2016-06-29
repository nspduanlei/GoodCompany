package com.apec.android.domain.entities.transport;

import com.apec.android.domain.H;

import java.util.List;

/**
 * Created by duanlei on 2016/6/29.
 */
public class TransportInfo {

    private H h;

    private List<TransportInfoItem> b;

    public H getH() {
        return h;
    }

    public void setH(H h) {
        this.h = h;
    }

    public List<TransportInfoItem> getB() {
        return b;
    }

    public void setB(List<TransportInfoItem> b) {
        this.b = b;
    }


}
