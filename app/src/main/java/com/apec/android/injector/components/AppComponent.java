package com.apec.android.injector.components;

import com.apec.android.app.MyApplication;
import com.apec.android.domain.repository.GoodsRepository;
import com.apec.android.injector.modules.AppModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/9.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    MyApplication app();

    GoodsRepository goodsRepository();

    @Named("ui_thread")
    Scheduler uiThread();

    @Named("executor_thread")
    Scheduler executorThread();
}
