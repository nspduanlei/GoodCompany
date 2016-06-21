package com.apec.android.mvp.presenters;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.user.User;
import com.apec.android.domain.usercase.UpdateUserInfoUseCase;
import com.apec.android.mvp.views.EditUserDataView;
import com.apec.android.mvp.views.View;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/6/17.
 */
public class EditUserDataPresenter implements Presenter {

    UpdateUserInfoUseCase mUpdateUserInfoUseCase;
    EditUserDataView mView;

    Subscription mSubscription;

    @Inject
    public EditUserDataPresenter(UpdateUserInfoUseCase updateUserInfoUseCase) {
        mUpdateUserInfoUseCase = updateUserInfoUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mView = (EditUserDataView) v;
    }

    @Override
    public void onCreate() {

    }

    public void submitInfo(User user) {
        mUpdateUserInfoUseCase.setData(user);
        mUpdateUserInfoUseCase.execute()
                .subscribe(this::onUpdateUserReceived, this::managerError);
    }

    private void managerError(Throwable throwable) {

    }

    private void onUpdateUserReceived(NoBody noBody) {
        if (noBody.getH().getCode() == 200) {
            mView.updateUserSuccess();
        }
    }
}
