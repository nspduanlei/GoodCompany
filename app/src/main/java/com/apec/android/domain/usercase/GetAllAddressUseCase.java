package com.apec.android.domain.usercase;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.transport.ReceiptList;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/16.
 * 获取全部地址
 */
public class GetAllAddressUseCase extends UseCase<ReceiptList> {

    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    @Inject
    public GetAllAddressUseCase(GoodsRepository repository,
                                @Named("ui_thread") Scheduler uiThread,
                                @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    @Override
    public Observable<ReceiptList> buildObservable() {
        return mRepository.getAllAddress()
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
