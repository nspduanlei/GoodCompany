package com.apec.android.domain.usercase;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.transport.ReceiptInfo;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/16.
 * 添加收货信息
 */
public class AddDeliveryUseCase extends UseCase<NoBody> {

    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    ReceiptInfo mReceiptInfo;

    @Inject
    public AddDeliveryUseCase(GoodsRepository repository,
                              @Named("ui_thread") Scheduler uiThread,
                              @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public void setData(ReceiptInfo receiptInfo) {
        mReceiptInfo = receiptInfo;
    }

    @Override
    public Observable<NoBody> buildObservable() {
        return mRepository.addReceiptInfo(mReceiptInfo)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
