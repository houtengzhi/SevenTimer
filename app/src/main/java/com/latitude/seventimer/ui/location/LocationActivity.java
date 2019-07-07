package com.latitude.seventimer.ui.location;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.latitude.seventimer.R;
import com.latitude.seventimer.base.BaseActivity;
import com.latitude.seventimer.ui.weather.WeatherFragment;

/**
 * Created by cloud on 2019/4/27.
 */
public class LocationActivity extends BaseActivity {

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LocationActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        FragmentManager fm = getSupportFragmentManager();
        LocationSettingsFragment fragment = (LocationSettingsFragment) fm.findFragmentById(R.id.layout_fragment_container);
        if (fragment == null) {
            fragment = LocationSettingsFragment.newInstance();
        }
        fragment.setOnActionListener(new LocationSettingsFragment.ActionListener() {
            @Override
            public void onBackCick() {
                onBackPressed();
            }
        });
        fm.beginTransaction()
                .add(R.id.layout_fragment_container, fragment)
                .commit();
    }
}
