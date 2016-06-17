package com.apec.android.injector.modules;

import com.apec.android.domain.repository.GoodsRepository;
import com.apec.android.domain.usercase.CompleteUserUseCase;
import com.apec.android.domain.usercase.UpdateUserInfoUseCase;
import com.apec.android.injector.Activity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/10.
 */
@Module
public class UserModule {

    @Provides
    @Activity
    UpdateUserInfoUseCase provideUpdateUserInfoUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new UpdateUserInfoUseCase(repository, uiThread, executorThread);
    }
}
