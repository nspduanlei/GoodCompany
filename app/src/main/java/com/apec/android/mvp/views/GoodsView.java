package com.apec.android.mvp.views;

import com.apec.android.domain.entities.goods.Category;
import com.apec.android.domain.entities.transport.GoodsReceipt;
import com.apec.android.domain.entities.user.OpenCity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface GoodsView extends View {
    void showErrorView(String s);
    void hideErrorView(String s);

    void bindDefaultAddress(GoodsReceipt goodsReceipt);

    void startLocation();
    void locationSuccess(int cityId, String cityName, boolean isReLocation);

    void locationFail(boolean isReLocation);

    //void reLocationSuccess(int cityId, String cityName, boolean isReLocation);

    void onGetCategory(ArrayList<Category> categories);
}
