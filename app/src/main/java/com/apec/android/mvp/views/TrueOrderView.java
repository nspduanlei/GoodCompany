package com.apec.android.mvp.views;

import com.apec.android.domain.entities.transport.ReceiptDefault;

/**
 * Created by duanlei on 2016/6/13.
 */
public interface TrueOrderView extends View {
    void bindArriveTime(String time);
    void onOrderSuccess();

    void onGetDefaultAddress(ReceiptDefault receiptDefault);
}
