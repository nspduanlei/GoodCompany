package com.apec.android.domain.usercase;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/16.
 * 获取用户信息
 */
public class GetUserInfoUseCase extends UseCase<NoBody> {

    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    int mSkuId, mNum;

    @Inject
    public GetUserInfoUseCase(GoodsRepository repository,
                              @Named("ui_thread") Scheduler uiThread,
                              @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public void setData(int skuId, int num) {
        mSkuId = skuId;
        mNum = num;
    }

    @Override
    public Observable<NoBody> buildObservable() {
        return mRepository.addShoppingCart(mSkuId, mNum)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
