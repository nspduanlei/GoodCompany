package com.apec.android.domain.usercase;

import com.apec.android.domain.entities.goods.GetAllAttribute;
import com.apec.android.domain.entities.user.Areas;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/9.
 * 获取商品全部规格属性
 */
public class GetAllGoodAttrsUseCase extends UseCase<GetAllAttribute> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;
    private final int mGoodId;

    @Inject
    public GetAllGoodAttrsUseCase(GoodsRepository repository,
                                  @Named("ui_thread") Scheduler uiThread,
                                  @Named("executor_thread") Scheduler executorThread,
                                  int goodId) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
        mGoodId = goodId;
    }

    @Override
    public Observable<GetAllAttribute> buildObservable() {
        return mRepository.getGoodAttrs(mGoodId)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
