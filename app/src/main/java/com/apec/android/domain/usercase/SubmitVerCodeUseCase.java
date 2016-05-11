package com.apec.android.domain.usercase;

import com.apec.android.domain.entities.user.UserBack;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/9.
 */
public class SubmitVerCodeUseCase extends UseCase<UserBack> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;
    private String mPhone;
    private String mVCode;

    @Inject
    public SubmitVerCodeUseCase(GoodsRepository repository,
                                @Named("ui_thread") Scheduler uiThread,
                                @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public void setData(String phoneNumber, String vCode) {
        mPhone = phoneNumber;
        mVCode = vCode;
    }

    @Override
    public Observable<UserBack> buildObservable() {
        return mRepository.submitVerCode(mPhone, mVCode)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }


}
