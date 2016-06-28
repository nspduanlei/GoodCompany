package com.apec.android.mvp.presenters;

import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.user.Version;
import com.apec.android.domain.usercase.GetVerCodeUseCase;
import com.apec.android.domain.usercase.GetVersionUseCase;
import com.apec.android.mvp.views.MeView;
import com.apec.android.mvp.views.View;
import com.apec.android.util.AppUtils;

import javax.inject.Inject;

/**
 * Created by duanlei on 2016/6/24.
 */
public class MePresenter implements Presenter {

    MeView mMeView;
    GetVersionUseCase mGetVersionUseCase;

    @Inject
    public MePresenter(GetVersionUseCase getVersionUseCase) {
        mGetVersionUseCase = getVersionUseCase;
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
        mMeView = (MeView) v;
    }

    @Override
    public void onCreate() {

    }

    public void getVersion() {
        mGetVersionUseCase.execute().subscribe(this::onVersionReceived, this::manageError);
    }

    private void onVersionReceived(Version version) {
        if (version.getH().getCode() == 200) {
            mMeView.getVersionSuccess(version);
        }
    }

    private void manageError(Throwable throwable) {
    }
}
