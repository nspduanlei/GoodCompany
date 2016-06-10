package com.apec.android.views.utils;

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
        if (queryCityByCityId(openCity.getCityId()) != null) {
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
    public static List<SkuData> queryAll() {
        return null;
    }


    //更具id查询
    public static OpenCity queryCityByCityId(String cityId) {

        List<OpenCity> list =
                DataSupport.where("cityid = ?", cityId).find(OpenCity.class);

        return list.get(0);
    }
}
