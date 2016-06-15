package com.apec.android.mvp.views;

import com.apec.android.domain.entities.transport.GoodsReceipt;
import com.apec.android.domain.entities.user.OpenCity;

import java.util.ArrayList;

public interface GoodsView extends View {
    void showErrorView(String s);
    void hideErrorView(String s);

    void bindDefaultAddress(GoodsReceipt goodsReceipt);

    void startLocation();
    void locationSuccess(int cityId, String cityName);

    void locationFail();

    void reLocationSuccess(int cityId, String cityName);
}
