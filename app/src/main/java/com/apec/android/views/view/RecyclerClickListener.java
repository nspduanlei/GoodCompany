package com.apec.android.views.view;

import com.apec.android.domain.entities.goods.Sku;

/**
 * Created by duanlei on 2016/5/10.
 */
public interface RecyclerClickListener {
    void onElementClick(int position);

    void onAddCount(Sku sku, int position);
    void onCutCount(Sku sku, int position);

    void onOrderClick(int skuId, int count);

    void onSaveCartClick(Sku sku, int position);
}
