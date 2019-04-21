package com.latitude.seventimer.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.latitude.seventimer.App;
import com.latitude.seventimer.injector.Component.DaggerFragmentComponent;
import com.latitude.seventimer.injector.Component.FragmentComponent;
import com.latitude.seventimer.injector.module.FragmentModule;


/**
 * Created by yechy on 2017/7/1.
 */

public abstract class BaseFragment extends Fragment {

    protected View parentView;
    protected Activity mActivity;
    protected LayoutInflater inflater;

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
        initDatas();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mActivity = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected abstract void setupComponent();

    protected abstract void configViews();

    protected abstract void initDatas();

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
