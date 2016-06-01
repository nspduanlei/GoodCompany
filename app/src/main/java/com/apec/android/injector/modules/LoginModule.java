package com.apec.android.injector.modules;

import com.apec.android.domain.repository.GoodsRepository;
import com.apec.android.domain.usercase.GetAllCityUseCase;
import com.apec.android.domain.usercase.GetGoodsUseCase;
import com.apec.android.domain.usercase.GetVerCodeUseCase;
import com.apec.android.domain.usercase.SubmitVerCodeUseCase;
import com.apec.android.injector.Activity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/10.
 */
@Module
public class LoginModule {

    @Provides
    @Activity
    GetAllCityUseCase provideGetAllCityUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new GetAllCityUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    GetVerCodeUseCase provideGetVerCodeUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new GetVerCodeUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    SubmitVerCodeUseCase provideSubmitVerCodeUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new SubmitVerCodeUseCase(repository, uiThread, executorThread);
    }

}
