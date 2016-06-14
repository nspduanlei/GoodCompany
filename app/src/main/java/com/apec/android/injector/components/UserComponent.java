package com.apec.android.injector.components;

import com.apec.android.injector.Activity;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.injector.modules.UserModule;
import com.apec.android.views.activities.CompleteActivity;
import com.apec.android.views.activities.ManageAddressActivity;
import com.apec.android.views.fragments.MeFragment;

import dagger.Component;

/**
 * Created by duanlei on 2016/5/10.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {UserModule.class, ActivityModule.class})
public interface UserComponent extends ActivityComponent {

    void inject(MeFragment meFragment);


    //检测版本更新


}
