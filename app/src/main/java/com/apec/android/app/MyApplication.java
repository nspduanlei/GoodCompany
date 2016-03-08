package com.apec.android.app;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.litepal.LitePalApplication;

/**
 * Created by Administrator on 2016/2/25.
 */
public class MyApplication extends LitePalApplication {

    private static RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeVolley();
    }

    private void initializeVolley() {
        mRequestQueue = Volley.newRequestQueue(this);
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }
}
