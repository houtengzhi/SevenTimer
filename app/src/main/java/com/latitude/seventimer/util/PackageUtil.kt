package com.latitude.seventimer.util

import android.content.Context
import android.os.Build

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
        var packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return packageInfo.longVersionCode
        } else {
            return packageInfo.versionCode.toLong()
        }
    }
}