package com.apec.android.views.view;

import com.apec.android.domain.entities.transport.GoodsReceipt;

/**
 * Created by duanlei on 2016/5/10.
 */
public interface AddressListClickListener {
    void onCBDefaultClick(int addressId);
    void onDeleteClick(int addressId);
    void onEditClick(GoodsReceipt goodsReceipt);
    void onElementClick(int addressId);
}
