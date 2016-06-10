package com.apec.android.injector.components;

import com.apec.android.domain.usercase.GetAllCityUseCase;
import com.apec.android.domain.usercase.GetVerCodeUseCase;
import com.apec.android.domain.usercase.SubmitVerCodeUseCase;
import com.apec.android.injector.Activity;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.injector.modules.LoginModule;
import com.apec.android.views.activities.CompleteActivity;
import com.apec.android.views.activities.GoodsActivity;
import com.apec.android.views.activities.LoginActivity;

import dagger.Component;

/**
 * Created by duanlei on 2016/5/10.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {LoginModule.class, ActivityModule.class})
public interface LoginComponent extends ActivityComponent {

    void inject(LoginActivity loginActivity);

    GetAllCityUseCase getAllCityUseCase();
    GetVerCodeUseCase getVerCodeUseCase();
    SubmitVerCodeUseCase submitVerCodeUseCase();
}
