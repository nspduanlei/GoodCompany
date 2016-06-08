package com.apec.android.views.view;

import com.apec.android.domain.entities.goods.Sku;

/**
 * Created by duanlei on 2016/5/10.
 */
public interface RecyclerClickListener {
    void onElementClick(int position);
    void onAddCartClick(Sku sku, int position);
    void onOrderClick(int skuId, int count);

    void onAddCount(Sku sku);
    void onCutCount(Sku sku);

}
