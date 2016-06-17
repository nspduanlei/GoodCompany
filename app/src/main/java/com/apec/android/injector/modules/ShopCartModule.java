package com.apec.android.injector.modules;

import com.apec.android.domain.repository.GoodsRepository;
import com.apec.android.domain.usercase.CreateOrderUseCase;
import com.apec.android.domain.usercase.DeleteCartUseCase;
import com.apec.android.domain.usercase.DoAddBatchCartUseCase;
import com.apec.android.domain.usercase.DoAddCartUseCase;
import com.apec.android.domain.usercase.GetAllCartUseCase;
import com.apec.android.injector.Activity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/10.
 */
@Module
public class ShopCartModule {

    @Provides
    @Activity
    DoAddBatchCartUseCase provideDoAddBatchCartUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new DoAddBatchCartUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    GetAllCartUseCase provideGetAllCartUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new GetAllCartUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    DeleteCartUseCase provideDeleteCartUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new DeleteCartUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    DoAddCartUseCase provideDoAddCartUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new DoAddCartUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    CreateOrderUseCase provideCreateOrderUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new CreateOrderUseCase(repository, uiThread, executorThread);
    }

}
