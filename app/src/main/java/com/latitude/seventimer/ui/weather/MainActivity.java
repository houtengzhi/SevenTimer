package com.latitude.seventimer.ui.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.latitude.seventimer.R;
import com.latitude.seventimer.base.BaseActivity;
import com.latitude.seventimer.model.Address;
import com.latitude.seventimer.ui.location.ChooseAreaActivity;

/**
 * Created by yechy on 2015/9/7.
 */
public class MainActivity extends BaseActivity {

    private Address selectedAddress;

    @Override
    protected void onCreate(Bundle savedInsatnceState) {
        super.onCreate(savedInsatnceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        WeatherFragment fragment = (WeatherFragment) fm.findFragmentById(R.id.fl_fragment_container);
        if (fragment == null) {
            fragment = WeatherFragment.newInstance();
        }
        fragment.setOnActionListener(new WeatherFragment.ActionListener() {
            @Override
            public void onBackClicked() {

            }

            @Override
            public void onSetLocation() {
                Intent intent = new Intent(MainActivity.this, ChooseAreaActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        fm.beginTransaction()
                .add(R.id.fl_fragment_container, fragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    selectedAddress = (Address) data.getSerializableExtra("selected_address");
                    float latitude = selectedAddress.getLatitude();
                    float longitude = selectedAddress.getLongitude();
                    String addressName = selectedAddress.getAddress();
                    //todo
                }
                break;
            default:
                break;
        }
    }


    public Address getSelectedAddress() {
        return selectedAddress;
    }
}
