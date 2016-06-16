package com.apec.android.domain.usercase;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.user.User;
import com.apec.android.domain.repository.GoodsRepository;

import java.net.MalformedURLException;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/16.
 * 加入购物车
 */
public class CompleteUserUseCase extends UseCase<NoBody> {

    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    User mUser;

    @Inject
    public CompleteUserUseCase(GoodsRepository repository,
                               @Named("ui_thread") Scheduler uiThread,
                               @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public void setData(User user) {
        mUser = user;
    }

    /**
     * userShop
     * userName
     * userCity
     * userAreacounty
     *
     * userAddress
     * @return
     */
    @Override
    public Observable<NoBody> buildObservable() {
        return mRepository.completeUser(mUser.getShopName(), mUser.getName(),
                mUser.getAddrRes().getCityId(), mUser.getAddrRes().getAreaId(), mUser.getAddrRes().getDetail())
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
