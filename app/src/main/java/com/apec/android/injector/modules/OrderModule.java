package com.apec.android.injector.modules;

import com.apec.android.domain.repository.GoodsRepository;
import com.apec.android.domain.usercase.CancelOrderUseCase;
import com.apec.android.domain.usercase.CreateOrderUseCase;
import com.apec.android.domain.usercase.GetAllAddressUseCase;
import com.apec.android.domain.usercase.GetAllOrderUseCase;
import com.apec.android.domain.usercase.GetOrderDetailUseCase;
import com.apec.android.injector.Activity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/10.
 */
@Module
public class OrderModule {
    @Provides
    @Activity
    CreateOrderUseCase provideCreateOrderUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new CreateOrderUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    GetAllOrderUseCase provideGetAllOrderUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new GetAllOrderUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    GetOrderDetailUseCase provideGetOrderDetailUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new GetOrderDetailUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    CancelOrderUseCase provideCancelOrderUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new CancelOrderUseCase(repository, uiThread, executorThread);
    }

}
