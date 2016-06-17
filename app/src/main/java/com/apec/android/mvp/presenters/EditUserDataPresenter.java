package com.apec.android.mvp.presenters;

import com.apec.android.domain.usercase.UpdateUserInfoUseCase;
import com.apec.android.mvp.views.EditUserDataView;
import com.apec.android.mvp.views.View;

import javax.inject.Inject;

/**
 * Created by duanlei on 2016/6/17.
 */
public class EditUserDataPresenter implements Presenter {

    UpdateUserInfoUseCase mUpdateUserInfoUseCase;
    EditUserDataView mView;

    @Inject
    public EditUserDataPresenter(UpdateUserInfoUseCase updateUserInfoUseCase) {
        mUpdateUserInfoUseCase = updateUserInfoUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

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
}
