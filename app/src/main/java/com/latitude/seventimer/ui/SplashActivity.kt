package com.latitude.seventimer.ui

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.latitude.seventimer.R
import com.latitude.seventimer.base.BaseActivity
import com.latitude.seventimer.ui.weather.MainActivity

/**
 *
 * Created by cloud on 2019-08-05.
 */
class SplashActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed(object: Runnable {  }, 1000)
    }
}