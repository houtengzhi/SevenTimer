package com.latitude.seventimer.injector.Component;

import android.content.Context;


import com.latitude.seventimer.injector.module.ServiceModule;
import com.latitude.seventimer.injector.qualifier.ContextLife;
import com.latitude.seventimer.injector.scope.PerService;

import dagger.Component;

/**
 * Created by yechy on 2017/4/1.
 */
@PerService
@Component(modules = ServiceModule.class, dependencies = ApplicationComponent.class)
public interface ServiceComponent {

    @ContextLife("Service")
    Context getServiceContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
