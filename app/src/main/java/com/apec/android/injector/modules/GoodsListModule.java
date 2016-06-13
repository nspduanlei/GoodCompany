package com.apec.android.injector.modules;

import com.apec.android.domain.repository.GoodsRepository;
import com.apec.android.domain.usercase.CreateOneOrderUseCase;
import com.apec.android.domain.usercase.CreateOrderUseCase;
import com.apec.android.domain.usercase.GetGoodsUseCase;
import com.apec.android.injector.Activity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/10.
 */
@Module
public class GoodsListModule {

    private final int mCid, mCityId;


    public GoodsListModule(int cid, int cityId) {
        mCid = cid;
        mCityId = cityId;
    }

    @Provides
    @Activity
    GetGoodsUseCase provideGetGoodsUserCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new GetGoodsUseCase(mCid, mCityId, repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    CreateOneOrderUseCase provideCreateOneOrderUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new CreateOneOrderUseCase(repository, uiThread, executorThread);
    }

}
