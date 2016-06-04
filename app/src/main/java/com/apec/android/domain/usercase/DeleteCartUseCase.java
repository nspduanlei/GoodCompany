package com.apec.android.domain.usercase;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.user.ShopCartBack;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/16.
 * 删除购物车
 */
public class DeleteCartUseCase extends UseCase<NoBody> {

    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    private int mSkuId;

    @Inject
    public DeleteCartUseCase(GoodsRepository repository,
                             @Named("ui_thread") Scheduler uiThread,
                             @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public void setData(int skuId) {
        mSkuId = skuId;
    }

    @Override
    public Observable<NoBody> buildObservable() {
        return mRepository.deleteCart(mSkuId)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
