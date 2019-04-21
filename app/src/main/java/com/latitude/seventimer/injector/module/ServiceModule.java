package com.latitude.seventimer.injector.module;

import android.app.Service;
import android.content.Context;
import com.latitude.seventimer.injector.qualifier.ContextLife;
import com.latitude.seventimer.injector.scope.PerService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yechy on 2017/3/29.
 */
@Module
public class ServiceModule {
    private Service mService;

    public ServiceModule(Service mService) {
        this.mService = mService;
    }

    @Provides
    @PerService
    @ContextLife("Service")
    public Context provideContext() {
        return mService;
    }
}
