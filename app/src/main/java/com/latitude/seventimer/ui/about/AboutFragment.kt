package com.latitude.seventimer.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.latitude.seventimer.R
import com.latitude.seventimer.base.BaseFragment
import com.latitude.seventimer.util.PackageUtil

/**
 * Created by cloud on 2019-08-03.
 */
class AboutFragment : Fragment() {

    private var mVersionTv: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mVersionTv = view.findViewById(R.id.tv_about_version)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mVersionTv!!.text = StringBuilder(PackageUtil.getVersionName(context!!))
                .append("(").append(PackageUtil.getVersionCode(context!!)).append(")")
    }

    companion object {

        val instance: AboutFragment
            get() = AboutFragment()
    }
}
