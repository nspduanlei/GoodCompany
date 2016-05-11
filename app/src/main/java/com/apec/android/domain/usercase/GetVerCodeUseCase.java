package com.apec.android.domain.usercase;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.user.Areas;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.internal.ListenerClass;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/9.
 */
public class GetVerCodeUseCase extends UseCase<NoBody> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;
    private String mMobile;
    private int mType = 3;

    @Inject
    public GetVerCodeUseCase(GoodsRepository repository,
                             @Named("ui_thread") Scheduler uiThread,
                             @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    @Override
    public Observable<NoBody> buildObservable() {
        return mRepository.getVerCode(mMobile, mType)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
