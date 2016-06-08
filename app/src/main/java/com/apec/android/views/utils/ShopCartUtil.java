package com.apec.android.views.utils;

import com.apec.android.domain.entities.goods.SkuData;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by duanlei on 2016/6/8.
 */
public class ShopCartUtil {

    //添加数据库
    public static void addData(SkuData skuData) {
        if (querySkuById(skuData.getSkuId()) != null) {
            update(skuData);
        } else {
            skuData.saveThrows();
        }
    }

    //查询购物车数量
    public static int queryAllNum() {
        return DataSupport.count(SkuData.class);
    }

    //删除购物车
    public static void deleteSku(SkuData skuData) {
        skuData.delete();
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

}
