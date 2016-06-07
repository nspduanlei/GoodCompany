package com.apec.android.domain.usercase;

import com.apec.android.domain.entities.goods.Goods;
import com.apec.android.domain.entities.goods.SkuList;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/9.
 */
public class GetGoodsUseCase extends UseCase<SkuList> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;
    private int mCid;
    private int mCityId;

    @Inject
    public GetGoodsUseCase(int cid, int cityId,
                           GoodsRepository repository,
                           @Named("ui_thread") Scheduler uiThread,
                           @Named("executor_thread") Scheduler executorThread) {
        mCid = cid;
        mCityId = cityId;
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public void setCityId(int cityId) {
        mCityId = cityId;
    }

    @Override
    public Observable<SkuList> buildObservable() {
        return mRepository.getGoods(mCid, mCityId)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
