package com.apec.android.views.utils;

import android.content.ContentValues;

import com.apec.android.app.MyApplication;
import com.apec.android.domain.entities.goods.SkuData;
import com.apec.android.domain.entities.user.OpenCity;
import com.apec.android.util.L;
import com.apec.android.util.SPUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanlei on 2016/6/8.
 */
public class ShopCartUtil {

    //添加数据库
    public static void addData(SkuData skuData) {
        int cityId = (int) SPUtils.get(MyApplication.getContext(), SPUtils.LOCATION_CITY_ID, 0);
        try {
            skuData.setCityId(cityId);
            skuData.saveThrows();
        } catch (Exception e) {
            L.e(e.toString());
        }
    }

    //查询购物车数量
    public static int queryAllNum() {
        return queryAll().size();
    }

    public static int querySkuNum() {
        List<SkuData> list = queryAll();
        int count = 0;
        for (SkuData skuData : list) {
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
        for (SkuData skuData : data) {
            skuData.delete();
        }
    }

    //查询全部数据
    public static List<SkuData> queryAll() {
        //更具当前城市id, 获取商品列表
        int cityId = (int) SPUtils.get(MyApplication.getContext(), SPUtils.LOCATION_CITY_ID, 0);

        List<SkuData> list = DataSupport.where("cityId = ?", String.valueOf(cityId))
                .find(SkuData.class);

        return list;
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
     *
     * @param skuData
     * @param isCheck
     */
    public static void updateCheck(SkuData skuData, boolean isCheck) {
        if (skuData != null) {
//            skuData.setSelect(isCheck);
//            skuData.update(skuData.getId());

            ContentValues values = new ContentValues();
            if (isCheck) {
                values.put("isSelect", "1");
            } else {
                values.put("isSelect", "0");
            }

            DataSupport.update(SkuData.class, values, skuData.getId());
        }
    }

    public static void clear() {
        DataSupport.deleteAll(SkuData.class);
    }

    //获取没有添加到远程的商品列表
    public static List<SkuData> getNotSaveList() {
        List<SkuData> list =
                DataSupport.where("isSave = ?", "0").find(SkuData.class);
        return list;
    }

    //商品添加到远程后修改商品状态
    public static void updateIsSave() {
        ContentValues values = new ContentValues();
        values.put("isSave", "1");
        DataSupport.updateAll(SkuData.class, values, "isSave = ?", "0");
    }

    /**
     * 全选
     */
    public static void selectAll() {
        int cityId = (int) SPUtils.get(MyApplication.getContext(), SPUtils.LOCATION_CITY_ID, 0);

        ContentValues values = new ContentValues();
        values.put("isSelect", "1");
        DataSupport.updateAll(SkuData.class, values, "isSelect = ? and cityId = ?", "0",
                String.valueOf(cityId));
    }

    /**
     * 取消全选
     */
    public static void cancelSelectAll() {
        int cityId = (int) SPUtils.get(MyApplication.getContext(), SPUtils.LOCATION_CITY_ID, 0);

        ContentValues values = new ContentValues();
        values.put("isSelect", "0");
        DataSupport.updateAll(SkuData.class, values, "isSelect = ? and cityId = ?", "1",
                String.valueOf(cityId));
    }

    /**
     * 获取选择的所以商品总价
     * @return
     */
    public static Double getSelectPrice() {
        List<SkuData> list = getSelectList();

        Double totalPrice = 0.0;

        for (SkuData skuData:list) {
            totalPrice = totalPrice + Double.valueOf(skuData.getPrice()) * skuData.getCount();
        }

        return totalPrice;
    }

    /**
     * 获取选择的所以商品总数量
     * @return
     */
    public static int getSelectCount() {
        List<SkuData> list = getSelectList();

        return list.size();
    }

    public static List<SkuData> getSelectList() {
        int cityId = (int) SPUtils.get(MyApplication.getContext(), SPUtils.LOCATION_CITY_ID, 0);

        List<SkuData> list =
                DataSupport.where("isSelect = ? and cityId = ?", "1",
                        String.valueOf(cityId)).find(SkuData.class);
        return list;
    }
}
