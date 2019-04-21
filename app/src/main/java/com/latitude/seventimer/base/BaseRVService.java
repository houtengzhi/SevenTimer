package com.latitude.seventimer.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.latitude.seventimer.App;
import com.latitude.seventimer.injector.Component.DaggerServiceComponent;
import com.latitude.seventimer.injector.Component.ServiceComponent;
import com.latitude.seventimer.injector.module.ServiceModule;

import javax.inject.Inject;


/**
 * Created by yechy on 2017/4/25.
 */
public abstract class BaseRVService<T extends BaseContract.IPresenter> extends Service
        implements BaseContract.IView{

    @Inject
    protected T mPresenter;

    protected abstract void initInject();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initInject();
        attachView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected ServiceComponent getServiceComponent() {
        return DaggerServiceComponent.builder()
                .applicationComponent(App.getInstance().getApplicationComponent())
                .serviceModule(getServiceModule())
                .build();
    }

    private ServiceModule getServiceModule() {
        return new ServiceModule(this);
    }

}
