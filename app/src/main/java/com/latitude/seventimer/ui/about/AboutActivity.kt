package com.latitude.seventimer.ui.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager

import com.latitude.seventimer.R
import com.latitude.seventimer.base.BaseActivity

/**
 * Created by cloud on 2019-08-03.
 */
class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_about)
        toolbar.title = getString(R.string.About)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { super@AboutActivity.onBackPressed() }

        val fm = supportFragmentManager
        var aboutFragment = fm.findFragmentById(R.id.layout_fragment_container) as AboutFragment?
        if (aboutFragment == null) {
            aboutFragment = AboutFragment.instance
        }
        if (!aboutFragment.isAdded) {
            fm.beginTransaction()
                    .add(R.id.layout_fragment_container, aboutFragment)
                    .commit()
        }
    }

    companion object {

        fun actionStart(context: Context) {
            val intent = Intent(context, AboutActivity::class.java)
            context.startActivity(intent)
        }
    }
}
