package com.apec.android.domain.entities.transport;

import com.apec.android.domain.H;

/**
 * Created by duanlei on 2016/3/29.
 */
public class ArrivalTime {
    private H h;
    private Time b;

    public H getH() {
        return h;
    }

    public void setH(H h) {
        this.h = h;
    }

    public Time getB() {
        return b;
    }

    public void setB(Time b) {
        this.b = b;
    }

    public class Time {
        String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
