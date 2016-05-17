package com.apec.android.support.rest.interceptors;

import android.content.Context;
import android.util.Log;

import com.apec.android.util.SPUtils;
import com.apec.android.util.StringUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by duanlei on 2016/5/16.
 */
public class LoggingInterceptor implements Interceptor {

    Context mContext;

    public LoggingInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Log.i("test001", String.format("Sending request %s on %s%n%s", request.url(),
                chain.connection(), request.headers()));
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        Log.i("test001", String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));


        String token = response.headers().get("x-auth-token");
        if (!StringUtils.isNullOrEmpty(token)) {
            SPUtils.put(mContext, SPUtils.SESSION_ID, token);
        }

        return response;
    }
}
