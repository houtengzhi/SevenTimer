package com.latitude.seventimer.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.latitude.seventimer.App;
import com.latitude.seventimer.injector.Component.DaggerFragmentComponent;
import com.latitude.seventimer.injector.Component.FragmentComponent;
import com.latitude.seventimer.injector.module.FragmentModule;
import com.latitude.seventimer.util.L;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

/**
 * Created by yechy on 2017/3/15.
 */
public abstract class BaseRVFragment<T extends BaseContract.IPresenter> extends Fragment
        implements BaseContract.IView {
    private final static String TAG = "BaseRVFragment";

    @Inject
    protected T mPresenter;

    protected View parentView;
    protected Activity mActivity;
    protected LayoutInflater inflater;

    protected RxPermissions mRxPermissions;


    protected abstract
    @LayoutRes
    int getLayoutResId();

    /**
     * 默认返回true
     * @return
     */
    protected abstract boolean isInflateLayout();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.d(TAG, "onCreateView()");
        if (isInflateLayout()) {
            parentView = inflater.inflate(getLayoutResId(), container, false);
            configViews();
        }
        this.inflater = inflater;
        setupComponent();
        return parentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        L.d(TAG, "onViewCreated()");
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        mRxPermissions = new RxPermissions(this);
        initDatas();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        L.d(TAG, "onAttach()");
        this.mActivity = activity;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.cancelAllSubscribe();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        L.d(TAG, "onDetach()");
        this.mActivity = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected abstract void setupComponent();

    protected abstract void configViews();

    protected abstract void initDatas();

    protected FragmentActivity getSupportActivity() {
        return super.getActivity();
    }

    protected View getParentView() {
        return parentView;
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .applicationComponent(App.getInstance().getApplicationComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

}
