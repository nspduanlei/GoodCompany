package com.apec.android.mvp.views;

import com.apec.android.domain.entities.goods.Good;

import java.util.List;

public interface GoodsListView extends View {
    void showErrorView(String msg);
    void hideErrorView();
    void bindGoods(List<Good> goods);
    void showDetailScreen(int goodId);
}
