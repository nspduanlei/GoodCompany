package com.apec.android.domain.usercase;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/16.
 * 加入购物车
 */
public class UploadArgumentUseCase extends UseCase<NoBody> {

    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    String mName;
    int mNameType;

    @Inject
    public UploadArgumentUseCase(GoodsRepository repository,
                                 @Named("ui_thread") Scheduler uiThread,
                                 @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public void setData(String name, int nameType) {
        mName = name;
        mNameType = nameType;
    }

    @Override
    public Observable<NoBody> buildObservable() {
        return mRepository.uploadArgument(mName, mNameType)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
