package com.apec.android.injector.modules;

import com.apec.android.domain.repository.GoodsRepository;
import com.apec.android.domain.usercase.AddDeliveryUseCase;
import com.apec.android.domain.usercase.DelAddressUseCase;
import com.apec.android.domain.usercase.GetAllAddressUseCase;
import com.apec.android.domain.usercase.GetAreaUseCase;
import com.apec.android.domain.usercase.SetDefaultAddressUseCase;
import com.apec.android.domain.usercase.UpdateDeliveryUseCase;
import com.apec.android.injector.Activity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/10.
 */
@Module
public class AddressModule {

    @Provides
    @Activity
    GetAreaUseCase provideGetAreaUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new GetAreaUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    GetAllAddressUseCase provideGetAllAddressUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new GetAllAddressUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    SetDefaultAddressUseCase provideSetDefaultAddressUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new SetDefaultAddressUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    DelAddressUseCase provideDelAddressUseCase(
                    GoodsRepository repository,
                    @Named("ui_thread") Scheduler uiThread,
                    @Named("executor_thread") Scheduler executorThread) {
        return new DelAddressUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    UpdateDeliveryUseCase provideUpdateDeliveryUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new UpdateDeliveryUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    AddDeliveryUseCase provideAddDeliveryUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new AddDeliveryUseCase(repository, uiThread, executorThread);
    }

}
