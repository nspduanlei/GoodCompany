package com.apec.android.injector.components;

import com.apec.android.domain.usercase.DoAddCartUseCase;
import com.apec.android.domain.usercase.GetAllGoodAttrsUseCase;
import com.apec.android.domain.usercase.GetArriveTimeUseCase;
import com.apec.android.domain.usercase.GetGoodDetailUseCase;
import com.apec.android.injector.Activity;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.injector.modules.GoodDetailModule;
import com.apec.android.views.activities.GoodDetailActivity;

import dagger.Component;

/**
 * Created by duanlei on 2016/5/10.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {GoodDetailModule.class, ActivityModule.class})
public interface GoodDetailComponent extends ActivityComponent {

    void inject(GoodDetailActivity goodDetailActivity);

    GetAllGoodAttrsUseCase getAllGoodAttrsUseCase();
    GetArriveTimeUseCase getArriveTimeUseCase();
    GetGoodDetailUseCase getGoodDetailUseCase();
    DoAddCartUseCase doAddCartUseCase();
}
