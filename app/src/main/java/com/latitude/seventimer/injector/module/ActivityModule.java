package com.latitude.seventimer.injector.module;

import android.app.Activity;
import android.content.Context;

import com.latitude.seventimer.injector.qualifier.ContextLife;
import com.latitude.seventimer.injector.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yechy on 2017/3/29.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context provideContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }
}
