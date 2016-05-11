package com.apec.android.domain.entities.order;

/**
 * 订单处理记录
 * Created by duanlei on 2016/3/10.
 */
public class ProRecord {
    private int type;
    private String time;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
