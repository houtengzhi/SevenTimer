package com.latitude.seventimer.util

import android.content.Context

/**
 *
 * Created by cloud on 2019-08-03.
 */
object PackageUtil {
    fun getVersionName(context : Context): String {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return packageInfo.versionName
    }

    fun getVersionCode(context: Context): Long {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return packageInfo.longVersionCode
    }
}