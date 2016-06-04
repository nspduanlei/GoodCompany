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
 * 加入购物车
 */
public class GetAllCartUseCase extends UseCase<ShopCartBack> {

    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    private int mCityId;

    @Inject
    public GetAllCartUseCase(GoodsRepository repository,
                             @Named("ui_thread") Scheduler uiThread,
                             @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public void setData(int cityId) {
        mCityId = cityId;
    }

    @Override
    public Observable<ShopCartBack> buildObservable() {
        return mRepository.getAllCart(mCityId)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
