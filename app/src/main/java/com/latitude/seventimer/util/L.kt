package com.latitude.seventimer.util

import android.util.Log

object L {

    const val logSwitch = true
    const val logLevel = 1
    private val version = ""
    private val LOG_MAXLENGTH = 2000

    @JvmStatic
    fun e(tag: String, msg: String) {
        if (logLevel <= Log.ERROR || logSwitch) {
            Log.e(version + tag, msg)
        }
    }

    fun e(tag: String, format: String, vararg args: Any) {
        e(tag, formatMessage(format, *args))
    }

    @JvmStatic
    fun d(tag: String, msg: String) {
        if (logLevel <= Log.DEBUG || logSwitch) {
            Log.d(version + tag, msg)
        }
    }

    @JvmStatic
    fun d(tag: String, format: String, vararg args: Any) {
        d(tag, formatMessage(format, *args))
    }

    @JvmStatic
    fun w(tag: String, msg: String) {
        if (logLevel <= Log.WARN || logSwitch) {
            Log.w(version + tag, msg)
        }
    }

    @JvmStatic
    fun w(tag: String, format: String, vararg args: Any) {
        w(tag, formatMessage(format, *args))
    }

    @JvmStatic
    fun i(tag: String, msg: String) {
        if (logLevel <= Log.INFO || logSwitch) {
            Log.i(version + tag, msg)
        }
    }

    @JvmStatic
    fun i(tag: String, format: String, vararg args: Any) {
        i(tag, formatMessage(format, *args))
    }

    @JvmStatic
    fun v(tag: String, msg: String) {
        if (logLevel <= Log.VERBOSE || logSwitch) {
            Log.v(version + tag, msg)
        }
    }

    @JvmStatic
    fun v(tag: String, format: String, vararg args: Any) {
        v(tag, formatMessage(format, *args))
    }

    @JvmStatic
    fun LongString(tag: String, msg: String) {
        if (logLevel <= Log.VERBOSE || logSwitch) {

            val strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            for (i in 0..99) {
                if (strLength > end) {
                    L.e(tag, i.toString() + ": " + msg.substring(start, end))
                    start = end
                    end = end + LOG_MAXLENGTH
                } else {
                    L.e(tag, i.toString() + ": " + msg.substring(start, strLength))
                    break
                }
            }
        }
    }

    fun formatMessage(message: String, vararg args: Any): String {
        return String.format(message, *args)
    }

}
