package com.apec.android.domain.goods;

import com.apec.android.domain.H;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/29.
 */
public class Goods {
    private H h;
    private Body b;

    class Body {
        private int pageNo;
        private int pageSize;
        private int dataTotal;
        private int pageTotal;

        private ArrayList<Good> data;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getDataTotal() {
            return dataTotal;
        }

        public void setDataTotal(int dataTotal) {
            this.dataTotal = dataTotal;
        }

        public int getPageTotal() {
            return pageTotal;
        }

        public void setPageTotal(int pageTotal) {
            this.pageTotal = pageTotal;
        }

        public ArrayList<Good> getData() {
            return data;
        }

        public void setData(ArrayList<Good> data) {
            this.data = data;
        }
    }
}
