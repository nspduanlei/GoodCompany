package com.apec.android.injector.components;

import com.apec.android.domain.usercase.DeleteCartUseCase;
import com.apec.android.domain.usercase.DoAddCartUseCase;
import com.apec.android.domain.usercase.GetAllCartUseCase;
import com.apec.android.injector.Activity;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.injector.modules.ShopCartModule;
import com.apec.android.views.fragments.ShoppingCartFragment;

import dagger.Component;

/**
 * Created by duanlei on 2016/5/10.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {ShopCartModule.class, ActivityModule.class})
public interface ShopCartComponent extends ActivityComponent {

    void inject(ShoppingCartFragment shoppingCartFragment);

    GetAllCartUseCase getAllCartUseCase();
    DeleteCartUseCase deleteCartUseCase();
    DoAddCartUseCase doAddCartUseCase();

}
