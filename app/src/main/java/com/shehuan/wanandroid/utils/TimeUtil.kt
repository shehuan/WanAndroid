package com.shehuan.wanandroid.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class TimeUtil {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun formatDate(timeMills: Long): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val date = Date(timeMills)
            return sdf.format(date)
        }
    }
}