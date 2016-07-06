package com.apec.android.app;

import com.apec.android.injector.components.AppComponent;
import com.apec.android.injector.components.DaggerAppComponent;
import com.apec.android.injector.modules.AppModule;
import com.facebook.stetho.Stetho;

import org.litepal.LitePalApplication;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/2/25.
 */
public class MyApplication extends LitePalApplication {

    private AppComponent mAppComponent;

    private static MyApplication sInstance;

    public static MyApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
        initializeDebug();
        initializeJPush();
        sInstance = this;
    }

    private void initializeJPush() {
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
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
