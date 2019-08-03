package com.latitude.seventimer.ui.weather;

import android.content.Intent;
import android.os.Bundle;

import com.latitude.seventimer.R;
import com.latitude.seventimer.base.BaseActivity;
import com.latitude.seventimer.model.database.WeatherLocation;
import com.latitude.seventimer.ui.location.LocationActivity;
import com.latitude.seventimer.util.AppDefs;

import androidx.fragment.app.FragmentManager;

/**
 * Created by yechy on 2015/9/7.
 */
public class MainActivity extends BaseActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private WeatherLocation selectedAddress;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInsatnceState) {
        super.onCreate(savedInsatnceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        mNavigationDrawerFragment = (NavigationDrawerFragment) fm.findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, findViewById(R.id.drawer_layout));

        WeatherFragment fragment = (WeatherFragment) fm.findFragmentById(R.id.layout_fragment_container);
        if (fragment == null) {
            fragment = WeatherFragment.newInstance();
        }
        fragment.setOnActionListener(new WeatherFragment.ActionListener() {
            @Override
            public void onBackClicked() {
                onBackPressed();
            }

            @Override
            public void onSetLocation() {
                startActivityForResult(LocationActivity.getStartIntent(MainActivity.this), AppDefs.REQUEST_CODE_GET_LOCATION);
            }
        });
        if (!fragment.isAdded()) {
            fm.beginTransaction()
                    .add(R.id.layout_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    public WeatherLocation getSelectedAddress() {
        return selectedAddress;
    }
}
