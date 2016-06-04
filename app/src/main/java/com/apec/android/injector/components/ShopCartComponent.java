package com.apec.android.injector.components;

import com.apec.android.injector.Activity;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.injector.modules.OrderModule;
import com.apec.android.injector.modules.ShopCartModule;

import dagger.Component;

/**
 * Created by duanlei on 2016/5/10.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {ShopCartModule.class, ActivityModule.class})
public interface ShopCartComponent extends ActivityComponent {


}
