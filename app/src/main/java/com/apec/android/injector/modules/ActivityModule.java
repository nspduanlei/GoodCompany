package com.apec.android.injector.modules;

import android.content.Context;

import com.apec.android.injector.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by duanlei on 2016/5/9.
 */
@Module
public class ActivityModule {
    private final Context mContext;

    public ActivityModule(Context context) {
        this.mContext = context;
    }

    @Provides
    @Activity
    Context provideActivityContext() {
        return mContext;
    }

}
