package com.latitude.seventimer.injector.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.latitude.seventimer.injector.qualifier.ContextLife;
import com.latitude.seventimer.injector.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yechy on 2017/3/29.
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    @Provides
    @PerFragment
    @ContextLife("Activity")
    public Context provideContext() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Fragment provideFragment() {
        return mFragment;
    }
}
