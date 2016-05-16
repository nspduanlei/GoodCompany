package com.apec.android.domain.usercase;

import com.apec.android.domain.entities.transport.ArrivalTime;
import com.apec.android.domain.entities.user.Areas;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/9.
 * 获取到货时间
 */
public class GetArriveTimeUseCase extends UseCase<ArrivalTime> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    @Inject
    public GetArriveTimeUseCase(GoodsRepository repository,
                                @Named("ui_thread") Scheduler uiThread,
                                @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    @Override
    public Observable<ArrivalTime> buildObservable() {
        return mRepository.getArrivalTime()
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
