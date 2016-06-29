package com.apec.android.mvp.views;

import com.apec.android.domain.entities.transport.TransportInfo;
import com.apec.android.domain.entities.transport.TransportInfoItem;

import java.util.List;

/**
 * Created by duanlei on 2016/6/28.
 */
public interface TransportView extends View {
    void bindTransports(List<TransportInfoItem> b);
}
