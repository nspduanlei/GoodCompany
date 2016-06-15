package com.apec.android.views.utils;

import android.content.ContentValues;

import com.apec.android.domain.entities.goods.SkuData;
import com.apec.android.domain.entities.user.OpenCity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by duanlei on 2016/6/8.
 */
public class CityUtil {

    //添加数据库
    public static void addData(OpenCity openCity) {
        OpenCity openCityData = queryCityByCityId(String.valueOf(openCity.getCityId()));
        if (openCityData != null) {
            openCity.setId(openCityData.getId());
            update(openCity);
        } else {
            openCity.saveThrows();
        }
    }

    //修改
    public static void update(OpenCity openCity) {
        openCity.update(openCity.getId());
    }


    //查询全部数据
    public static List<OpenCity> queryAll() {
        return DataSupport.findAll(OpenCity.class);
    }

    //更具id查询
    public static OpenCity queryCityByCityId(String cityId) {
        List<OpenCity> list =
                DataSupport.where("cityid = ?", cityId).find(OpenCity.class);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public static void updateSelect(int cityId) {
        ContentValues values = new ContentValues();
        values.put("isSelect", "0");
        DataSupport.updateAll(OpenCity.class, values, "isSelect = ?", "1");

        OpenCity openCitySel = queryCityByCityId(String.valueOf(cityId));
        if (openCitySel != null) {
            openCitySel.setSelect(true);
            openCitySel.update(openCitySel.getId());
        }
    }

    public static OpenCity updateSelectDefault() {
        OpenCity openCity = queryAll().get(0);
        updateSelect(openCity.getCityId());
        return openCity;
    }
}
