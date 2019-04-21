package com.latitude.seventimer.base;

import android.os.Bundle;

import com.latitude.seventimer.App;
import com.latitude.seventimer.injector.Component.ActivityComponent;
import com.latitude.seventimer.injector.Component.DaggerActivityComponent;
import com.latitude.seventimer.injector.module.ActivityModule;

import javax.inject.Inject;

/**
 * Created by yechy on 2017/9/13.
 */

public abstract class BaseRVActivity<T extends BaseContract.IPresenter> extends BaseActivity
implements BaseContract.IView{
    @Inject
    protected T mPresenter;

    protected abstract void setupComponent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.cancelAllSubscribe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .applicationComponent(App.getInstance().getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
