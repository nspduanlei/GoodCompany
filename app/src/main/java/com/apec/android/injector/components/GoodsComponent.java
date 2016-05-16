package com.apec.android.injector.components;

import com.apec.android.domain.entities.goods.GetAllAttribute;
import com.apec.android.domain.usercase.DoAddCartUseCase;
import com.apec.android.domain.usercase.GetAllCityUseCase;
import com.apec.android.domain.usercase.GetArriveTimeUseCase;
import com.apec.android.domain.usercase.GetGoodDetailUseCase;
import com.apec.android.domain.usercase.GetVerCodeUseCase;
import com.apec.android.domain.usercase.SubmitVerCodeUseCase;
import com.apec.android.injector.Activity;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.injector.modules.GoodsModule;
import com.apec.android.mvp.views.GoodDetailView;
import com.apec.android.views.activities.GoodDetailActivity;
import com.apec.android.views.activities.GoodsActivity;
import com.apec.android.views.activities.LoginActivity;

import dagger.Component;

/**
 * Created by duanlei on 2016/5/10.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {GoodsModule.class, ActivityModule.class})
public interface GoodsComponent extends ActivityComponent {
    void inject(GoodsActivity goodsActivity);
    void inject(LoginActivity loginActivity);

    GetAllCityUseCase getAllCityUseCase();
    GetVerCodeUseCase getVerCodeUseCase();
    SubmitVerCodeUseCase submitVerCodeUseCase();
}
