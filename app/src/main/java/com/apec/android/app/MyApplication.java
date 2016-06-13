package com.apec.android.app;

import com.apec.android.injector.components.AppComponent;
import com.apec.android.injector.components.DaggerAppComponent;
import com.apec.android.injector.modules.AppModule;
import com.facebook.stetho.Stetho;

import org.litepal.LitePalApplication;

/**
 * Created by Administrator on 2016/2/25.
 */
public class MyApplication extends LitePalApplication {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
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

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
