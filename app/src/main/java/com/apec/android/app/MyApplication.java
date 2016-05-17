package com.apec.android.app;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.apec.android.injector.components.AppComponent;
import com.apec.android.injector.components.DaggerAppComponent;
import com.apec.android.injector.modules.AppModule;
import com.facebook.stetho.Stetho;

import org.litepal.LitePalApplication;

/**
 * Created by Administrator on 2016/2/25.
 */
public class MyApplication extends LitePalApplication {

    private static RequestQueue mRequestQueue;
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeVolley();
        initializeInjector();
        initializeDebug();
    }

    private void initializeDebug() {
        Stetho.initializeWithDefaults(this);
    }

    private void initializeInjector() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
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

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
