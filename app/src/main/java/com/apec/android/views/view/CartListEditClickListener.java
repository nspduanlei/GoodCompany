package com.apec.android.views.view;

import com.apec.android.domain.entities.goods.SkuData;

/**
 * Created by duanlei on 2016/5/10.
 */
public interface CartListEditClickListener {
    void onElementClick(int position);

    void onCheckChange(SkuData skuData, boolean isCheck, int position);
}
