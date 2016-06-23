package com.apec.android.domain.usercase;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.order.OrderListBack;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/16.
 * 加入购物车
 */
public class GetAllOrderUseCase extends UseCase<OrderListBack> {

    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    private int mState;

    @Inject
    public GetAllOrderUseCase(GoodsRepository repository,
                              @Named("ui_thread") Scheduler uiThread,
                              @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    //   "待处理", "备货中", "配送中", "已完成", "已取消"
    public void setState(int state) {
        mState = state;
    }

    @Override
    public Observable<OrderListBack> buildObservable() {
        return mRepository.getAllOrder(mState)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
