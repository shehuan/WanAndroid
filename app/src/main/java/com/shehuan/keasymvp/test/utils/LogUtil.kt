package com.shehuan.keasymvp.test.utils

import android.util.Log
import com.shehuan.keasymvp.BuildConfig

class LogUtil {
    companion object {
        fun e(tag: String, log: Any) {
            if (BuildConfig.DEBUG) {
                Log.e(tag, log.toString())
            }
        }

        fun d(tag: String, log: Any) {
            if (BuildConfig.DEBUG) {
                Log.d(tag, log.toString())
            }
        }
    }
}