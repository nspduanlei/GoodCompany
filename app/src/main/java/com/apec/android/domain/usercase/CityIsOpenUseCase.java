package com.apec.android.domain.usercase;

import com.apec.android.domain.entities.user.OpenCity;
import com.apec.android.domain.repository.GoodsRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/16.
 * 加入购物车
 */
public class CityIsOpenUseCase extends UseCase<ArrayList<OpenCity>> {

    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    @Inject
    public CityIsOpenUseCase(GoodsRepository repository,
                             @Named("ui_thread") Scheduler uiThread,
                             @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }


    @Override
    public Observable<ArrayList<OpenCity>> buildObservable() {
        return mRepository.cityIsOpen()
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
