package com.apec.android.views.utils;

import com.apec.android.domain.entities.goods.SkuData;
import com.apec.android.util.L;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanlei on 2016/6/8.
 */
public class ShopCartUtil {

    //添加数据库
    public static void addData(SkuData skuData) {
//        if (querySkuById(skuData.getSkuId()) != null) {
//            update(skuData);
//        } else {
            try {
                skuData.saveThrows();
            } catch (Exception e) {
                L.e(e.toString());
            }

//        }
    }

    //查询购物车数量
    public static int queryAllNum() {
        return DataSupport.count(SkuData.class);
    }

    public static int querySkuNum() {
        List<SkuData> list = queryAll();
        int count = 0;
        for (SkuData skuData:list) {
            count = count + skuData.getCount();
        }
        return count;
    }

    //删除购物车
    public static void deleteSku(SkuData skuData) {
        skuData.delete();
    }

    //批量删除
    public static void deleteSkuList(ArrayList<SkuData> data) {
        for (SkuData skuData:data) {
            skuData.delete();
        }
    }

    //查询全部数据
    public static List<SkuData> queryAll() {
        return DataSupport.findAll(SkuData.class);
    }

    //修改商品数量
    public static void update(SkuData skuData) {
        skuData.update(skuData.getId());
    }

    //更具id查询sku
    public static SkuData querySkuById(String skuId) {

        List<SkuData> list = DataSupport.where("skuId = ?", skuId).find(SkuData.class);

        if (list.size() == 0) {
            return null;
        }

        return list.get(0);
    }

    /**
     * 修改购物车数量
     *
     * @param i 增量
     */
    public static void updateCount(String skuId, int i) {
        SkuData skuData = querySkuById(skuId);
        if (skuData != null) {
            int count = skuData.getCount();
            int newCount = count + i;
            if (newCount == 0) {
                deleteSkuById(skuData.getSkuId());
            } else {
                skuData.setCount(count + i);
                skuData.update(skuData.getId());
            }
        }
    }

    private static void deleteSkuById(String skuId) {
        DataSupport.deleteAll(SkuData.class, "skuId = ?", skuId);
    }

    /**
     * 修改选择状态
     * @param skuId
     * @param isCheck
     */
    public static void updateCheck(String skuId, boolean isCheck) {
        SkuData skuData = querySkuById(skuId);
        if (skuData != null) {
            skuData.setSelect(isCheck);
            skuData.update(skuData.getId());
        }

    }
}
