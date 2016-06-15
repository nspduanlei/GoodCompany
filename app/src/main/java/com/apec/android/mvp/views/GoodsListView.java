package com.apec.android.mvp.views;

import com.apec.android.domain.entities.goods.Sku;

import java.util.List;

public interface GoodsListView extends View {
    void showErrorView(String msg);
    void hideErrorView();
    void bindGoods(List<Sku> goods);
    void showDetailScreen(int goodId);

    void updateCountSuccess(int allCount);
}
