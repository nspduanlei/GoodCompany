package com.apec.android.injector.modules;

import com.apec.android.domain.repository.GoodsRepository;
import com.apec.android.domain.usercase.CityIsOpenUseCase;
import com.apec.android.domain.usercase.CreateOrderUseCase;
import com.apec.android.domain.usercase.GetAllCityUseCase;
import com.apec.android.domain.usercase.GetDefaultAddressUseCase;
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
public class GoodsModule {

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
    CityIsOpenUseCase provideCityIsOpenUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new CityIsOpenUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    GetDefaultAddressUseCase provideGetDefaultAddressUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new GetDefaultAddressUseCase(repository, uiThread, executorThread);
    }
}
