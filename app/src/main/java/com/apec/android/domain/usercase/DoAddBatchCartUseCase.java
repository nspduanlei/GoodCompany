package com.apec.android.domain.usercase;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/16.
 * 批量加入购物车
 */
public class DoAddBatchCartUseCase extends UseCase<NoBody> {

    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    //[{“skuId”:1,”num”:3}]
    String mJson;

    @Inject
    public DoAddBatchCartUseCase(GoodsRepository repository,
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
        return mRepository.addBatchShoppingCart(mJson)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
