package com.apec.android.views.view;

/**
 * Created by duanlei on 2016/5/10.
 */
public interface CartListClickListener {
    void onElementClick(int position);

    void onAddClick(String skuId);
    void onCutClick(String skuId);
    void onCheckChange(String skuId, boolean isCheck);
}
