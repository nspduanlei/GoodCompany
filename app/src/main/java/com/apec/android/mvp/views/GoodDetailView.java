package com.apec.android.mvp.views;

import com.apec.android.domain.entities.goods.Good;

/**
 * Created by duanlei on 2016/5/16.
 */
public interface GoodDetailView extends View {
    void bindGood(Good good);
}
