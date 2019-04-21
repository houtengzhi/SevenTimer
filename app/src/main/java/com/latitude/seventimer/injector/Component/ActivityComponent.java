package com.latitude.seventimer.injector.Component;

import android.app.Activity;
import android.content.Context;

import com.latitude.seventimer.injector.module.ActivityModule;
import com.latitude.seventimer.injector.qualifier.ContextLife;
import com.latitude.seventimer.injector.scope.PerActivity;

import dagger.Component;

/**
 * Created by yechy on 2017/4/1.
 */
@PerActivity
@Component(modules = ActivityModule.class, dependencies = ApplicationComponent.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    Activity getActivity();

}
