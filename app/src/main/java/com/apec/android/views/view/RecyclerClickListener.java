package com.apec.android.views.view;

import com.apec.android.domain.entities.goods.Sku;

/**
 * Created by duanlei on 2016/5/10.
 */
public interface RecyclerClickListener {
    void onElementClick(int position);

    /**
     * 修改商品数量 ， -1数量减1， 1数量加1
     * @param sku
     * @param position
     * @param num
     */
    void onUpdateCount(Sku sku, int position, int num);

    void onOrderClick(int skuId, int count);

    void onSaveCartClick(Sku sku, int position);
}
