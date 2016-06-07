package com.apec.android.views.view;

/**
 * Created by duanlei on 2016/5/10.
 */
public interface RecyclerClickListener {
    void onElementClick(int position);
    void onAddCartClick(int skuId, int count);
    void onOrderClick(int skuId, int count);



}
