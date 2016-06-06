package com.apec.android.mvp.views;

import com.apec.android.domain.entities.user.Area;
import com.apec.android.domain.entities.user.OpenCity;

import java.util.ArrayList;
import java.util.List;

public interface GoodsView extends View {
    void showErrorView(String s);
    void hideErrorView(String s);
    void bindLocation(String cityId, String cityName);
    void bindCity(List<Area> areas);

    void startLocation();
    void locationSuccess(ArrayList<OpenCity> openCities, String cityName);
}
