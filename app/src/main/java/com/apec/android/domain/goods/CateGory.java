package com.apec.android.domain.goods;

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


    //    class Cate1 {
//        private int id;
//        private String name;
//        private ArrayList<Cate2> subNode;
//
//        class Cate2 {
//            private int id;
//            private String name;
//            private ArrayList<Cate3> subNode;
//
//            class Cate3 {
//                private int id;
//                private String name;
//
//                public int getId() {
//                    return id;
//                }
//
//                public void setId(int id) {
//                    this.id = id;
//                }
//
//                public String getName() {
//                    return name;
//                }
//
//                public void setName(String name) {
//                    this.name = name;
//                }
//            }
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public ArrayList<Cate2> getSubNode() {
//            return subNode;
//        }
//
//        public void setSubNode(ArrayList<Cate2> subNode) {
//            this.subNode = subNode;
//        }
//    }
}
