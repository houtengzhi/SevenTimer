package com.latitude.seventimer.ui.location;

import android.os.Bundle;

import com.latitude.seventimer.R;
import com.latitude.seventimer.base.BaseRVFragment;

/**
 * Created by cloud on 2019/4/21.
 */
public class LocationSettingsFragment extends BaseRVFragment<LocationSettingsPresenter>
        implements LocationSettingsContract.IView  {

    public static LocationSettingsFragment newInstance() {
        Bundle args = new Bundle();
        LocationSettingsFragment fragment = new LocationSettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnActionListener(ActionListener listener) {
        this.mActionListener = listener;
    }

    interface ActionListener {
        void onBackCick();
    }

    private ActionListener mActionListener;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_location_settings;
    }

    @Override
    protected boolean isInflateLayout() {
        return true;
    }

    @Override
    protected void setupComponent() {

    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {

    }
}
