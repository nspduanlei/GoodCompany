package com.apec.android.mvp.views;

import com.apec.android.domain.entities.transport.GoodsReceipt;

import java.util.ArrayList;

/**
 * Created by duanlei on 2016/6/1.
 */
public interface ManageAddressView extends View {
    void bindAddress(ArrayList<GoodsReceipt> addressList);
}
