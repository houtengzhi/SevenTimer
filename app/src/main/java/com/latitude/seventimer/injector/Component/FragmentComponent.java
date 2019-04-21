package com.latitude.seventimer.injector.Component;

import android.app.Activity;
import android.content.Context;

import com.latitude.seventimer.injector.module.FragmentModule;
import com.latitude.seventimer.injector.qualifier.ContextLife;
import com.latitude.seventimer.injector.scope.PerFragment;
import com.latitude.seventimer.ui.weather.WeatherFragment;

import dagger.Component;

/**
 * Created by yechy on 2017/3/31.
 */
@PerFragment
@Component(modules = FragmentModule.class, dependencies = {ApplicationComponent.class})
public interface FragmentComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(WeatherFragment fragment);
}
