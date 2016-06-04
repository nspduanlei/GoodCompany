package com.apec.android.domain.usercase;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.order.OrderBack;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/16.
 * 加入购物车
 */
public class GetOrderDetailUseCase extends UseCase<OrderBack> {

    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    int mOrderId;

    @Inject
    public GetOrderDetailUseCase(GoodsRepository repository,
                                 @Named("ui_thread") Scheduler uiThread,
                                 @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public void setData(int orderId) {
        mOrderId = orderId;
    }

    @Override
    public Observable<OrderBack> buildObservable() {
        return mRepository.getOrderDetail(mOrderId)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
