package com.apec.android.injector.modules;

import android.app.Application;

import com.apec.android.app.MyApplication;
import com.apec.android.domain.repository.GoodsRepository;
import com.apec.android.support.rest.RestDataSource;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by duanlei on 2016/5/9.
 */
@Module
public class AppModule {
    private final MyApplication mMyApplication;

    public AppModule(MyApplication myApplication) {
        this.mMyApplication = myApplication;
    }

    @Provides
    @Singleton
    MyApplication provideMyApplicationContext() {
        return mMyApplication;
    }

    @Provides
    @Singleton
    GoodsRepository provideGoodsRepository(RestDataSource restDataSource) {
        return restDataSource;
    }

    @Provides @Named("executor_thread")
    Scheduler provideExecutorThread() {
        return Schedulers.newThread();
    }

    @Provides @Named("ui_thread")
    Scheduler provideUiThread() {
        return AndroidSchedulers.mainThread();
    }
}
