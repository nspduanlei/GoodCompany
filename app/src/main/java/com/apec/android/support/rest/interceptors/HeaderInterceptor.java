package com.apec.android.support.rest.interceptors;

import android.content.Context;

import com.apec.android.util.AppUtils;
import com.apec.android.util.SPUtils;
import com.apec.android.util.ScreenUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    Context mContext;

    public HeaderInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("_c", "android");
        params.put("IMEI", AppUtils.getDeviceId(mContext));
        params.put("UA", getHeaderUserAgent());

        String token = (String) SPUtils.get(mContext, SPUtils.SESSION_ID, null);

        if (token != null) {
            params.put("x-auth-token", token);
        }

        Request originalRequest = chain.request();
        Request authorised = originalRequest.newBuilder()
                .headers(Headers.of(params))
                .build();
        return chain.proceed(authorised);
    }

    public String getHeaderUserAgent() {
        return AppUtils.getAppName(mContext) + "/"
                + AppUtils.getVersionCode(mContext) + "&ADR&"
                + ScreenUtils.getScreenWidth(mContext) + "&"
                + ScreenUtils.getScreenHeight(mContext) + "&"
                + AppUtils.getModel();
    }

}

