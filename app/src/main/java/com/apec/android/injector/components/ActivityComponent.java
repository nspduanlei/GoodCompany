package com.apec.android.injector.components;

import android.content.Context;

import com.apec.android.injector.Activity;
import com.apec.android.injector.modules.ActivityModule;

import dagger.Component;

/**
 * Created by duanlei on 2016/5/9.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Context context();
}
