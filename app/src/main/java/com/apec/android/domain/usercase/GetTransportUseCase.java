package com.apec.android.domain.usercase;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.transport.TransportInfo;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/16.
 * 加入购物车
 */
public class GetTransportUseCase extends UseCase<TransportInfo> {

    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    String mOrderId;

    @Inject
    public GetTransportUseCase(GoodsRepository repository,
                               @Named("ui_thread") Scheduler uiThread,
                               @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public void setData(String orderId) {
        mOrderId = orderId;
    }

    @Override
    public Observable<TransportInfo> buildObservable() {
        return mRepository.getTransport(mOrderId)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
