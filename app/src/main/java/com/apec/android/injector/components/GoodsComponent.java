package com.apec.android.injector.components;

import com.apec.android.domain.usercase.CityIsOpenUseCase;
import com.apec.android.domain.usercase.GetAllCityUseCase;
import com.apec.android.domain.usercase.GetDefaultAddressUseCase;
import com.apec.android.injector.Activity;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.injector.modules.GoodsModule;
import com.apec.android.views.fragments.GoodsCFragment;

import dagger.Component;

/**
 * Created by duanlei on 2016/5/10.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {GoodsModule.class, ActivityModule.class})
public interface GoodsComponent extends ActivityComponent {

    void inject(GoodsCFragment goodsCFragment);

    GetAllCityUseCase getAllCityUseCase();
    CityIsOpenUseCase getCityIsOpenUseCase();
    GetDefaultAddressUseCase GetDefaultAddressUseCase();

}