package com.apec.android.domain.entities.user;

import com.apec.android.domain.H;

/**
 * Created by duanlei on 2016/6/24.
 */
public class Version {

    private H h;
    private data b;

    public H getH() {
        return h;
    }

    public void setH(H h) {
        this.h = h;
    }

    public data getB() {
        return b;
    }

    public void setB(data b) {
        this.b = b;
    }

    class data {
        private int id;
        private int versionNo;
        private String remake;
        private String url;
    }
}
