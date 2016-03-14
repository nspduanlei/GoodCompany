package com.apec.android.support.http;

import android.content.Context;

import com.apec.android.util.AppUtils;
import com.apec.android.util.ScreenUtils;

/**
 * Created by duanlei on 2016/3/7.
 */
public class RequestHelper {

    Context mContext;
    public RequestHelper(Context context) {
        mContext = context;
    }

    public String getHeaderUserAgent() {
        return AppUtils.getAppName(mContext) + "/"
                + AppUtils.getVersionCode(mContext) + "&ADR&"
                + ScreenUtils.getScreenWidth(mContext) + "&"
                + ScreenUtils.getScreenHeight(mContext) + "&"
                + AppUtils.getModel();
    }
}
