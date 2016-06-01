package com.apec.android.domain.usercase;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/16.
 * 设置默认地址
 */
public class SetDefaultAddressUseCase extends UseCase<NoBody> {

    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    int mAddressId;

    @Inject
    public SetDefaultAddressUseCase(GoodsRepository repository,
                                    @Named("ui_thread") Scheduler uiThread,
                                    @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public void setData(int addressId) {
        mAddressId = addressId;
    }

    @Override
    public Observable<NoBody> buildObservable() {
        return mRepository.setDefaultAddress(mAddressId)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
