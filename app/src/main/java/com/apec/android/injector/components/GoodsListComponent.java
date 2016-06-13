package com.apec.android.injector.components;

import com.apec.android.domain.usercase.CreateOneOrderUseCase;
import com.apec.android.domain.usercase.CreateOrderUseCase;
import com.apec.android.domain.usercase.GetGoodsUseCase;
import com.apec.android.injector.Activity;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.injector.modules.GoodsListModule;
import com.apec.android.views.activities.TrueOrderActivity;
import com.apec.android.views.fragments.GoodsFragment;

import dagger.Component;

/**
 * Created by duanlei on 2016/5/10.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {GoodsListModule.class, ActivityModule.class})
public interface GoodsListComponent extends ActivityComponent {

    void inject(GoodsFragment goodsFragment);
    void inject(TrueOrderActivity trueOrderActivity);

    GetGoodsUseCase getGoodsUserCase();
    CreateOneOrderUseCase createOneOrderUseCase();
}
