package com.apec.android.domain.usercase;

import com.apec.android.domain.entities.user.Areas;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/16.
 * 加入购物车
 */
public class GetAreaUseCase extends UseCase<Areas> {

    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    int mId;

    @Inject
    public GetAreaUseCase(GoodsRepository repository,
                          @Named("ui_thread") Scheduler uiThread,
                          @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public void setData(int id) {
        mId = id;
    }

    @Override
    public Observable<Areas> buildObservable() {
        return mRepository.getArea(mId)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
