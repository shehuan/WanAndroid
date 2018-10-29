package com.shehuan.wanandroid.utils.sp

class SpUtil {
    companion object {
        fun getCookies(): String {
            return SharedPreferencesHelper.get("cookies", "")
        }

        fun setCookies(version: String) {
            SharedPreferencesHelper.put("cookies", version)
        }
    }
}