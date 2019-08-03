package com.latitude.seventimer.ui.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.latitude.seventimer.R;
import com.latitude.seventimer.base.BaseActivity;

/**
 * Created by cloud on 2019-08-03.
 */
public class AboutActivity extends BaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar_about);
        toolbar.setTitle(getString(R.string.About));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.super.onBackPressed();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        AboutFragment aboutFragment = (AboutFragment) fm.findFragmentById(R.id.layout_fragment_container);
        if (aboutFragment == null) {
            aboutFragment = AboutFragment.getInstance();
        }
        if (!aboutFragment.isAdded()) {
            fm.beginTransaction()
                    .add(R.id.layout_fragment_container, aboutFragment)
                    .commit();
        }
    }
}
