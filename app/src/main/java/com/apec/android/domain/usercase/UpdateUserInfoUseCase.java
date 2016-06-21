package com.apec.android.domain.usercase;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.user.User;
import com.apec.android.domain.repository.GoodsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/16.
 * 修改用户信息
 */
public class UpdateUserInfoUseCase extends UseCase<NoBody> {

    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    User mUser;

    @Inject
    public UpdateUserInfoUseCase(GoodsRepository repository,
                                 @Named("ui_thread") Scheduler uiThread,
                                 @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public void setData(User user) {
        mUser = user;
    }

    @Override
    public Observable<NoBody> buildObservable() {
        return mRepository.updateUser(mUser.getPhone(), mUser.getShopName(), mUser.getName())
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
