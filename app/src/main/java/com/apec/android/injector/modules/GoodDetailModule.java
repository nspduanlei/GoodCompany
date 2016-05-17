package com.apec.android.injector.modules;

import com.apec.android.domain.repository.GoodsRepository;
import com.apec.android.domain.usercase.DoAddCartUseCase;
import com.apec.android.domain.usercase.GetAllGoodAttrsUseCase;
import com.apec.android.domain.usercase.GetArriveTimeUseCase;
import com.apec.android.domain.usercase.GetGoodDetailUseCase;
import com.apec.android.injector.Activity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/16.
 */
@Module
public class GoodDetailModule {

    private final int mGoodId;

    public GoodDetailModule(int goodId) {
       mGoodId = goodId;
    }

    @Provides
    @Activity
    GetAllGoodAttrsUseCase provideGetAllGoodAttrsUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new GetAllGoodAttrsUseCase(repository, uiThread, executorThread, mGoodId);
    }

    @Provides
    @Activity
    GetArriveTimeUseCase provideGetArriveTimeUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new GetArriveTimeUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    GetGoodDetailUseCase provideGetGoodDetailUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new GetGoodDetailUseCase(repository, uiThread, executorThread, mGoodId);
    }

    @Provides
    @Activity
    DoAddCartUseCase provideDoAddCartUseCase (
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new DoAddCartUseCase(repository, uiThread, executorThread);
    }
}
