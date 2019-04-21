package com.latitude.seventimer.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;


public abstract class BaseActivity extends AppCompatActivity {
    private final static String TAG = "BaseActivity";

    protected RxPermissions mRxPermissions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxPermissions = new RxPermissions(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
