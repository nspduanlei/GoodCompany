package com.apec.android.domain.usercase;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/16.
 * 购物车创建订单
 */
public class CreateOrderUseCase extends UseCase<NoBody> {

    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    //[{“skuId”:1,”num”:3, “addressId”:”1111” }]

    String mJson;

    @Inject
    public CreateOrderUseCase(GoodsRepository repository,
                              @Named("ui_thread") Scheduler uiThread,
                              @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public void setData(String json) {
        mJson = json;
    }

    @Override
    public Observable<NoBody> buildObservable() {
        return mRepository.createOrder(mJson)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
