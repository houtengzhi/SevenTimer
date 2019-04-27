package com.latitude.seventimer;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.latitude.seventimer.injector.Component.ApplicationComponent;
import com.latitude.seventimer.injector.Component.DaggerApplicationComponent;
import com.latitude.seventimer.injector.module.ApplicationModule;

/**
 * Created by cloud on 2019/3/31.
 */
public class App extends Application {

    private static App instance;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        // Baidu SDK initialization
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    public static App getInstance() {
        return instance;
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
